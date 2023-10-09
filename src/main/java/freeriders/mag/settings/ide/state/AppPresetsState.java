package freeriders.mag.settings.ide.state;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.OptionTag;
import freeriders.mag.settings.ide.state.models.Preset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@State(
        name = "freeriders.mag.settings.ide.state.AppSettingsState",
        storages = @Storage("SettingsPlugin.xml")
)
public class AppPresetsState implements PersistentStateComponent<AppPresetsState> {

    @OptionTag(converter = PresetConverter.class)
    public List<Preset> idePresets = new ArrayList<>();

    public List<Preset> getIdePresets() {
        return idePresets;
    }

    public static AppPresetsState getInstance() {
        return ApplicationManager.getApplication().getService(AppPresetsState.class);
    }
    @Override
    public @Nullable AppPresetsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppPresetsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
