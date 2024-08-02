package view.menu;

import controller.save.Configs;
import model.Squad;
import model.Status;
import network.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class Battle {
    private GameFrame gameFrame;
    private JPanel panel;
    private JScrollPane scroller;
    private JPanel scrollerPanel;
    private JButton back;
    public Battle(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        panel = gameFrame.getGamePanel();
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
        ClientHandler clientHandler = gameFrame.getGameManager().getClientHandler();
        Squad squad = clientHandler.getCompetitor();
        ArrayList<Map<String, String>> maps = squad.getMembers();
        for (int i = 0; i < maps.size(); i++) {
            addClientPanel(maps.get(i).get("name"), maps.get(i).get("xp"), maps.get(i).get("status"));
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
                        gameFrame.getGameManager().getClientHandler().sendBattleRequest(panel1.getName(), battle);
                    }
                }
            });
        scrollerPanel.add(memberPanel);
    }
    private int battleRequest(){
        String[] options = new String[]{"Monomachia", "Colosseum"};
        int pick = JOptionPane.showOptionDialog(null, "Which battle do you request for?",
                null, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return pick;
    }
}
