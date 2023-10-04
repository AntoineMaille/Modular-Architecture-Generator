package freeriders.mag.settings.state;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JsonConverter extends Converter<JsonObject> {

    @Override
    public @Nullable JsonObject fromString(@NotNull String value) {
        return JsonParser.parseString(value).getAsJsonObject();
    }

    @Override
    public @Nullable String toString(@NotNull JsonObject value) {
        return value.toString();
    }
}
