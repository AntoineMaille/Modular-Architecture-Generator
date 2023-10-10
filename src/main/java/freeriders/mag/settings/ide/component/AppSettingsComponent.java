package freeriders.mag.settings.ide.component;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import freeriders.mag.action.export.ExportPresetsAction;
import freeriders.mag.settings.ide.component.components.MyButton;
import freeriders.mag.settings.ide.component.components.MyGridConstraint;
import freeriders.mag.settings.ide.component.components.MyScrollPane;
import freeriders.mag.settings.ide.state.AppPresetsState;
import freeriders.mag.settings.ide.state.models.FileNode;
import freeriders.mag.settings.ide.state.models.Preset;
import freeriders.mag.ui.create.PresetCreationDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

    // The main panel for the component.
    private JPanel myMainPanel;

    // The text area for the component.
    private final JBTextArea myTextArea = new JBTextArea();

    private final MyScrollPane scrollableList = new MyScrollPane(myTextArea);

    // Create buttons for adding and deleting presets
    private final MyButton addButton = new MyButton("+");
    private final MyButton deleteButton = new MyButton("-");

    private final MyButton exportButton = new MyButton("Export Presets");


    // The state of the component.
    private final AppPresetsState appSettingsState;

    public AppSettingsComponent() {
        this.appSettingsState = AppPresetsState.getInstance();
        this.initState();
        this.initUI();
        this.setupFileUpload();
        this.setupDeletePreset();
        this.setupExportPresets();
    }

    /**
     * Create the UI for the component.
     */
    private void initUI(){
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

        // Reset gridx and add the exportButton centered below the list and text area
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weighty = 0;
        panel.add(exportButton, constraints);

        // Increment the gridx value for the right column.
        constraints.gridy = 1;
        constraints.gridx++;
        constraints.weighty = 1.0;
        // Adjust the weight for the right column (wider).
        constraints.weightx = 0.8;

        // Add the text area to the right column.
        panel.add(myTextArea, constraints);

        this.myMainPanel = panel;
    }

    private void initState(){
        this.scrollableList.setPresets(appSettingsState.getIdePresets());
    }


    /**
     * Handle the preset creation
     */
    private void setupFileUpload(){
        addButton.addActionListener(e -> {
            // get project
            PresetCreationDialog dialog = new PresetCreationDialog(null);
            if(dialog.showAndGet()){
                Preset preset = new Preset(dialog.getName(), FileNode.fromFiles(dialog.getTemplateFile()));
                appSettingsState.idePresets.add(preset);
                scrollableList.addPresetToList(preset);
            }
        });
    }

    /**
     * Handle the preset deletion
     */
    private void setupDeletePreset(){
        deleteButton.addActionListener(e -> {
            String selectedPresetName = scrollableList.getList().getSelectedValue();
            if(selectedPresetName == null) return;
            for (Preset preset : appSettingsState.idePresets) {
                if (preset.getName().equals(selectedPresetName)) {
                    appSettingsState.idePresets.remove(preset);
                    scrollableList.deletePresetFromList(preset.getName());
                    break;
                }
            }
        });
    }

    private void setupExportPresets(){
        exportButton.addActionListener(e -> {
            // Find and execute the "Export Presets as JSON" action
            ExportPresetsAction exportAction = (ExportPresetsAction) ActionManager.getInstance().getAction("freeriders.mag.action.export.ExportPresetsAction");


            if (exportAction != null) {
                List<Preset> presets = getList().getSelectedValuesList().stream().map(presetName -> {
                    for (Preset preset : appSettingsState.getIdePresets()) {
                        if (preset.getName().equals(presetName)) {
                            return preset;
                        }
                    }
                    return null;
                }).toList();
                if(!presets.isEmpty())  exportAction.setPresets(presets);
                DataContext dataContext = DataManager.getInstance().getDataContext(exportButton); // Provide the component here
                AnActionEvent event = AnActionEvent.createFromAnAction(exportAction, null, "", dataContext);
                exportAction.actionPerformed(event);
            }
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

    public Optional<Preset> getSelectedPreset(){
        return this.appSettingsState.getIdePresets().stream().filter(preset -> preset.getName().equals(this.scrollableList.getList().getSelectedValue())).findFirst();
    }

    public JComponent getPreferredFocusedComponent() {
        return scrollableList;
    }

}
