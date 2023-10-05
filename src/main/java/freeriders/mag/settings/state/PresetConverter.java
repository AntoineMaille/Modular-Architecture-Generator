package freeriders.mag.settings.state;

import com.google.gson.Gson;
import com.intellij.util.xmlb.Converter;
import freeriders.mag.settings.state.models.Preset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PresetConverter extends Converter<List<Preset>> {

    @Override
    public @Nullable List<Preset> fromString(@NotNull String value) {
        return Preset.fromListJson(value);

    }

    @Override
    public @Nullable String toString(@NotNull List<Preset> value) {
        try {
            return new Gson().toJson(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
