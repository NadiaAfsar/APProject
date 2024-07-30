package view.menu;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Shop extends JFrame {
    private final int width;
    private final int height;
    private JButton hephaestus;
    private JButton athena;
    private JButton apollo;
    private JButton deimos;
    private JButton hypnos;
    private JButton phonoi;
    private JButton resume;
    private JButton mainMenu;
    private JPanel panel;
    private int x;
    private int y;
    public Shop() {
        width = 700;
        height = 600;
        x = (int) Configs.FRAME_SIZE.getWidth()/2-width/2;
        y = (int) Configs.FRAME_SIZE.getHeight()/2-height/2;
        addFrame();
        addPanel();
        addHephaestus();
        addAthena();
        addApollo();
        addHypnos();
        addPhonoi();
        addDeimos();
        addResume();
        addMainMenu();
    }
    private void addFrame() {
        setBounds(x, y, width,height);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }
    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.setLayout(null);
        panel.setBackground(Color.DARK_GRAY);
        setContentPane(panel);
    }
    private void addDeimos() {
        deimos = new JButton("O' Deimos, Dismay");
        addJButton(deimos, 400, 220);
        deimos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().deimos()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O' Deimos, Dismay");
                    resumeGame();
                }
            }
        });
    }
    private void addAthena() {
        athena = new JButton("O, Athena, Empower");
        addJButton(athena, 50, 220);
        athena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().athena()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O, Athena, Empower");
                    resumeGame();
                }
            }
        });
    }
    private void addApollo() {
        apollo = new JButton("O' Apollo Heal");
        addJButton(apollo, 50, 340);
        apollo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().apollo()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O' Apollo Heal");
                    resumeGame();
                }
            }
        });
    }
    private void addHypnos() {
        hypnos = new JButton("O' Hypnos, Slumber");
        addJButton(hypnos, 400, 340);
        hypnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().hypnos()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O' Hypnos, Slumber");
                    resumeGame();
                }
            }
        });
    }
    private void addHephaestus() {
        hephaestus = new JButton("O' Hephaestus, Banish");
        addJButton(hephaestus, 50, 100);
        hephaestus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().hephaestus()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O' Hephaestus, Banish");
                    resumeGame();
                }
            }
        });
    }
    private void addPhonoi() {
        phonoi = new JButton("O' Phonoi, Slaughter");
        addJButton(phonoi, 400, 100);
        phonoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GameManager.getINSTANCE().phonoi()) {
                    showNotEnoughXP();
                }
                else {
                    GameManager.getINSTANCE().getGameView().getHui().setAbility("O' Phonoi, Slaughter");
                    resumeGame();
                }
            }
        });
    }
    private void addJButton(JButton jButton, int x, int y) {
        jButton.setFont(new Font("Elephant", Font.BOLD, 15));
        jButton.setBackground(Color.WHITE);
        jButton.setForeground(Color.BLACK);
        jButton.setBounds(x,y,250,70);
        panel.add(jButton);
    }
    private void addResume() {
        resume = new JButton("Resume");
        resume.setFont(new Font("Elephant", Font.BOLD, 20));
        resume.setBackground(Color.BLACK);
        resume.setForeground(Color.WHITE);
        resume.setBounds(450,500, 150, 50);
        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });
        panel.add(resume);
    }
    private void showNotEnoughXP() {
        String[] options = new String[]{"OK"};
        JOptionPane.showOptionDialog(null, "Not enough XPs!",null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
    private void resumeGame() {
        Controller.gameRunning = true;
        GameManager.getINSTANCE().getGameModel().setLastSavedTime(System.currentTimeMillis());
        dispose();
    }
    private void addMainMenu() {
        mainMenu = new JButton("Main Menu");
        mainMenu.setFont(new Font("Elephant", Font.BOLD, 15));
        mainMenu.setBackground(Color.BLACK);
        mainMenu.setForeground(Color.WHITE);
        mainMenu.setBounds(50,500, 150, 50);
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getINSTANCE().stopGame();
                ArrayList<GamePanel> gamePanels = GameManager.getINSTANCE().getGameView().getGamePanels();
                for (int i = 0; i < gamePanels.size(); i++){
                    gamePanels.get(i).getFrame().dispose();
                }
                GameManager.getINSTANCE().getGameFrame().setVisible(true);
                dispose();
            }
        });
        panel.add(mainMenu);
    }
}
