package freeriders.mag.settings.project.state;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.annotations.OptionTag;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.ide.state.PresetConverter;
import freeriders.mag.settings.ide.state.models.Preset;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@State(name = "freeriders.mag.settings.project.state.ProjectPresetsState", storages = @Storage("ProjectSettingsPlugin.xml"))
public class ProjectPresetsState implements PersistentStateComponent<ProjectPresetsState> {

    @OptionTag(converter = PresetConverter.class)
    List<Preset> projectPresetsState = new ArrayList<>();

    public static ProjectPresetsState getInstance() {
        return ApplicationManager.getApplication().getService(ProjectPresetsState.class);
    }

    @NotNull
    @Override
    public ProjectPresetsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ProjectPresetsState state) {
       this.projectPresetsState = state.projectPresetsState;
    }

    public void setProjectPresetsState(List<Preset> projectPresetsState) {
        this.projectPresetsState = projectPresetsState;
    }

    public List<Preset> getProjectPresetsState() {
        return projectPresetsState;
    }
}

