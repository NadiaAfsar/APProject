package view;

import controller.Controller;
import controller.InputListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Settings{
    private JSlider soundSlider;
    private JSlider difficultySlider;
    private JSlider sensitivitySlider;
    private JLabel music;
    private JLabel difficulty;
    private JLabel sensitivity;
    private JPanel panel;
    private JButton back;
    private final int d;
    private final int s;
    private final int m;

    public Settings(JPanel panel, int s, int d, int m) {
        this.s = s;
        this.d = d;
        this.m = m;
        this.panel = panel;
        addMusic();
        addSoundSlider();
        addDifficulty();
        addDifficultySlider();
        addSensitivity();
        addSensitivitySlider();
        addBack();
    }
    private void addJLabel(JLabel jLabel, int x, int y) {
        jLabel.setBounds(x,y, 300,100);
        jLabel.setFont(new Font("Elephant", Font.BOLD, 30));
        jLabel.setForeground(Color.GRAY);
        panel.add(jLabel);
    }
    private void addSoundSlider() {
        soundSlider = new JSlider(0,100);
        soundSlider.setBounds(500,100,300,50);
        soundSlider.setValue(m);
        soundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float value = (float) soundSlider.getValue();
                Controller.getMusic().controlSound(value);
            }
        });
        panel.add(soundSlider);
    }
    private void addMusic() {
        music = new JLabel("Music:");
        addJLabel(music,300, 70);
    }
    private void addDifficultySlider() {
        difficultySlider = new JSlider(1,3);
        difficultySlider.setBounds(500,200,300,50);
        difficultySlider.setValue(d);
        difficultySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Controller.setDifficulty(difficultySlider.getValue());
            }
        });
        panel.add(difficultySlider);
    }
    private void addDifficulty() {
        difficulty = new JLabel("Difficulty");
        addJLabel(difficulty,300,170);
    }
    private void addSensitivitySlider() {
        sensitivitySlider = new JSlider(1,3);
        sensitivitySlider.setBounds(500,300,300,50);
        sensitivitySlider.setValue(s);
        sensitivitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Controller.setSensitivity(sensitivitySlider.getValue());
            }
        });
        panel.add(sensitivitySlider);
    }
    private void addSensitivity() {
        sensitivity = new JLabel("Sensitivity:");
        addJLabel(sensitivity, 300, 270);
    }
    private void addBack() {
        back = new JButton("Back");
        back.setFont(new Font("Elephant", Font.BOLD, 20));
        back.setBackground(Color.GRAY);
        back.setForeground(Color.BLACK);
        back.setBounds(1000,550,100,50);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                new MainMenu(panel);
                update();
            }
        });
        panel.add(back);
    }
    private void empty() {
        panel.remove(music);
        panel.remove(soundSlider);
        panel.remove(difficulty);
        panel.remove(difficultySlider);
        panel.remove(sensitivity);
        panel.remove(sensitivitySlider);
        panel.remove(back);
    }
    private void update() {
        panel.revalidate();
        panel.repaint();
    }





}
