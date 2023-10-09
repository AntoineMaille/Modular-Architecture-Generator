package freeriders.mag.settings.ide.component;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBTextArea;
import freeriders.mag.settings.ide.state.models.Preset;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class PresetSelectionListener implements ListSelectionListener {

    List<Preset> presets;

    JBList<String> list;

    JBTextArea myTextArea;

    public PresetSelectionListener(List<Preset> presets, JBList<String> list, JBTextArea myTextArea) {
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
            for (Preset preset : presets) {
                if (preset.getName().equals(selectedPresetName)) {
                    // Set the text area to the preset content
                    myTextArea.setText(preset.getContent().toJson(true));
                    return;
                }
            }
            myTextArea.setText("");
        }
    }
}
