package freeriders.mag.settings.state;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.OptionTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@State(
        name = "freeriders.mag.settings.state.AppSettingsState",
        storages = @Storage("SettingsPlugin.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    @OptionTag(converter = JsonConverter.class)
    public List<JsonObject> presets = new ArrayList<>(Collections.singleton(JsonParser.parseString("{\"name\":\"default\",\"content\":\"{\"myFeature\":{}\"}").getAsJsonObject()));

    public List<JsonObject> getPresets() {
        return presets;
    }

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }
    @Override
    public @Nullable AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
