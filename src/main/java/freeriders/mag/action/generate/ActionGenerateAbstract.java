package freeriders.mag.action.generate;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.vfs.VirtualFile;
import freeriders.mag.generator.Generator;
import freeriders.mag.settings.ide.state.models.Preset;
import freeriders.mag.ui.generate.FolderNameDialog;
import freeriders.mag.ui.utils.Notifier;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;


public abstract class ActionGenerateAbstract extends AnAction {

    private final String id;

    public ActionGenerateAbstract(String name, String description, Icon newFolder, String id) {
        super(name, description, newFolder);
        this.id = id;
    }


    protected void generate(Preset preset,@NotNull AnActionEvent e){
        FolderNameDialog dialog = new FolderNameDialog(e.getProject());
        if (dialog.showAndGet()) {
            VirtualFile selected = PlatformDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
            if (selected == null) {
                Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your selected folder.");
                return;
            }
            VirtualFile folder = selected.isDirectory() ? selected : selected.getParent();
            WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
                try {
                    Generator.generate(preset, folder, dialog.getName());
                } catch (IOException ex) {
                    Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your selected folder.");
                }
            });
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
