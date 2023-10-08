package freeriders.mag.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import freeriders.mag.settings.state.AppSettingsState;
import freeriders.mag.settings.state.models.Preset;
import freeriders.mag.ui.FolderNameDialog;
import freeriders.mag.ui.Notifier;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

import static freeriders.mag.generator.Generator.generate;

public class ActionGenerate extends AnAction {

    private final String id;

    public ActionGenerate(String flutterCleanArchitecture, String flutterCleanArchitecture1, Icon newFolder, String id) {
        super(flutterCleanArchitecture, flutterCleanArchitecture1, newFolder);
        this.id = id;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        FolderNameDialog dialog = new FolderNameDialog(e.getProject());
        //get the settings state
        AppSettingsState settings = AppSettingsState.getInstance();
        Optional<Preset> preset = settings.getPresets().stream().filter(p -> p.getName().equals(id)).findFirst();
        if (preset.isEmpty()) {
            Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your preset.");
        } else if (dialog.showAndGet()) {
            VirtualFile selected =  PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
            if(selected == null) {
                Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your selected folder.");
                return;
            }
            VirtualFile folder = selected.isDirectory()? selected : selected.getParent();
            try {
                generate(preset.get(), folder , dialog.getName());
            } catch (IOException ex) {
                Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your selected folder.");
            }
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }
}
