package view.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetName {
    private GameFrame gameFrame;
    private JPanel panel;
    private JLabel getName;
    private JTextField textField;
    private JButton next;
    public GetName(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();
        setGetName();
        setTextField();
        setNext();
        gameFrame.update();
    }
    private void addButton(JButton button, int x, int y, int width, int height){
        button.setBounds(x,y, width, height);
        button.setFont(new Font("Elephant", Font.BOLD, 25));
        button.setBackground(Color.WHITE);
        panel.add(button);
    }
    private void setGetName(){
        getName = new JLabel("Enter your name:");
        getName.setBounds(100,100, 300, 50);
        getName.setFont(new Font("Elephant", Font.BOLD, 30));
        getName.setForeground(Color.WHITE);
        panel.add(getName);
    }
    private void setTextField(){
        textField = new JTextField();
        textField.setBounds(100, 200, 400, 50);
        textField.setFont(new Font("Elephant", Font.BOLD, 25));
        panel.add(textField);
    }
    private void setNext(){
        next = new JButton("Next");
        addButton(next, 350, 350, 150, 50);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gameFrame.getGameManager().getClientHandler().sendName(textField.getText());
                panel.remove(getName);
                panel.remove(textField);
                panel.remove(next);
                new MainMenu(gameFrame);
            }
        });
    }

}
