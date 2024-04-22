package view;

import controller.Constants;

import javax.swing.*;

public class GameFrame extends JFrame {
    private int xSize;
    private int ySize;
    private JPanel panel;
    private JPanel gamePanel;
    private Background background;
    public static GameFrame INSTANCE;
    private MainMenu mainMenu;
    public GameFrame() {
        xSize = Constants.FRAME_SIZE.width;
        ySize = Constants.FRAME_SIZE.height;
        addFrame();
        addPanel();
        addGamePanel();
        addBackGround();
        panel.add(gamePanel);
        panel.add(background);
        mainMenu = new MainMenu(gamePanel);
        update();
    }
    private void addFrame() {
        setTitle("Window Kill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(xSize, ySize);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, xSize, ySize);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        setContentPane(panel);
    }
    private void addGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, xSize, ySize);
        gamePanel.setVisible(true);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();
        gamePanel.setLayout(null);
        gamePanel.setOpaque(false);
    }
    private void addBackGround() {
        background = new Background("src/main/resources/background.jpg");
    }
    private void update() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public static GameFrame getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameFrame();
        }
        return INSTANCE;
    }
}
