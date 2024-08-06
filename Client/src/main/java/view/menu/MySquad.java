package view.menu;

import controller.save.Configs;
import model.Requests;
import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
        setActions();
        addBack();
        addBattle();
        addLeave();
        addMembers();
        gameFrame.update();
    }
    private void setActions(){
        action1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                new MainMenu(gameFrame);
            }
        };
        action2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.add(back);
                addBattle();
                addMembers();
                addLeave();
                back.addActionListener(action1);
                back.removeActionListener(action2);
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
        membersPanel.removeAll();
        ClientHandler clientHandler = gameFrame.getApplicationManager().getClientHandler();
        ArrayList<String> members = clientHandler.getClient().getSquad().getMembers();
        boolean owner = clientHandler.getClient().getUsername().equals(clientHandler.getClient().getSquad().getOwner());
        for (int i = 0; i < members.size(); i++) {
            synchronized (clientHandler.getLock()) {
                clientHandler.getTcpClient().getListener().sendMessage(Requests.CLIENT_DATA.toString());
                clientHandler.getTcpClient().getListener().sendMessage(members.get(i));
                String xp = clientHandler.getUdpClient().getReceiver().getString();
                String status = clientHandler.getUdpClient().getReceiver().getString();
                addClientPanel(members.get(i), xp, owner, status);
            }
        }
    }
    private void addClientPanel(String name, String xp, boolean owner, String status) {
        JPanel memberPanel = new JPanel();
        memberPanel.setName(name);
        JLabel label = new JLabel(name+"       "+xp+"XPs         "+status);
        label.setFont(new Font("Serif", Font.PLAIN,25));
        label.setForeground(Color.BLACK);
        memberPanel.add(label);
        if (owner) {
            memberPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    JPanel p = (JPanel) e.getSource();
                    if (deleteMember(p.getName())){
                        gameFrame.getApplicationManager().getClientHandler().deleteMember(p.getName());
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
                panel.removeAll();
                panel.add(back);
                addScroller();
                back.addActionListener(action2);
                back.removeActionListener(action1);
                gameFrame.update();
            }
        });
    }
    private void addBattle(){
        battle = new JButton("Battle");
        addButton(battle, Configs.FRAME_SIZE.width/2-200, 200, 400, 100);
        battle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameFrame.getApplicationManager().getClientHandler().getClient().getSquad().isInBattle()){
                    panel.removeAll();
                    new Battle(gameFrame);
                }
                else {
                    String[] options = new String[]{"Ok"};
                    JOptionPane.showOptionDialog(null, "No battles yet!",
                            null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                }
            }
        });
    }
    private void addLeave(){
        leave = new JButton("Leave");
        addButton(leave, Configs.FRAME_SIZE.width/2-200, 350, 400, 100);
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientHandler clientHandler = gameFrame.getApplicationManager().getClientHandler();
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
}
