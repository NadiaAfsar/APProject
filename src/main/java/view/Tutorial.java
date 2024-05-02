package view;

import controller.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Tutorial {
    private JButton skills;
    private JButton shop;
    private JButton aboutGame;
    private JButton back;
    private JButton backToTutorial;
    private JLabel shopJLabel;
    private JLabel skillJLabel;
    private JLabel game;
    private JPanel panel;
    public Tutorial(JPanel jPanel) {
        this.panel = jPanel;
        try {
            shopJLabel = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/shop.png"))));
            shopJLabel.setBounds(0,0,1121,600);
            skillJLabel = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/skills.png"))));
            skillJLabel.setBounds(0,0,1036,600);
            game = new JLabel(new ImageIcon(ImageIO.read(new File("src/main/resources/aboutGame.png"))));
            game.setBounds(0,0,1073,600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addShop();
        addSkills();
        addAboutGame();
        addBack();
        update();
    }
    private void addJButton(JButton button, int x, int y) {
        button.setFont(new Font("Elephant", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBounds(x,y,200,80);
        panel.add(button);
    }
    private void addShop() {
        shop = new JButton("Shop");
        addJButton(shop, (int)Constants.FRAME_SIZE.getWidth()/2-100, 100);
        shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addBackToTutorial(shopJLabel);
                panel.add(shopJLabel);
                update();
            }
        });
    }
    private void addSkills() {
        skills = new JButton("Skills");
        addJButton(skills, (int)Constants.FRAME_SIZE.getWidth()/2-100, 300);
        skills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addBackToTutorial(skillJLabel);
                panel.add(skillJLabel);
                update();
            }
        });
    }
    private void addAboutGame() {
        aboutGame = new JButton("About Game");
        addJButton(aboutGame, (int)Constants.FRAME_SIZE.getWidth()/2-100, 500);
        aboutGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addBackToTutorial(game);
                panel.add(game);
                update();
            }
        });
    }
    private void empty() {
        panel.remove(shop);
        panel.remove(skills);
        panel.remove(aboutGame);
        panel.remove(back);
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
    private void update() {
        panel.revalidate();
        panel.repaint();
    }
    private void addBackToTutorial(JLabel jlabel) {
        backToTutorial = new JButton("Back");
        backToTutorial.setFont(new Font("Elephant", Font.BOLD, 20));
        backToTutorial.setBackground(Color.GRAY);
        backToTutorial.setForeground(Color.BLACK);
        backToTutorial.setBounds(1000,550,100,50);
        backToTutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(backToTutorial);
                panel.remove(jlabel);
                new Tutorial(panel);
                update();
            }
        });
        panel.add(backToTutorial);
    }
}
