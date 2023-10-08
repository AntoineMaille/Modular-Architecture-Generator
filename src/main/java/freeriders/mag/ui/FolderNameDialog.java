package freeriders.mag.ui;

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
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        dialogPanel.add(textField, BorderLayout.CENTER);

        return dialogPanel;
    }

    public String getName() {
        return textField.getText();
    }
}
