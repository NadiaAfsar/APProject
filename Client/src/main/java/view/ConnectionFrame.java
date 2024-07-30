package view;

import controller.Controller;
import controller.GameManager;
import network.ClientHandler;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectionFrame extends JFrame {
    private int xSize;
    private int ySize;
    private JPanel panel;
    private JButton connect;
    private JButton offline;
    public ConnectionFrame() {
        xSize = 500;
        ySize = 500;
        addFrame();
        addPanel();
        addConnect();
        addOffline();
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
    private void addConnect(){
        connect = new JButton("Connect to Server");
        addJButton(connect, 125, 135);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientHandler().initialize();
            }
        });
    }
    private void addOffline(){
        offline = new JButton("Play Offline");
        addJButton(offline, 125, 275);
        offline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Controller.runGame(new GameManager());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    }
    private void addJButton(JButton jButton, int x, int y) {
        jButton.setFont(new Font("Elephant", Font.BOLD, 20));
        jButton.setBackground(Color.WHITE);
        jButton.setForeground(Color.BLACK);
        jButton.setBounds(x,y,250,70);
        panel.add(jButton);
    }
    private void update(){
        revalidate();
        repaint();
    }
    public static boolean showConnectionError(){
        String[] options = new String[]{"Try Again", "Cancel"};
        String message = "Connection failed!";
        int pick = JOptionPane.showOptionDialog(null, message,
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0){
            return true;
        }
        return false;
    }

}
