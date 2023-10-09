package freeriders.mag.action.generate;


import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.psi.PsiElement;
import freeriders.mag.action.generate.ide.ActionGenerateIdePreset;
import freeriders.mag.action.generate.project.ActionGenerateProjectPreset;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.project.state.ProjectPresetsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Defines the ActionGroup to be only visible when parent is a [PSI_ELEMENT]
 */
class ActionGroupGenerate extends ActionGroup {

    @Override
    public void update(@NotNull AnActionEvent event) {
        super.update(event);
        // Enable/disable depending on whether user is editing
        @Nullable PsiElement psi = event.getData(CommonDataKeys.PSI_ELEMENT);
        //event.presentation.isEnabled = project != null
        // Always make visible.
        event.getPresentation().setVisible(psi != null);
        // Take this opportunity to set an icon for the menu entry.
        event.getPresentation().setIcon(AllIcons.Actions.NewFolder);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        // Create subgroups with names
        DefaultActionGroup idePresets = new DefaultActionGroup("Ide Presets", true);
        DefaultActionGroup projectPresets = new DefaultActionGroup("Project Presets", true);

        AppPresetsState settings = AppPresetsState.getInstance();
        settings.getIdePresets().forEach(preset -> {
            idePresets.add(new ActionGenerateIdePreset(preset.getName(), preset.getName(), AllIcons.Actions.NewFolder, preset.getName()));
        });
        ProjectPresetsState projectSettings = ProjectPresetsState.getInstance();

        projectSettings.getProjectPresetsState().forEach(preset -> {
            projectPresets.add(new ActionGenerateProjectPreset(preset.getName(), preset.getName(), AllIcons.Actions.NewFolder, preset.getName()));
        });

        //transform list into array
        return new AnAction[]{idePresets, projectPresets};
    }
}
