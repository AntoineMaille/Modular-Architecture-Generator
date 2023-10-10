package freeriders.mag.settings.ide.state.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileNode fileNode = (FileNode) o;
        return Objects.equals(getName(), fileNode.getName()) && getType() == fileNode.getType() && Objects.equals(getContent(), fileNode.getContent()) && Objects.equals(getChildren(), fileNode.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getContent(), getChildren());
    }

    /**
     * Custom JSON serialization
     * @param fileNodes The list of FileNodes to serialize
     * @param prettyPrint Whether to pretty print the JSON or not
     * @return The JSON string
     */
    public static String toJson(List<FileNode> fileNodes, Boolean prettyPrint) {
        Gson gson;
        gson = prettyPrint ? new Gson().newBuilder().setPrettyPrinting().create() : new Gson();
        return gson.toJson(fileNodes);
    }

    /**
     * Custom JSON deserialization
     * @param json The JSON string to deserialize
     * @return The list of FileNodes
     */
    public static List<FileNode> fromJson(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<FileNode>>() {}.getType();
        return gson.fromJson(json,listType);
    }

    /**
     * Creates a list of FileNodes from a list of files
     * @param files The list of files to create the FileNodes from
     * @return The list of FileNodes
     */
    public static List<FileNode> fromFiles(List<File> files) {
        List<FileNode> fileNodes = new ArrayList<>();
        files.forEach(file -> fileNodes.add(fromFile(file)));
        return fileNodes;
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
            if (file.length() >= 5000) return  new FileNode(file.getName(), NodeType.FILE, null, null);
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
