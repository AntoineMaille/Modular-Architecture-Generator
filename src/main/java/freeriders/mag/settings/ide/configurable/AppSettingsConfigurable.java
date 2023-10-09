package freeriders.mag.settings.ide.configurable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import freeriders.mag.settings.ide.component.AppSettingsComponent;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.ide.state.PresetConverter;
import freeriders.mag.settings.ide.state.models.FileNode;
import freeriders.mag.settings.ide.state.models.Preset;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent mySettingsComponent;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return null;
    }

    @Override
    public @Nullable JComponent createComponent() {
        mySettingsComponent = new AppSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public boolean isModified() {
        Optional<Preset> preset = mySettingsComponent.getSelectedPreset();
        return preset.filter(value -> !value.getContent().equals(FileNode.fromJson(mySettingsComponent.getTextArea().getText()))).isPresent();
    }

    @Override
    public void apply() {
        AppPresetsState settings = AppPresetsState.getInstance();
        settings.idePresets.stream().filter(preset -> preset.getName().equals(mySettingsComponent.getList().getSelectedValue())).forEach(preset -> {
            //replace the children of the selected preset with the content of the text area
            preset.setContent(FileNode.fromJson(mySettingsComponent.getTextArea().getText()));
        });
    }

    @Override
    public void reset() {
        AppPresetsState settings = AppPresetsState.getInstance();
        if(!settings.idePresets.isEmpty()) mySettingsComponent.getTextArea().setText(settings.idePresets.get(0).toString());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
