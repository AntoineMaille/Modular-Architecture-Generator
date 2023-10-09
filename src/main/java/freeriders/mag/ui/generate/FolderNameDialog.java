package freeriders.mag.ui.generate;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class FolderNameDialog extends DialogWrapper {


    JBTextField textField = new JBTextField();

    public FolderNameDialog(@Nullable Project project) {
        super(project);
        this.setTitle("Enter the Title of the Root Folder to Be Generated");
        init();
        // Set focus on the JBTextField
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        // Set a preferred size for the text field
        textField.setPreferredSize(new Dimension(300, 30)); // Adjust the dimensions as needed
        dialogPanel.add(textField, BorderLayout.CENTER);
        return dialogPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return textField;
    }

    public String getName() {
        return textField.getText();
    }
}
