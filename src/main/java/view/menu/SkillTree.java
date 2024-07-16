package view.menu;

import controller.Controller;
import controller.SkillsController;
import save.Save;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkillTree {
    private JLabel skillTree;
    private JButton writOfAres;
    private JButton writOfAceso;
    private JButton writOfProteus;
    private JLabel attack;
    private JLabel defence;
    private JLabel transform;
    private JPanel panel;
    private JButton back;
    private String[] options;
    private JLabel totalXP;
    public SkillTree(JPanel panel) {
        this.panel = panel;
        options = new String[]{"Yes","No"};
        addSkillTree();
        addAttack();
        addWritOfAres();
        addDefence();
        addWritOfAceso();
        addTransform();
        addWritOfProteus();
        addBack();
        addTotalXP();
        update();

    }
    private void addSkillTree() {
        skillTree = new JLabel("Skill Tree");
        skillTree.setFont(new Font("Elephant", Font.BOLD, 60));
        skillTree.setForeground(Color.WHITE);
        skillTree.setBounds(450,50,500,200);
        panel.add(skillTree);
    }
    private void addWritOfAres() {
        writOfAres = new JButton("Writ Of Ares");
        addJButton(writOfAres, 150, 350);
        writOfAres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SkillsController.isAresUnlocked()) {
                    showPickOption(0, "Writ Of Ares");
                }
                else {
                    showUnlockOption(750,0, "Writ Of Ares");
                }
            }
        });
        if (SkillsController.isAresPicked()) {
            writOfAres.setBackground(Color.GREEN);
        }
        else if (SkillsController.isAresUnlocked()) {
            writOfAres.setBackground(Color.WHITE);
        }
    }
    private void addAttack() {
        attack = new JLabel("Attack:");
        addJLabel(attack, 150,200);
    }
    private void addWritOfAceso() {
        writOfAceso = new JButton("Writ Of Aceso");
        addJButton(writOfAceso, 500, 350);
        writOfAceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SkillsController.isAcesoUnlocked()) {
                    showPickOption(1, "Writ Of Aceso");
                }
                else {
                    showUnlockOption(500,1, "Writ Of Aceso");
                }
            }
        });
        if (SkillsController.isAcesoPicked()) {
            writOfAceso.setBackground(Color.GREEN);
        }
        else if (SkillsController.isAcesoUnlocked()) {
            writOfAceso.setBackground(Color.WHITE);
        }
    }
    private void addDefence() {
        defence = new JLabel("Defence:");
        addJLabel(defence, 500, 200);
    }
    private void addWritOfProteus() {
        writOfProteus = new JButton("Writ Of Proteus");
        addJButton(writOfProteus, 850, 350);
        writOfProteus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SkillsController.isProteusUnlocked()) {
                    showPickOption(2, "Writ Of Proteus");
                }
                else {
                    showUnlockOption(1000,2, "Writ Of Proteus");
                }
            }
        });
        if (SkillsController.isProteusPicked()) {
            writOfProteus.setBackground(Color.GREEN);
        }
        else if (SkillsController.isProteusUnlocked()) {
            writOfProteus.setBackground(Color.WHITE);
        }
    }
    private void addTransform() {
        transform = new JLabel("Transform:");
        addJLabel(transform, 850, 200);
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
        panel.remove(writOfAceso);
        panel.remove(writOfAres);
        panel.remove(writOfProteus);
        panel.remove(back);
        panel.remove(totalXP);
    }
    private void pickSkill(int x) {
        if (x != 0) {
            if (SkillsController.isAresUnlocked()) {
                writOfAres.setBackground(Color.WHITE);
            }
            else {
                writOfAres.setBackground(Color.RED);
            }
            SkillsController.setAres(false);
        }
        else {
            writOfAres.setBackground(Color.GREEN);
            SkillsController.setAres(true);
        }
        if (x != 1) {
            if (SkillsController.isAcesoUnlocked()) {
                writOfAceso.setBackground(Color.WHITE);
            }
            else {
                writOfAceso.setBackground(Color.RED);
            }
            SkillsController.setAceso(false);
        }
        else {
            writOfAceso.setBackground(Color.GREEN);
            SkillsController.setAceso(true);
        }
        if (x != 2) {
            if (SkillsController.isProteusUnlocked()) {
                writOfProteus.setBackground(Color.WHITE);
            }
            else {
                writOfProteus.setBackground(Color.RED);
            }
            SkillsController.setProteus(false);
        }
        else {
            writOfProteus.setBackground(Color.GREEN);
            SkillsController.setProteus(true);
        }
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
            int xp = Controller.getXP();
            if (xp >= price) {
                Controller.setXP(xp-price);
                pickSkill(skill);
                if (skill == 0) {
                    SkillsController.setAresUnlocked(true);
                } else if (skill == 1) {
                    SkillsController.setAcesoUnlocked(true);
                } else {
                    SkillsController.setProteusUnlocked(true);
                }
                totalXP.setText("XPs: "+Controller.getTotalXP());
                Save.save();
            }
            else {
                String[] options = new String[]{"OK"};
                JOptionPane.showOptionDialog(null, "Not enough XPs!",null, JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            }
        }
    }
    private void addTotalXP() {
        totalXP = new JLabel("XPs: "+Controller.getTotalXP());
        addJLabel(totalXP,300 ,500);
    }
}
