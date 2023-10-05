package freeriders.mag.settings.component.components;

import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public MyButton(String text) {
        super(text);
        Dimension buttonSize = new Dimension(20, 20);
        this.setPreferredSize(buttonSize);
        this.setPreferredSize(buttonSize);
        this.setMargin(JBUI.emptyInsets());
    }
}
