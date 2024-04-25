package view;

import controller.Controller;

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
        startButton.setBounds(200, 100, 100, 50);
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.getINSTANCE().setVisible(false);
                Controller.startGame();
            }
        });
        gamePanel.add(startButton);
    }
    private void addSettingsButton() {
        settingsButton = new JButton("Settings");
        settingsButton.setBounds(700, 100, 100, 50);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gamePanel.add(settingsButton);
    }
    private void addTutorialButton() {
        tutorialButton = new JButton("Tutorial");
        tutorialButton.setBounds(200, 400, 100, 50);
        tutorialButton.setBackground(Color.WHITE);
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gamePanel.add(tutorialButton);
    }
    private void addSkillTree() {
        skillTreeButton = new JButton("Skill Tree");
        skillTreeButton.setBounds(700, 400, 100, 50);
        skillTreeButton.setBackground(Color.WHITE);
        skillTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gamePanel.add(skillTreeButton);
    }
    private void addExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds(1050, 500, 100, 50);
        exitButton.setBackground(Color.RED);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gamePanel.add(exitButton);
    }
}