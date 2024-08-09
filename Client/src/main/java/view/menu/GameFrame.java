package view.menu;

import application.MyApplication;
import controller.ApplicationManager;
import controller.save.Configs;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {
    private int xSize;
    private int ySize;
    private JPanel panel;
    private JPanel gamePanel;
    private Background background;
    private Settings settings;
    private boolean online;
    private ApplicationManager applicationManager;
    public GameFrame(ApplicationManager applicationManager, boolean online) {
        xSize = Configs.FRAME_SIZE.width;
        ySize = Configs.FRAME_SIZE.height;
        this.online = online;
        this.applicationManager = applicationManager;
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
        setSize(xSize, ySize);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                applicationManager.getClientHandler().interrupt();
            }
        });
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

    public ApplicationManager getApplicationManager() {
        return applicationManager;
    }
    public static boolean receiveRequest(String name, String request){
        String[] options = new String[]{"Accept", "Decline"};
        int pick = JOptionPane.showOptionDialog(null, name+" sent "+request+" request.",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0){
            return true;
        }
        return false;
    }
    public static void showMessage(String message){
        String[] options = new String[]{"Ok"};
        int pick = JOptionPane.showOptionDialog(null, message,
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
