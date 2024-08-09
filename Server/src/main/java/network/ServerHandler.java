package network;

import controller.CliThread;
import model.Server;
import model.Squad;
import network.TCP.TCPServer;
import network.UDP.UDPServer;

import java.util.ArrayList;

public class ServerHandler {
    private Server server;
    private TCPServer tcpServer;
    private static ServerHandler instance;
    private static ArrayList<String> unmatchedSquads;
    private ServerHandler(){
        server = new Server();
        server.load();
        tcpServer = new TCPServer(1000);
    }
    public void initiate(){
        new CliThread().start();
        tcpServer.start();
    }

    public static ServerHandler getInstance() {
        if (instance == null){
            instance = new ServerHandler();
        }
        return instance;
    }

    public Server getServer() {
        return server;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }
    public void initiateSquadBattle(){
        unmatchedSquads = new ArrayList<>();
        unmatchedSquads.addAll(server.getSquadsName());
        for (int i = 0; i < server.getSquadsName().size(); i++){
            if (!server.getSquads().get(server.getSquadsName().get(i)).isInBattle() && unmatchedSquads.size()>1){
                unmatchedSquads.remove(server.getSquadsName().get(i));
                int random = (int)(Math.random()*unmatchedSquads.size()-1);
                String squad = unmatchedSquads.get(random);
                Squad squad1 = server.getSquads().get(server.getSquadsName().get(i));
                Squad squad2 = server.getSquads().get(squad);
                squad1.setInBattle(true);
                squad1.setCompetitorSquad(squad2.getName());
                squad2.setInBattle(true);
                squad2.setCompetitorSquad(squad1.getName());
                unmatchedSquads.remove(random);
            }
        }
    }
    public void terminateBattle(){
        for (int i = 0; i < server.getSquadsName().size(); i++){
            server.getSquads().get(server.getSquadsName().get(i)).setInBattle(false);
            server.getSquads().get(server.getSquadsName().get(i)).setCompetitorSquad(null);
        }
    }
}
