package freeriders.mag.generator;

import com.intellij.openapi.vfs.VirtualFile;
import freeriders.mag.settings.ide.state.models.FileNode;
import freeriders.mag.settings.ide.state.models.NodeType;
import freeriders.mag.settings.ide.state.models.Preset;

import java.io.IOException;

public class Generator {

    /**
     * Generate a file or a directory in the parent folder
     */
    public static void generate(Preset preset, VirtualFile parent, String rootFolderName) throws IOException {
        VirtualFile child = parent.createChildDirectory(parent, rootFolderName);
        for(FileNode node: preset.getContent()){
            generateHelper(node, child, rootFolderName);
        }
    }

    public static void generateHelper(FileNode fileNode, VirtualFile parent, String featureName) throws IOException {
        if (fileNode.getType() == NodeType.DIRECTORY) {
            // create a directory in the parent folder
            VirtualFile child = parent.createChildDirectory(parent, fileNode.getName().replaceAll("<name>", featureName));
            if(fileNode.getChildren() == null) return;
            for (FileNode childNode : fileNode.getChildren()) {
                // generate the children of the directory
                generateHelper(childNode, child, featureName);
            }
        } else if (fileNode.getType() == NodeType.FILE) {
            // create a file in the parent folder
            VirtualFile child = parent.createChildData(parent, fileNode.getName().replaceAll("<name>", featureName));
            // write the content of the file
            child.setBinaryContent(fileNode.getContent().replaceAll("<name>", featureName).getBytes());
        }
    }
}

