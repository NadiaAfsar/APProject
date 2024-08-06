package view.menu;

import controller.Controller;
import controller.audio.Audio;

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
    private JButton squad;
    private JPanel gamePanel;
    private boolean online;
    private GameFrame gameFrame;
    public MainMenu(GameFrame gameFrame) {
        this.gamePanel = gameFrame.getGamePanel();
        this.online = gameFrame.isOnline();
        this.gameFrame = gameFrame;
        addStartButton();
        addSettingsButton();
        addTutorialButton();
        addSkillTree();
        addExitButton();
        if (gameFrame.isOnline()) {
            addSquad();
        }
        gameFrame.update();
    }
    private void addButton(JButton button, int x, int y){
        button.setBounds(x,y, 200, 100);
        button.setFont(new Font("Elephant", Font.BOLD, 25));
        button.setBackground(Color.WHITE);
        gamePanel.add(button);
    }
    private void addStartButton() {
        startButton = new JButton("Start");
        addButton(startButton, 150, 100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                Controller.startGame(gameFrame.getApplicationManager());
            }
        });
    }
    private void addSettingsButton() {
        settingsButton = new JButton("Settings");
        addButton(settingsButton, 450, 100);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                new Settings(gameFrame, gameFrame.getApplicationManager().getSensitivity(), gameFrame.getApplicationManager().getDifficulty(),
                        (int)((Audio.getSoundValue()+80)/0.86));
                gameFrame.update();
            }
        });
    }
    private void addTutorialButton() {
        tutorialButton = new JButton("Tutorial");
        addButton(tutorialButton, 150, 250);
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                new Tutorial(gameFrame);
            }
        });
    }
    private void addSkillTree() {
        skillTreeButton = new JButton("Skill Tree");
        addButton(skillTreeButton, 450, 250);
        skillTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                new SkillTree(gameFrame);
                gameFrame.update();
            }
        });
    }
    private void addSquad(){
        squad = new JButton("Squad");
        addButton(squad, 150, 400);
        squad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                new Squad(gameFrame);
            }
        });
    }
    private void addExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setBounds(600, 500, 100, 50);
        exitButton.setFont(new Font("Elephant", Font.BOLD, 20));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gamePanel.add(exitButton);
    }
}