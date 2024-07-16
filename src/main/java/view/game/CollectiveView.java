package view.game;

import controller.GameManager;

import javax.swing.*;
import java.awt.*;

public class CollectiveView extends JLabel {
    private GamePanel gamePanel;
    private String ID;
    public CollectiveView(int x, int y, Color color, String panel, String ID) {
        setText("●");
        setBounds(x,y,15,15);
        setForeground(color);
        gamePanel = GameManager.getINSTANCE().getGameView().getGamePanelMap().get(panel);
        this.ID = ID;
        gamePanel.add(this);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public String getID() {
        return ID;
    }

}
