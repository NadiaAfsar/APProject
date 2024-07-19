package view.game;

import controller.GameManager;
import controller.listeners.InputListener;
import controller.save.Configs;
import model.skills.Skill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HUI extends JFrame {
    private JLabel HP;
    private JLabel xp;
    private JLabel skill;
    private int ability;
    private JLabel wave;
    private JLabel time;
    private long hour;
    private long minute;
    private long second;
    private int x;
    private int y;
    private int width;
    private int height;
    private JPanel panel;
    private Timer timer;
    public HUI() {
        x = Configs.FRAME_SIZE.width-200;
        y = 10;
        width = 150;
        height = 300;
        addFrame();
        addPanel();
        setHUI();
        setTimer();
        timer.start();
        new InputListener(panel);
    }
    private void addFrame() {
        setBounds(x,y,width,height);
        setUndecorated(true);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
    }
    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setVisible(true);
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        setContentPane(panel);
    }
    private void setTimer() {
        timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        timer.setRepeats(false);
    }
    public void showHUI() {
        setVisible(true);
        setTimer();
        timer.start();
    }
    private String getElapsedTime(long time1) {
        setTime(time1);
        String time = "";
        if (hour < 10) {
            time += 0;
        }
        time += hour+":";
        if (minute < 10) {
            time += 0;
        }
        time += minute+":";
        if (second < 10) {
            time += 0;
        }
        time += second;
        return time;

    }
    private void setTime(long time) {
        hour = time / 3600;
        time %= 3600;
        minute = time / 60;
        second = time % 60;
    }
    private void setHP() {
        HP = new JLabel("HP: "+100);
        HP.setBounds(5,5,80,10);
        HP.setForeground(Color.WHITE);
        panel.add(HP);
    }
    private void setXP() {
        xp = new JLabel("xp: "+0);
        xp.setBounds(5,25,80,15);
        xp.setForeground(Color.WHITE);
        panel.add(xp);
    }
    private void setWave() {
        wave = new JLabel("1/3");
        wave.setBounds(5,45,40,10);
        wave.setForeground(Color.WHITE);
        panel.add(wave);
    }
    private void setTimeJLabel() {
        time = new JLabel("00:00:00");
        time.setBounds(5,65,100,10);
        time.setForeground(Color.WHITE);
        panel.add(time);
    }
    private void setSkill() {
        Skill pickedSkill = GameManager.getINSTANCE().getPickedSkill();
        if (pickedSkill != null) {
            skill = new JLabel(pickedSkill.getName() + ": " + pickedSkill.getStatus());
            skill.setBounds(5, 85, 150, 10);
            skill.setForeground(Color.WHITE);
            panel.add(skill);
            ability++;
        }
    }
    public void setAbility(String ability) {
        JLabel label = new JLabel(ability+" is activated.");
        label.setBounds(5,85+20*this.ability,70,10);
        label.setForeground(Color.WHITE);
        panel.add(label);
    }
    public void setHUI() {
        setHP();
        setXP();
        setWave();
        setTimeJLabel();
        setSkill();
    }
    public void updateHP(int hp) {
        HP.setText("HP: "+hp);
    }
    public void updateXP(int xp) {
        this.xp.setText("xp: "+xp);
    }
    public void updateWave(int wave) {
        this.wave.setText(wave+"/3");
    }
    public void updateTime(long time1) {
        time.setText(getElapsedTime(time1));
    }
    public void updateSkill(Skill skill1) {
        skill.setText(skill1.getName()+": "+skill1.getStatus());
    }
}
