package freeriders.mag.action.export;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.ide.state.models.Preset;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExportPresetsAction extends AnAction {

    List<Preset> presets;
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Let the user choose the location to save the JSON file
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        descriptor.setTitle("Select Folder to Save JSON File");

        VirtualFile selectedFolder = FileChooser.chooseFile(descriptor, null, null);
        if (selectedFolder != null) {
            // Get the list of presets
            if(this.presets == null) this.presets = AppPresetsState.getInstance().getIdePresets();

            // Convert presets to JSON
            String json = Preset.toListJson(presets);

            // Create a temporary JSON file
            try {
                String fileName = "presets.json";
                String filePath = selectedFolder.getPath() + File.separator + fileName;
                Files.write(Paths.get(filePath), json.getBytes());

                // Notify the user that the file has been saved
                String message = "Presets JSON file saved to: " + filePath;
                Messages.showInfoMessage(message, "JSON File Saved");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setPresets(List<Preset> presets) {
        this.presets = presets;
    }
}

