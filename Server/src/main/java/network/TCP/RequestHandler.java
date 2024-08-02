package network.TCP;

import application.MyApplication;
import model.*;
import network.ServerHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {
    public static void checkRequest(String request, ServerListener listener){
        if (request.equals(Requests.NAME.toString())){
            sendClient(listener);
        }
        else if (request.equals(Requests.JOIN.toString())){
            joinRequest(listener);
        }
        else if (request.equals(Requests.CREATE_SQUAD.toString())){
            createSquad(listener);
        }
        else if (request.equals(Requests.DELETE_MEMBER.toString())){
            removeMember(listener);
        }
        else if (request.equals(Requests.SQUADS.toString())){
            sendSquads(listener);
        }
        else if (request.equals(Requests.SQUAD.toString())){
            sendSquad(listener);
        }
        else if (request.equals(Requests.BATTLE_REQUEST.toString())) {
            battleRequest(listener);
        }
        else if (request.equals(Requests.ACCEPTED.toString())){
            requestAccepted(listener);
        }
    } 
    private static void sendClient(ServerListener listener){
        String name = listener.getMessage();
        Client client = ServerHandler.getInstance().getServer().getClients().get(name);
        if (client == null){
            client = new Client(name);
            ServerHandler.getInstance().getServer().getClients().put(name, client);
        }
        File file = MyApplication.readerWriter.convertToFile(client);
        ServerHandler.getInstance().getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
        listener.setClient(client);
    }
    private static void joinRequest(ServerListener listener){
        String squadName = listener.getMessage();
        Server server = ServerHandler.getInstance().getServer();
        String requestName = "Join";
        String sender = listener.getClient().getUsername();
        String receiver = server.getSquads().get(squadName).getOwner();
        Request request = new Request(requestName, sender, receiver);
        server.getClients().get(sender).getSentRequests().add(request);
        server.getClients().get(receiver).getReceivedRequests().add(request);
        server.getRequests().put(request.getID(), request);
    }
    private static void createSquad(ServerListener listener){
       String squadName = listener.getMessage();
       Server server = ServerHandler.getInstance().getServer();
       if (server.getSquads().get(squadName) != null){
           listener.sendMessage(Requests.DECLINED.toString());
       }
       else {
           listener.sendMessage(Requests.ACCEPTED.toString());
           String name = listener.getMessage();
           Squad squad = new Squad(server.getClients().get(name), squadName);
           File squadFile = MyApplication.readerWriter.convertToFile(squad);
           ServerHandler.getInstance().getUdpServer().getSender().sendFile(squadFile, listener.getSocketAddress());
       }
    }
    private static void removeMember(ServerListener listener){
        String squadName = listener.getMessage();
        String name = listener.getMessage();
        ServerHandler.getInstance().getServer().getSquads().get(squadName).removeMember(name);
        ServerHandler.getInstance().getServer().getClients().get(name).setSquad(null);
    }
    private static void sendSquads(ServerListener listener){
        ArrayList<String> squads = ServerHandler.getInstance().getServer().getSquadsName();
        ServerHandler.getInstance().getUdpServer().getSender().sendString(squads.size()+"", listener.getSocketAddress());
        for (int i = 0; i < squads.size(); i++){
            ServerHandler.getInstance().getUdpServer().getSender().sendString(squads.get(i), listener.getSocketAddress());
        }
    }
    private static void sendSquad(ServerListener listener){
        String name = listener.getMessage();
        Squad squad = ServerHandler.getInstance().getServer().getSquads().get(name);
        File file = MyApplication.readerWriter.convertToFile(squad);
        ServerHandler.getInstance().getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
        System.out.println("sent");
    }
    private static void battleRequest(ServerListener listener){
        String sender = listener.getMessage();
        String receiver = listener.getMessage();
        int battle = Integer.parseInt(listener.getMessage());
        Server server = ServerHandler.getInstance().getServer();
        String requestName = null;
        if (battle == 0){
            if (server.getClients().get(receiver).isMonomachia()){
                sendMessage(listener, receiver+" has played monomachia battle!");
            }
            else {
                requestName = "Monomachia battle";
            }
        }
        else {
            if (server.getClients().get(receiver).isColosseum()){
                sendMessage(listener, receiver+" has played colosseum battle!");
            }
            else {
                requestName = "Colosseum battle";
            }
        }
        if (requestName != null) {
            Request request = new Request(requestName, sender, receiver);
            server.getClients().get(sender).getSentRequests().add(request);
            server.getClients().get(receiver).getReceivedRequests().add(request);
            server.getRequests().put(request.getID(), request);
        }
    }
    private static void sendMessage(ServerListener listener, String message){
        listener.sendMessage(Requests.MESSAGE.toString());
        listener.sendMessage(message);
    }
    private static void requestAccepted(ServerListener listener){
        String requestID = listener.getMessage();
        Server server = ServerHandler.getInstance().getServer();
        server.getRequests().get(requestID).setAccepted(true);
        if (server.getRequests().get(requestID).getRequestName().equals("join")){
            Client client = server.getClients().get(server.getRequests().get(requestID).getSender());
            listener.getClient().getSquad().getMembers().add(client.getUsername());
            client.setSquad(listener.getClient().getSquad());
        }
        server.getRequests().put(requestID, null);
    }
}
