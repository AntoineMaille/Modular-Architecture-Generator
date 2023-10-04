package freeriders.mag.settings.component;

import com.google.gson.JsonObject;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBTextArea;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class PresetSelectionListener implements ListSelectionListener {

    List<JsonObject> presets;

    JBList<String> list;

    JBTextArea myTextArea;

    public PresetSelectionListener(List<JsonObject> presets, JBList<String> list, JBTextArea myTextArea) {
        this.presets = presets;
        this.list = list;
        this.myTextArea = myTextArea;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // Get the selected preset name
            String selectedPresetName = list.getSelectedValue();

            // Find the preset by name in the presets list
            for (JsonObject preset : presets) {
                if (preset.get("name").getAsString().equals(selectedPresetName)) {
                    // Set the content of the selected preset in the text area
                    myTextArea.setText(preset.get("content").getAsString());
                    break;
                }
            }
        }
    }
}
