package view.menu;

import application.MyApplication;
import controller.GameManager;
import controller.save.Configs;

import javax.swing.*;

public class GameFrame extends JFrame {
    private int xSize;
    private int ySize;
    private JPanel panel;
    private JPanel gamePanel;
    private Background background;
    private Settings settings;
    private boolean online;
    private GameManager gameManager;
    public GameFrame(GameManager gameManager, boolean online) {
        xSize = Configs.FRAME_SIZE.width;
        ySize = Configs.FRAME_SIZE.height;
        this.online = online;
        this.gameManager = gameManager;
        addFrame();
        addPanel();
        addGamePanel();
        addBackGround();
        panel.add(gamePanel);
        panel.add(background);
        if (online){
            new GetName(this);
        }
        else {
            new MainMenu(this);
        }
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
        background = new Background(MyApplication.configs.BACKGROUND);
    }
    public void update() {
        panel.revalidate();
        panel.repaint();
    }


    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    public boolean option(String string){
        String[] options = new String[]{"Yes", "No"};
        int pick = JOptionPane.showOptionDialog(null, string,
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0){
            return true;
        }
        return false;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public boolean isOnline() {
        return online;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
