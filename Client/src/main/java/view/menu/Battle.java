package view.menu;

import controller.save.Configs;
import model.Requests;
import model.Squad;
import model.Status;
import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Battle {
    private GameFrame gameFrame;
    private JPanel panel;
    private JScrollPane scroller;
    private JPanel scrollerPanel;
    private JButton back;
    public Battle(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();
        addScroller();
        addBack();
        gameFrame.update();
    }
    private void addScroller() {
        scrollerPanel = new JPanel();
        scrollerPanel.setLayout(new BoxLayout(scrollerPanel, BoxLayout.Y_AXIS));
        scroller = new JScrollPane(scrollerPanel);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setBounds(Configs.FRAME_SIZE.width/2-300,100,600,500);
        panel.add(scroller);
        addData();
    }
    private void addData() {
        ClientHandler clientHandler = gameFrame.getApplicationManager().getClientHandler();
        Squad squad = clientHandler.getCompetitor();
        ArrayList<String> members = squad.getMembers();
        for (int i = 0; i < members.size(); i++) {
            synchronized (clientHandler.getLock()) {
                clientHandler.getTcpClient().getListener().sendMessage(Requests.CLIENT_DATA.toString());
                clientHandler.getTcpClient().getListener().sendMessage(members.get(i));
                String xp = clientHandler.getUdpClient().getReceiver().getString();
                String status = clientHandler.getUdpClient().getReceiver().getString();
                addClientPanel(members.get(i), xp, status);
            }
        }
    }
    private void addClientPanel(String name, String xp, String status) {
        JPanel memberPanel = new JPanel();
        memberPanel.setName(name);
        JLabel label = new JLabel(name+"       "+xp+"XPs         "+status);
        label.setFont(new Font("Serif", Font.PLAIN,25));
        label.setForeground(Color.BLACK);
        memberPanel.add(label);
            memberPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    JPanel panel1 = (JPanel) e.getSource();
                    if (status.equals(Status.ONLINE.toString())){
                        int battle = battleRequest();
                        if (battle == 0) {
                            gameFrame.getApplicationManager().getClientHandler().sendBattleRequest(panel1.getName(), battle);
                        }
                    }
                }
            });
        scrollerPanel.add(memberPanel);
    }
    private int battleRequest(){
        String[] options = new String[]{"Monomachia", "Cancel"};
        int pick = JOptionPane.showOptionDialog(null, "Do you want to request for a battle?",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return pick;
    }
    private void addBack(){
        back = new JButton("Back");
        back.setBounds(100, Configs.FRAME_SIZE.height-200, 150, 50);
        back.setFont(new Font("Elephant", Font.BOLD, 25));
        back.setBackground(Color.WHITE);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                new MySquad(gameFrame);
            }
        });
    }
}
