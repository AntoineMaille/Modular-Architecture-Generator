package freeriders.mag.settings.component;

import com.google.gson.JsonObject;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import freeriders.mag.settings.state.AppSettingsState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    // The main panel for the component.
    private JPanel myMainPanel;

    // The text area for the component.
    private final JBTextArea myTextArea = new JBTextArea();
    // The scrollableList for the component.
    private final JBList<String> list = new JBList<>();
    private final JBScrollPane scrollableList = new JBScrollPane(list);

    // The table for the component.
    private final JBTable table = new JBTable();

    // The state of the component.
    private final AppSettingsState appSettingsState;

    public AppSettingsComponent() {
        this.appSettingsState = AppSettingsState.getInstance();
        this.initUI();
        this.initState();
        this.setListeners();
    }
    /**
     * Initializes the state of the component.
     */
    private void initState(){
        // Populate the list with preset names
        DefaultListModel<String> listModel = new DefaultListModel<>();
        java.util.List<JsonObject> presets = appSettingsState.getPresets();
        for (JsonObject preset : presets) {
            // Assuming your presets have a specific structure, e.g., a "name" field
            String name = preset.get("name").getAsString();
            listModel.addElement(name);
        }
        list.setModel(listModel);
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

        GridBagConstraints constraints = this.createConstraints();
        // Add the scrollable list to the left column.
        panel.add(scrollableList, constraints);

        // Increment the gridx value for the right column.
        constraints.gridx++;

        // Adjust the weight for the right column (wider).
        constraints.weightx = 0.8;

        // Add the text area to the right column.
        panel.add(myTextArea, constraints);

        this.myMainPanel = panel;
    }

    private GridBagConstraints createConstraints(){
        // Create GridBagConstraints for layout.
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.2; // Adjust the weight for the left column (narrower).
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = JBUI.insets(5);
        return constraints;
    }


    /**
     * Sets the listeners for the List component.
     */
    private void setListeners(){
        list.addListSelectionListener(new PresetSelectionListener(appSettingsState.getPresets(), list, myTextArea));
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JBTextArea getTextArea() {
        return myTextArea;
    }

    public JBList<String> getList() {
        return list;
    }

    public JBScrollPane getScrollableList() {
        return scrollableList;
    }

    public JComponent getPreferredFocusedComponent() {
        return myTextArea;
    }

}
