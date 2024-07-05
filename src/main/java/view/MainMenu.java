package view;

import controller.Controller;
import controller.GameManager;
import controller.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private JButton startButton;
    private JButton settingsButton;
    private JButton tutorialButton;
    private JButton skillTreeButton;
    private JButton exitButton;
    private JPanel gamePanel;
    public MainMenu(JPanel gamePanel) {
        this.gamePanel = gamePanel;
        addStartButton();
        addSettingsButton();
        addTutorialButton();
        addSkillTree();
        addExitButton();
    }
    private void addStartButton() {
        startButton = new JButton("Start");
        startButton.setBounds(200, 100, 150, 100);
        startButton.setFont(new Font("Elephant", Font.BOLD, 25));
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getINSTANCE().getGameFrame().setVisible(false);
                Controller.startGame();
            }
        });
        gamePanel.add(startButton);
    }
    private void addSettingsButton() {
        settingsButton = new JButton("Settings");
        settingsButton.setBounds(700, 100, 150, 100);
        settingsButton.setFont(new Font("Elephant", Font.BOLD, 25));
        settingsButton.setBackground(Color.WHITE);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                new Settings(gamePanel, Controller.getSensitivity(), Controller.getDifficulty(),
                        (int)((Sound.getSoundValue()+80)/0.86));
                GameManager.getINSTANCE().getGameFrame().update();
            }
        });
        gamePanel.add(settingsButton);
    }
    private void addTutorialButton() {
        tutorialButton = new JButton("Tutorial");
        tutorialButton.setBounds(200, 400, 150, 100);
        tutorialButton.setBackground(Color.WHITE);
        tutorialButton.setFont(new Font("Elephant", Font.BOLD, 25));
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                new Tutorial(gamePanel);
            }
        });
        gamePanel.add(tutorialButton);
    }
    private void addSkillTree() {
        skillTreeButton = new JButton("Skill Tree");
        skillTreeButton.setBounds(700, 400, 150, 100);
        skillTreeButton.setFont(new Font("Elephant", Font.BOLD, 20));
        skillTreeButton.setBackground(Color.WHITE);
        skillTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                new SkillTree(gamePanel);
                GameManager.getINSTANCE().getGameFrame().update();
            }
        });
        gamePanel.add(skillTreeButton);
    }
    private void addExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds(1050, 500, 100, 50);
        exitButton.setFont(new Font("Elephant", Font.BOLD, 20));
        exitButton.setBackground(Color.RED);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gamePanel.add(exitButton);
    }
    private void empty() {
        gamePanel.remove(startButton);
        gamePanel.remove(settingsButton);
        gamePanel.remove(tutorialButton);
        gamePanel.remove(skillTreeButton);
        gamePanel.remove(exitButton);
    }
}