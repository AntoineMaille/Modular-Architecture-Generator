package freeriders.mag.ui.create;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBTextField;
import freeriders.mag.settings.ide.state.models.FileNode;
import freeriders.mag.settings.ide.state.models.Preset;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class PresetCreationDialog extends DialogWrapper {


    private final JBTextField textField = new JBTextField();

    private final JFileChooser fileChooser = new JFileChooser();
    private final JButton button = new JButton("Choose a template");

    File selectedFile;




    public PresetCreationDialog(@Nullable Project project) {
        super(project);
        this.setTitle("Enter the Title of the Preset to Be Created");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        // Set a preferred size for the text field
        textField.setPreferredSize(new Dimension(300, 30)); // Adjust the dimensions as needed

        this.setupListener();

        dialogPanel.add(textField, BorderLayout.CENTER);
        dialogPanel.add(button, BorderLayout.SOUTH);

        return dialogPanel;
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        // Implement your custom validation logic here
        String name = getName();
        if (name == null || name.trim().isEmpty()) {
            return new ValidationInfo("Name cannot be empty.", textField);
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
            selectedFile = fileChooser.getSelectedFile();
        });
    }

    public String getName() {
        return textField.getText();
    }

    /**
     * Returns the selected file
     * @return the selected file
     */
    public File getTemplateFile() {
        return selectedFile;
    }




}
