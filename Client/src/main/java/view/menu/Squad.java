package view.menu;

import controller.save.Configs;
import network.ClientHandler;
import network.UDP.Receiver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Squad {
    private GameFrame gameFrame;
    private JScrollPane squadsScroller;
    private JPanel squadsScrollerPanel;
    private JButton squads;
    private JButton newSquad;
    private JLabel nameLabel;
    private JTextField textField;
    private JButton next;
    private JPanel panel;
    private JButton back;
    private ActionListener action1;
    private ActionListener action2;
    public Squad(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();
        if (gameFrame.getGameManager().getClientHandler().getClient().getSquad() == null){
            addActions();
            addSquadsButton();
            addNewSquad();
            addBack();
        }
        gameFrame.update();
    }

    private void addButton(JButton button, int x, int y, int width, int height){
        button.setBounds(x,y, width, height);
        button.setFont(new Font("Elephant", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        panel.add(button);
    }
    private void addActions(){
        action1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                panel.remove(back);
                new MainMenu(gameFrame);
            }
        };
        action2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addSquadsButton();
                addNewSquad();
                back.removeActionListener(action2);
                back.addActionListener(action1);
                gameFrame.update();
            }
        };
    }
    private void addBack(){
        back = new JButton("Back");
        addButton(back, 100, Configs.FRAME_SIZE.height-200, 150, 50);
        back.addActionListener(action1);
    }
    private void addNameLabel(){
        nameLabel = new JLabel("Enter a name:");
        nameLabel.setBounds(Configs.FRAME_SIZE.width/2-200,100, 300, 50);
        nameLabel.setFont(new Font("Elephant", Font.BOLD, 30));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
    }
    private void addTextField(){
        textField = new JTextField();
        textField.setBounds(Configs.FRAME_SIZE.width/2-200, 200, 400, 50);
        textField.setFont(new Font("Elephant", Font.BOLD, 25));
        panel.add(textField);
    }
    private void addNext(){
        next = new JButton("Next");
        addButton(next, Configs.FRAME_SIZE.width-250, Configs.FRAME_SIZE.height-200, 150, 50);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.getGameManager().getClientHandler().createNewSquad(textField.getText());
            }
        });
    }
    private void addSquadsButton(){
        squads = new JButton("Squads");
        addButton(squads, Configs.FRAME_SIZE.width/2-200, Configs.FRAME_SIZE.height/2-150, 400, 100);
        squads.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addScroller();
                back.removeActionListener(action1);
                back.addActionListener(action2);
                gameFrame.update();
            }
        });
    }
    private void addNewSquad(){
        newSquad = new JButton("Create New Squad");
        addButton(newSquad, Configs.FRAME_SIZE.width/2-200, Configs.FRAME_SIZE.height/2, 400, 100);
        newSquad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addNameLabel();
                addTextField();
                addNext();
                back.removeActionListener(action1);
                back.addActionListener(action2);
                gameFrame.update();
            }
        });
    }
    private void addScroller() {
        squadsScrollerPanel = new JPanel();
        squadsScrollerPanel.setLayout(new BoxLayout(squadsScrollerPanel, BoxLayout.Y_AXIS));
        squadsScroller = new JScrollPane(squadsScrollerPanel);
        squadsScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        squadsScroller.setBounds(Configs.FRAME_SIZE.width/2-300,100,600,500);
        panel.add(squadsScroller);
        //addData();
    }
    private void addData() {
        ClientHandler clientHandler = gameFrame.getGameManager().getClientHandler();
        clientHandler.getTcpClient().getListener().sendMessage("squads");
        Receiver receiver = clientHandler.getUdpClient().getReceiver();
        int requests = Integer.parseInt(receiver.getString());
        for (int i = 0; i < requests; i++) {
            String name = receiver.getString();
            addSquadPanel(name);
        }
    }
    private void addSquadPanel(String name) {
        JPanel requestPanel = new JPanel();
        requestPanel.setName(name);
        JLabel label = new JLabel(name);
        label.setFont(new Font("Serif", Font.PLAIN,25));
        label.setForeground(Color.BLACK);
        requestPanel.add(label);
        requestPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JPanel p = (JPanel) e.getSource();
                if (askToJoin()){
                    gameFrame.getGameManager().getClientHandler().sendJoinRequest(p.getName());
                }
            }
        });
        squadsScrollerPanel.add(requestPanel);
    }
    private boolean askToJoin(){
        String[] options = new String[]{"Yes", "No"};
        int pick = JOptionPane.showOptionDialog(null, "Ask to join?",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0){
            return true;
        }
        return false;
    }
    private void empty(){
        if (squadsScroller != null){
            panel.remove(squadsScroller);
        }
        if (squads != null){
            panel.remove(squads);
        }
        if (newSquad != null){
            panel.remove(newSquad);
        }
        if (nameLabel != null){
            panel.remove(nameLabel);
        }
        if(textField != null){
            panel.remove(textField);
        }
        if (next != null){
            panel.remove(next);
        }
    }
}
