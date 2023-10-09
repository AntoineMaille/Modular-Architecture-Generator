package freeriders.mag.settings.ide.component.components;

import com.intellij.util.ui.JBUI;

import java.awt.*;

public class MyGridConstraint extends GridBagConstraints {

    public MyGridConstraint() {
        this.fill = GridBagConstraints.BOTH;
        this.gridx = 0;
        this.gridy = 0;
        this.gridwidth = 1;
        this.gridheight = 1;
        this.insets = JBUI.insets(5);
    }
}
