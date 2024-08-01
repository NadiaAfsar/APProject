package network.TCP;

import application.MyApplication;
import model.Client;
import model.Requests;
import model.Server;
import model.Squad;
import network.ServerHandler;

import java.io.File;
import java.util.ArrayList;

public class RequestHandler {
    public static void checkRequest(Object request, ServerListener listener){
        if (request.equals(Requests.NAME)){
            sendClient(listener);
        }
        else if (request.equals(Requests.JOIN)){
            joinRequest(listener);
        }
        else if (request.equals(Requests.CREATE_SQUAD)){
            createSquad();
        }
        else if (request.equals(Requests.DELETE_MEMBER)){
            removeMember(listener);
        }
        else if (request.equals(Requests.SQUADS)){
            sendSquads(listener);
        }
        else if (request.equals(Requests.MEMBERS)){
            sendSquad(listener);
        }
    } 
    private static void sendClient(ServerListener listener){
        String name = listener.getMessage().toString();
        Client client = ServerHandler.getInstance().getServer().getClients().get(name);
        if (client == null){
            client = new Client(name);
        }
        File file = MyApplication.readerWriter.convertToFile(client);
        ServerHandler.getInstance().getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
        listener.setClient(client);
    }
    private static void joinRequest(ServerListener listener){
        String squadName = listener.getMessage().toString();
        Server server = ServerHandler.getInstance().getServer();
        server.getClients().get(server.getSquads().get(squadName).getOwner()).getRequests().add(listener.getClient().getUsername());
    }
    private static void createSquad(){
        File file = ServerHandler.getInstance().getUdpServer().getReceiver().getFile();
        Squad squad = MyApplication.readerWriter.getObject(Squad.class, file);
        Server server = ServerHandler.getInstance().getServer();
        server.getSquads().put(squad.getName(), squad);
        server.getClients().get(squad.getOwner()).setSquad(squad);
    }
    private static void removeMember(ServerListener listener){
        String squadName = listener.getMessage().toString();
        String name = listener.getMessage().toString();
        ServerHandler.getInstance().getServer().getSquads().get(squadName).removeMember(name);
        ServerHandler.getInstance().getServer().getClients().get(name).setSquad(null);
    }
    private static void sendSquads(ServerListener listener){
        ArrayList<String> squads = ServerHandler.getInstance().getServer().getSquadsName();
        for (int i = 0; i < squads.size(); i++){
            ServerHandler.getInstance().getUdpServer().getSender().sendString(squads.get(i), listener.getSocketAddress());
        }
    }
    private static void sendSquad(ServerListener listener){
        String name = listener.getMessage().toString();
        Squad squad = ServerHandler.getInstance().getServer().getSquads().get(name);
        File file = MyApplication.readerWriter.convertToFile(squad);
        ServerHandler.getInstance().getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
    }
}
