package view.game;

import controller.GameManager;

import javax.swing.*;
import java.awt.*;

public class CollectiveView extends JLabel {
    public CollectiveView(int x, int y, Color color) {
        setText("●");
        setBounds(x,y,15,15);
        setForeground(color);
        GameManager.getINSTANCE().getGameView().add(this);
    }

}
