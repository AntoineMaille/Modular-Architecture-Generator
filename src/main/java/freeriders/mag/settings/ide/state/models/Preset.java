package freeriders.mag.settings.ide.state.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Preset {
    @SerializedName("name")
    String name;

    @SerializedName("content")
    FileNode content;

    public Preset(String name, FileNode content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileNode getContent() {
        return content;
    }

    public void setContent(FileNode content) {
        this.content = content;
    }

    // Custom JSON serialization
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Custom JSON deserialization
    public static Preset fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Preset.class);
    }

    public static List<Preset> fromListJson(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Preset>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    /**
     * Creates a FileNode from a file
     * @param presets the presets to create the json
     * @return The json
     */
    public static String toListJson(List<Preset> presets) {
        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<List<Preset>>() {}.getType();
        return gson.toJson(presets, listType);
    }
}
