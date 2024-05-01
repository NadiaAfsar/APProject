package view;

import javax.swing.*;

public class Shop extends JFrame {
    private final int width;
    private final int height;
    private JPanel panel;
    public Shop() {
        width = 300;
        height = 700;
        addFrame();
        addPanel();
    }
    private void addFrame() {
        setTitle("Shop");
        setSize(width, height);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        setContentPane(panel);
    }
}
