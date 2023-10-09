package freeriders.mag.action.generate.project;

import com.intellij.openapi.actionSystem.AnActionEvent;
import freeriders.mag.action.generate.ActionGenerateAbstract;
import freeriders.mag.settings.ide.state.models.Preset;
import freeriders.mag.settings.project.state.ProjectPresetsState;
import freeriders.mag.ui.utils.Notifier;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Optional;

public class ActionGenerateProjectPreset extends ActionGenerateAbstract {

    String id;

    public ActionGenerateProjectPreset(String name, String description, Icon newFolder, String id) {
        super(name, description, newFolder, id);
        this.id = id;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //get the settings state
        ProjectPresetsState settings = ProjectPresetsState.getInstance();
        Optional<Preset> preset = settings.getProjectPresetsState().stream().filter(p -> p.getName().equals(id)).findFirst();
        if (preset.isEmpty()) Notifier.notifyError(e.getProject(), "An error occurred while generating the project. We couldn't find your preset.");
        else super.generate(preset.get(), e);
    }
}
