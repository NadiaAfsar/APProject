package view;

import javax.swing.*;
import java.awt.*;

public class XPView extends JLabel {
    public XPView(int x, int y, Color color) {
        setText("●");
        setBounds(x,y,15,15);
        setForeground(color);
        GameView.getINSTANCE().add(this);
    }

}
