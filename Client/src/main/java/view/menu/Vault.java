package view.menu;

import controller.save.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vault {
    private GameFrame gameFrame;
    private JPanel panel;
    private JButton palixios;
    private JButton adonis;
    private JButton gefion;
    private JTextField textField;
    private JButton pay;
    private JLabel xp;
    private JButton back;

    public Vault(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();

        gameFrame.update();
    }
    private void addButton(JButton button, int x, int y, int width, int height){
        button.setBounds(x,y, width, height);
        button.setFont(new Font("Elephant", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        panel.add(button);
    }
    private void addPalixios(){
        palixios = new JButton("Call of Palioxis");
        addButton(palixios, Configs.FRAME_SIZE.width/2-200, 50, 400, 100);
        palixios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameFrame.getApplicationManager().getClientHandler().getClient().getSquad().getXP() >= 100){

                }
                else {
                    GameFrame.showMessage("Not enough XP!");
                }
            }
        });
    }
    private void addAdonis(){
        adonis = new JButton("Call of Adonis");
        addButton(adonis, Configs.FRAME_SIZE.width/2-200, 170, 400, 100);
        adonis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameFrame.getApplicationManager().getClientHandler().getClient().getSquad().getXP() >= 400){

                }
                else {
                    GameFrame.showMessage("Not enough XP!");
                }
            }
        });
    }
    private void addGefion(){
        gefion = new JButton("Call of Gefion");
        addButton(gefion, Configs.FRAME_SIZE.width/2-200, 290, 400, 100);
        gefion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameFrame.getApplicationManager().getClientHandler().getClient().getSquad().getXP() >= 300){

                }
                else {
                    GameFrame.showMessage("Not enough XP!");
                }
            }
        });
    }
    private void addTextField(){
        textField = new JTextField();
        textField.setBounds(100, 420, 200, 50);
        textField.setFont(new Font("Elephant", Font.BOLD, 25));
        panel.add(textField);
    }
    private void addPay(){
        pay = new JButton("Pay");
        addButton(pay, 350, 420, 150, 50);
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameFrame.getApplicationManager().getClientHandler().getClient().getSquad().isInBattle()){
                    int xp = Integer.parseInt(textField.getText());
                    if (gameFrame.getApplicationManager().getTotalXP() > xp){

                    }
                    else {
                        GameFrame.showMessage("Not enough XP!");
                    }
                }
                else {
                    GameFrame.showMessage("You're in battle!");
                }
            }
        });
    }
    private void addBack(){
        back = new JButton("Back");
        addButton(back, 100, Configs.FRAME_SIZE.height-200, 150, 50);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
            }
        });
    }
}
