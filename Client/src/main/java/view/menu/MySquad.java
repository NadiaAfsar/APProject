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
import java.util.ArrayList;
import java.util.Map;

public class MySquad {
    private GameFrame gameFrame;
    private JScrollPane membersScroller;
    private JPanel membersPanel;
    private JPanel panel;
    private JButton members;
    private JButton leave;
    private JButton battle;
    private JButton back;
    private ActionListener action1;
    private ActionListener action2;
    public MySquad(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();
        addBattle();
        addLeave();
        addMembers();
        addBack();
        gameFrame.update();
    }
    private void setActions(){
        action1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                new MainMenu(gameFrame);
            }
        };
        action2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empty();
                addBattle();
                addMembers();
                addLeave();
                back.removeActionListener(action2);
                back.addActionListener(action1);
                gameFrame.update();
            }
        };
    }
    private void addScroller() {
        membersPanel = new JPanel();
        membersPanel.setLayout(new BoxLayout(membersPanel, BoxLayout.Y_AXIS));
        membersScroller = new JScrollPane(membersPanel);
        membersScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        membersScroller.setBounds(Configs.FRAME_SIZE.width/2-300,100,600,500);
        panel.add(membersScroller);
        addData();
    }
    private void addData() {
        ClientHandler clientHandler = gameFrame.getGameManager().getClientHandler();
        ArrayList<Map<String, String>> maps = clientHandler.getClient().getSquad().getMembers();
        boolean owner = clientHandler.getClient().getID().equals(clientHandler.getClient().getSquad().getOwner());
        for (int i = 0; i < maps.size(); i++) {
            addClientPanel(maps.get(i).get("name"), maps.get(i).get("xp"), owner);
        }
    }
    private void addClientPanel(String name, String xp, boolean owner) {
        JPanel memberPanel = new JPanel();
        memberPanel.setName(name);
        JLabel label = new JLabel(name+"       "+xp+"XPs");
        label.setFont(new Font("Serif", Font.PLAIN,25));
        label.setForeground(Color.BLACK);
        memberPanel.add(label);
        if (owner) {
            memberPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    JPanel p = (JPanel) e.getSource();
                    if (deleteMember(name)){
                        gameFrame.getGameManager().getClientHandler().deleteMember(name);
                        membersPanel.remove(memberPanel);
                    }
                }
            });
        }
        membersPanel.add(memberPanel);
    }
    private boolean deleteMember(String name){
        String[] options = new String[]{"Yes", "No"};
        int pick = JOptionPane.showOptionDialog(null, "Remove "+name+" from squad?",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (pick == 0){
            return true;
        }
        return false;
    }
    private void addButton(JButton button, int x, int y, int width, int height){
        button.setBounds(x,y, width, height);
        button.setFont(new Font("Elephant", Font.BOLD, 30));
        button.setBackground(Color.WHITE);
        panel.add(button);
    }
    private void addMembers(){
        members = new JButton("Members");
        addButton(members, Configs.FRAME_SIZE.width/2-200, 50, 400, 100);
        members.addActionListener(new ActionListener() {
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
    private void addBattle(){
        battle = new JButton("Battle");
        addButton(battle, Configs.FRAME_SIZE.width/2-200, 200, 400, 100);
    }
    private void addLeave(){
        leave = new JButton("Leave");
        addButton(leave, Configs.FRAME_SIZE.width/2-200, 350, 400, 100);
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientHandler clientHandler = gameFrame.getGameManager().getClientHandler();
                if (deleteMember(clientHandler.getClient().getUsername())){
                    clientHandler.deleteMember(clientHandler.getClient().getUsername());
                }
            }
        });
    }
    private void addBack(){
        back = new JButton("Back");
        addButton(back, 100, Configs.FRAME_SIZE.height-200, 150, 50);
        back.addActionListener(action1);
    }
    private void empty(){
        if (members != null){
            panel.remove(members);
        }
        if (membersScroller != null){
            panel.remove(membersScroller);
        }
        if (leave != null){
            panel.remove(leave);
        }
        if (battle != null){
            panel.remove(battle);
        }
    }
}
