package freeriders.mag.settings.component.components;

import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import freeriders.mag.settings.component.PresetSelectionListener;
import freeriders.mag.settings.state.models.Preset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MyScrollPane extends JBScrollPane {

    // The scrollableList for the component.
    private final JBList<String> list = new JBList<>();

    private DefaultListModel<String> listModel;

    private final JBTextArea myTextArea;

    private final List<Preset> presets = new ArrayList<>();
    public MyScrollPane(JBTextArea myTextArea) {
        this.initList();
        this.setViewportView(list);
        this.myTextArea = myTextArea;
        this.setListeners();
    }

    public void setPresets(List<Preset> presets){
        this.presets.clear();
        this.presets.addAll(presets);
        this.initList();
    }

    /**
     * Sets the listeners for the List component.
     */
    private void setListeners(){
        list.addListSelectionListener(new PresetSelectionListener(presets, list, myTextArea));
    }

    /**
     * Initializes the list
     */
    private void initList(){
        listModel = new DefaultListModel<>();
        presets.forEach(preset -> listModel.addElement(preset.getName()));
        list.setModel(listModel);
    }

    /**
     * Adds a preset to the list
     * @param preset the preset to add
     */
    public void addPresetToList(Preset preset){
        listModel.addElement(preset.getName());
    }

    /**
     * Deletes a preset from the list
     * @param presetName the name of the preset to delete
     */
    public void deletePresetFromList(String presetName){
        listModel.removeElement(presetName);
    }

    public JBList<String> getList() {
        return this.list;
    }
}
