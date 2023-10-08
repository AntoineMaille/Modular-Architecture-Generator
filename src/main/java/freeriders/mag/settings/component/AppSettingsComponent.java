package freeriders.mag.settings.component;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.table.JBTable;
import freeriders.mag.settings.component.components.MyButton;
import freeriders.mag.settings.component.components.MyGridConstraint;
import freeriders.mag.settings.component.components.MyScrollPane;
import freeriders.mag.settings.state.AppSettingsState;
import freeriders.mag.settings.state.models.FileNode;
import freeriders.mag.settings.state.models.Preset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    // The main panel for the component.
    private JPanel myMainPanel;

    // The text area for the component.
    private final JBTextArea myTextArea = new JBTextArea();

    private final MyScrollPane scrollableList = new MyScrollPane(myTextArea);


    // The upload button for the component.
    JButton uploadButton = new JButton("Upload Folder Structure");

    // The table for the component.
    private final JBTable table = new JBTable();

    // Create buttons for adding and deleting presets
    private final MyButton addButton = new MyButton("+");
    private final MyButton deleteButton = new MyButton("-");


    // The state of the component.
    private final AppSettingsState appSettingsState;

    public AppSettingsComponent() {
        this.appSettingsState = AppSettingsState.getInstance();
        this.initState();
        this.initUI();
        this.setupFileUpload();
    }

    /**
     * Create the UI for the component.
     */
    private void initUI(){
        DefaultTableModel model = (DefaultTableModel) table.getModel() ;
        model.addColumn("Preset");
        model.addColumn("");
        JPanel panel = new JPanel(new GridBagLayout());
        myTextArea.setWrapStyleWord(true);
        myTextArea.setLineWrap(true);


        GridBagConstraints constraints = new MyGridConstraint();

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();

        // LEFT COLUMN
        constraints.weighty = 0;
        constraints.weightx = 0.2;
        // Add the "Add" and "Delete" buttons to the panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        // change constraint to align the button panel on left
        constraints.anchor = GridBagConstraints.NORTHWEST;
        panel.add(buttonPanel, constraints);

        //reset constraint
        constraints.anchor = GridBagConstraints.CENTER;

        // SCROLLABLE LIST
        constraints.weighty = 1.0;
        constraints.gridy++;
        panel.add(scrollableList, constraints);

        // Increment the gridx value for the right column.
        constraints.gridx++;

        // Adjust the weight for the right column (wider).
        constraints.weightx = 0.8;

        // Add the text area to the right column.
        panel.add(myTextArea, constraints);
        panel.add(uploadButton);

        this.myMainPanel = panel;
    }

    private void initState(){
        this.scrollableList.setPresets(appSettingsState.getPresets());
    }



    private void setupFileUpload(){
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();
            appSettingsState.presets.add(new Preset("preset" + appSettingsState.presets.size(), FileNode.fromFile(file)));
        });
    }




    public JPanel getPanel() {
        return myMainPanel;
    }

    public JBTextArea getTextArea() {
        return myTextArea;
    }

    public JBList<String> getList() {
        return scrollableList.getList();
    }

    public JBScrollPane getScrollableList() {
        return scrollableList;
    }

    public JComponent getPreferredFocusedComponent() {
        return myTextArea;
    }

}
