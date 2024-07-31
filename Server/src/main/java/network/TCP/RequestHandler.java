package network.TCP;

import application.MyApplication;
import model.Client;
import model.Server;
import network.ServerHandler;

import java.io.File;

public class RequestHandler {
    public static void checkRequest(String request, ServerListener listener){
        if (request.equals("name")){
            sendClient(listener);
        }
        else if (request.equals("join")){
            joinRequest(listener);
        }
    }
    private static void sendClient(ServerListener listener){
        String name = listener.getMessage();
        Client client = ServerHandler.getInstance().getServer().getClients().get(name);
        if (client == null){
            client = new Client(name);
        }
        File file = MyApplication.readerWriter.convertToFile(client);
        ServerHandler.getInstance().getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
        listener.setClient(client);
    }
    private static void joinRequest(ServerListener listener){
        String squadName = listener.getMessage();
        Server server = ServerHandler.getInstance().getServer();
        server.getClients().get(server.getSquads().get(squadName).getOwner()).getRequests().add(listener.getClient().getUsername());
    }
}
