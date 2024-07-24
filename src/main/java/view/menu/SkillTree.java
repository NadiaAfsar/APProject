package view.menu;

import controller.Controller;
import controller.GameManager;
import model.skills.Skill;
import model.skills.attack.WritOfAres;
import model.skills.attack.WritOfAstrape;
import model.skills.attack.WritOfCerberus;
import model.skills.defence.WritOfAceso;
import model.skills.defence.WritOfAthena;
import model.skills.defence.WritOfChiron;
import model.skills.defence.WritOfMelampus;
import model.skills.transform.WritOfDolus;
import model.skills.transform.WritOfEmpusa;
import model.skills.transform.WritOfProteus;
import save.Save;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SkillTree {
    private JLabel skillTree;
    private JButton writOfAres;
    private JButton writOfAstrape;
    private JButton writOfCerberus;
    private JButton writOfAceso;
    private JButton writOfAthena;
    private JButton writOfChiron;
    private JButton writOfMelampus;
    private JButton writOfProteus;
    private JButton writOfDolus;
    private JButton writOfEmpusa;
    private JLabel attack;
    private JLabel defence;
    private JLabel transform;
    private JPanel panel;
    private JButton back;
    private String[] options;
    private JLabel totalXP;
    private Map<Integer, JButton> skills;
    private Map<JButton, Skill> skillsMap;
    private JButton picked;
    public SkillTree(JPanel panel) {
        this.panel = panel;
        options = new String[]{"Yes","No"};
        skills = new HashMap<>();
        skillsMap = new HashMap<>();
        addSkillTree();
        addAttack();
        addDefence();
        addTransform();
        addBack();
        addTotalXP();
        addSkills();
        update();

    }
    private void addSkills() {
        writOfAres = new JButton();
        addButton(writOfAres,1, new WritOfAres(), 150, 210);
        writOfAstrape = new JButton();
        addButton(writOfAstrape, 2, new WritOfAstrape(), 150, 300);
        writOfCerberus = new JButton();
        addButton(writOfCerberus, 3, new WritOfCerberus(), 150, 390);
        writOfAceso = new JButton();
        addButton(writOfAceso, 4, new WritOfAceso(), 500, 210);
        writOfMelampus = new JButton();
        addButton(writOfMelampus, 5, new WritOfMelampus(), 500, 300);
        writOfChiron = new JButton();
        addButton(writOfChiron, 6, new WritOfChiron(), 500, 390);
        writOfAthena = new JButton();
        addButton(writOfAthena, 7, new WritOfAthena(), 500, 480);
        writOfProteus = new JButton();
        addButton(writOfProteus, 8, new WritOfProteus(), 850, 210);
        writOfEmpusa = new JButton();
        addButton(writOfEmpusa, 9, new WritOfEmpusa(), 850, 300);
        writOfDolus = new JButton();
        addButton(writOfDolus, 10, new WritOfDolus(), 850, 390);
    }
    private void addSkillTree() {
        skillTree = new JLabel("Skill Tree");
        skillTree.setFont(new Font("Elephant", Font.BOLD, 60));
        skillTree.setForeground(Color.WHITE);
        skillTree.setBounds(450,0,500,200);
        panel.add(skillTree);
    }
    private void addButton(JButton button, int n, Skill skill, int x, int y){
        button.setText(skill.getName());
        addJButton(button, x, y);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (skill.isUnlocked()) {
                    showPickOption(n, skill.getName());
                }
                else {
                    showUnlockOption(skill.getPrice(), n, skill.getName());
                }
            }
        });
        if (skill.isPicked()) {
            button.setBackground(Color.GREEN);
            picked = button;
        }
        else if (skill.isUnlocked()) {
            button.setBackground(Color.WHITE);
        }
        skills.put(n,button);
        skillsMap.put(button, skill);
    }
    private void addAttack() {
        attack = new JLabel("Attack:");
        addJLabel(attack, 150,100);
    }
    private void addDefence() {
        defence = new JLabel("Defence:");
        addJLabel(defence, 500, 100);
    }
    private void addTransform() {
        transform = new JLabel("Transform:");
        addJLabel(transform, 850, 100);
    }
    private void update() {
        panel.revalidate();
        panel.repaint();
    }
    private void addJLabel(JLabel jlabel, int x, int y) {
        jlabel.setFont(new Font("Elephant", Font.BOLD, 30));
        jlabel.setForeground(Color.GRAY);
        jlabel.setBounds(x,y,300,100);
        panel.add(jlabel);
    }
    private void addJButton(JButton jButton, int x, int y) {
        jButton.setFont(new Font("Elephant", Font.BOLD, 20));
        jButton.setBackground(Color.RED);
        jButton.setForeground(Color.BLACK);
        jButton.setBounds(x,y,250,70);
        panel.add(jButton);
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
        panel.remove(skillTree);
        panel.remove(defence);
        panel.remove(attack);
        panel.remove(transform);
        for (int i = 1; i <= 10; i++){
            panel.remove(skills.get(i));
        }
        panel.remove(back);
        panel.remove(totalXP);
    }
    private void pickSkill(int x) {
        if (picked != null){
            picked.setBackground(Color.white);
            skillsMap.get(picked).setPicked(false);
        }
        skills.get(x).setBackground(Color.GREEN);
        skillsMap.get(skills.get(x)).setPicked(true);
        picked = skills.get(x);
        GameManager.getINSTANCE().setPickedSkill(skillsMap.get(skills.get(x)));
    }
    private void showPickOption(int skill, String title) {
        int pick = JOptionPane.showOptionDialog(null, "Do you wanna pick this skill?", title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0) {
            pickSkill(skill);
            Save.save();
        }
    }
    private void showUnlockOption(int price, int skill, String title) {
        int pick = JOptionPane.showOptionDialog(null, "You have to pay "+price+" XPs. Do you wanna unlock this skill?", title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0) {
            boolean toUnlock = true;
            int xp = Controller.getXP();
            if (skill != 1 && skill != 4 && skill != 8){
                if (skill == 7){
                    if (!skillsMap.get(writOfAstrape).isUnlocked() && !skillsMap.get(writOfChiron).isUnlocked()){
                        toUnlock = false;
                        unlockFirst(skillsMap.get(writOfAstrape).getName()+" and "+skillsMap.get(writOfChiron).getName());
                    }
                    else if (!skillsMap.get(writOfAstrape).isUnlocked()){
                        toUnlock = false;
                        unlockFirst(skillsMap.get(writOfAstrape).getName());
                    }
                    else if (!skillsMap.get(writOfChiron).isUnlocked()){
                        toUnlock = false;
                        unlockFirst(skillsMap.get(writOfChiron).getName());
                    }
                }
                else if (!skillsMap.get(skills.get(skill-1)).isUnlocked()){
                    toUnlock = false;
                    unlockFirst(skillsMap.get(skills.get(skill-1)).getName());
                }
            }
            if (toUnlock) {
                if (xp >= price) {
                    Controller.setXP(xp - price);
                    pickSkill(skill);
                    skillsMap.get(skills.get(skill)).setUnlocked(true);
                    totalXP.setText("XPs: " + Controller.getTotalXP());
                    Save.save();
                } else {
                    String[] options = new String[]{"OK"};
                    JOptionPane.showOptionDialog(null, "Not enough XPs!", null, JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                }
            }
        }
    }
    private void unlockFirst(String string){
        String[] options = new String[]{"OK"};
        JOptionPane.showOptionDialog(null, "Unlock "+string+" first!",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
    private void addTotalXP() {
        totalXP = new JLabel("XPs: "+Controller.getTotalXP());
        addJLabel(totalXP,250 ,550);
    }
}
