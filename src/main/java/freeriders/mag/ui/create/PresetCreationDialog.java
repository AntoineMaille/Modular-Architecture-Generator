package freeriders.mag.ui.create;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBTextField;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.ide.state.models.Preset;
import freeriders.mag.ui.utils.Notifier;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PresetCreationDialog extends DialogWrapper {


    private final JBTextField textField = new JBTextField();

    private final JFileChooser fileChooser = new JFileChooser();
    private final JButton button = new JButton("Choose a template");

    private List<File> selectedFile;

    private final Project project;


    public PresetCreationDialog(@Nullable Project project) {
        super(project);
        this.setTitle("Enter the Title of the Preset to Be Created");
        this.project = project;
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        // Set a preferred size for the text field
        textField.setPreferredSize(new Dimension(300, 30)); // Adjust the dimensions as needed
        //add margin to textField
        dialogPanel.setBorder(BorderFactory.createCompoundBorder(
                dialogPanel.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.setupListener();

        dialogPanel.add(textField, BorderLayout.CENTER);
        dialogPanel.add(button, BorderLayout.SOUTH);

        return dialogPanel;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        // Implement your custom validation logic here
        String name = getName();
        AppPresetsState appSettingsState = AppPresetsState.getInstance();
        if (name == null || name.trim().isEmpty()) {
            return new ValidationInfo("Name cannot be empty.", textField);
        }
        if( appSettingsState.getIdePresets().stream().map(Preset::getName).toList().contains(name)){
            return new ValidationInfo("Name already exists.", textField);
        }
        if (selectedFile == null) {
            return new ValidationInfo("You must select a template.", button);
        }
        return null; // Return null if validation succeeds
    }

    /**
     * Sets up the listener for the button
     */
    private void setupListener(){
        button.addActionListener(e ->{
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(null);
            File chosenFile = fileChooser.getSelectedFile();
            if(chosenFile != null && chosenFile.listFiles() != null){
                selectedFile = Arrays.stream(Objects.requireNonNull(fileChooser.getSelectedFile().listFiles())).toList();
            }
            else{
                Notifier.notifyError(project, "You must select a directory with your template inside");
            }
        });
    }

    public String getName() {
        return textField.getText();
    }

    /**
     * Returns the selected file
     * @return the selected file
     */
    public List<File> getTemplateFile() {
        return selectedFile;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return textField;
    }
}
