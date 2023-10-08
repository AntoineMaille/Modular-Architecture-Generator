package freeriders.mag.settings.state.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileNode {

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private NodeType type;
    @SerializedName("content")
    private String content; // For files
    @SerializedName("children")
    private List<FileNode> children; // For folders

    public FileNode(String name, NodeType type, String content, List<FileNode> children) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.children = children;
    }


    // Custom JSON serialization
    public String toJson(Boolean prettyPrint) {
        Gson gson;
        gson = prettyPrint ? new Gson().newBuilder().setPrettyPrinting().create() : new Gson();
        return gson.toJson(this);
    }

    // Custom JSON deserialization
    public static FileNode fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FileNode.class);
    }

    /**
     * Creates a FileNode from a file
     *
     * @param file The file to create the FileNode from
     * @return The created FileNode
     */
    public static FileNode fromFile(File file) {
        if (file.isDirectory()) {
            File[] childrenFiles = file.listFiles();
            if (childrenFiles != null && childrenFiles.length != 0) {
                List<FileNode> children = new ArrayList<>();
                for (File child : childrenFiles) {
                    children.add(fromFile(child));
                }
                return new FileNode(file.getName(), NodeType.DIRECTORY, null, children);
            } else return new FileNode(file.getName(), NodeType.DIRECTORY, null, null);
        } else {
            // get the content of the file
            String content = "";
            try {
                content = new String(Objects.requireNonNull(Files.readAllBytes(file.toPath())));
            } catch (Exception e) {
                // if we cannot get the content of the file, we create a FileNode with null content
                return new FileNode(file.getName(), NodeType.FILE, null, null);
            }
            return new FileNode(file.getName(), NodeType.FILE, content, null);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileNode> children) {
        this.children = children;
    }
}
