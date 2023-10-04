package freeriders.mag.settings.configurable;

import com.google.gson.JsonParser;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import freeriders.mag.settings.component.AppSettingsComponent;
import freeriders.mag.settings.state.AppSettingsState;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;

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
        AppSettingsState settings = AppSettingsState.getInstance();
        return true;
    }

    @Override
    public void apply() {
        AppSettingsState settings = AppSettingsState.getInstance();
        settings.presets.stream().filter(preset -> preset.get("name").getAsString().equals(mySettingsComponent.getList().getSelectedValue())).forEach(preset -> {
            preset.addProperty("content", mySettingsComponent.getTextArea().getText());
        });
    }

    @Override
    public void reset() {
        AppSettingsState settings = AppSettingsState.getInstance();
        mySettingsComponent.getTextArea().setText(settings.presets.get(0).toString());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }
}
