package network.TCP;

import application.MyApplication;
import controller.GameManagerHelper;
import model.*;
import model.Point;
import network.ServerHandler;
import network.ServerListener;

import java.io.File;
import java.util.ArrayList;

public class RequestHandler {
    public static void checkRequest(String request, ServerListener listener){
            if (request.equals(Requests.NAME.toString())) {
                sendClient(listener);
            } else if (request.equals(Requests.JOIN.toString())) {
                joinRequest(listener);
            } else if (request.equals(Requests.CREATE_SQUAD.toString())) {
                createSquad(listener);
            } else if (request.equals(Requests.DELETE_MEMBER.toString())) {
                removeMember(listener);
            } else if (request.equals(Requests.SQUADS.toString())) {
                sendSquads(listener);
            } else if (request.equals(Requests.SQUAD.toString())) {
                sendSquad(listener);
            } else if (request.equals(Requests.BATTLE_REQUEST.toString())) {
                battleRequest(listener);
            } else if (request.equals(Requests.ACCEPTED.toString())) {
                requestAccepted(listener);
            } else if (request.equals(Requests.CLIENT.toString())) {
                client(listener);
            } else if (request.equals(Requests.RECEIVED.toString())) {
                received(listener);
            } else if (request.equals(Requests.SENT.toString())) {
                sent(listener);
            } else if (request.equals(Requests.DECLINED.toString())) {
                requestDeclined(listener);
            }
            else if (request.equals(Requests.CLIENT_DATA.toString())){
                clientData(listener);
            }
            else if (request.equals(Requests.RUNNING_GAME.toString())){
                sendGame(listener);
            }
            else if (request.equals(Requests.SEND_UPDATE.toString())){
                getUpdates(listener);
            }
            else if (request.equals(Requests.RECEIVE_UPDATE.toString())){
                sendUpdates(listener);
            }
    }
    private static void addEnemy(ServerListener listener){
        int enemies = Integer.parseInt(listener.getMessage());
        Point point = GameManagerHelper.getRandomPosition(400, 400);
        int random = (int)(Math.random()*enemies);
        Entity enemy = new Entity((int)point.getX(), (int)point.getY(), random);
        listener.getClient().getRunningGame().getAddedEnemies().get(listener.getClient()).add(enemy);
        listener.getClient().getRunningGame().getAddedEnemies().get(listener.getClient().getRunningGame().
                getClientsInGame().get(1)).add(enemy);
    }
    private static void shootBullet(ServerListener listener){
        File bulletFile = listener.getUdpServer().getReceiver().getFile();
        Entity bullet = MyApplication.readerWriter.getObject(Entity.class, bulletFile);
        listener.getClient().getRunningGame().getBullets().get(listener.getClient()).add(bullet);
        bulletFile.delete();
    }
    private static void getUpdates(ServerListener listener){
        if (listener.getClient().getRunningGame().isFinished()){
            listener.sendMessage(Requests.FINISHED.toString());
        }
        else {
            listener.sendMessage("nothing");
        }
        File epsilon = listener.getUdpServer().getReceiver().getFile();
        Entity epsilonModel = MyApplication.readerWriter.getObject(Entity.class, epsilon);
        RunningGame game = listener.getClient().getRunningGame();
        game.getEpsilons().put(listener.getClient(), epsilonModel);
        epsilon.delete();
        int xp = Integer.parseInt(listener.getMessage());
        int hp = Integer.parseInt(listener.getMessage());
        game.getClientData().get(listener.getClient()).put("xp", xp);
        game.getClientData().get(listener.getClient()).put("hp", hp);
        int bullets = Integer.parseInt(listener.getMessage());
        for (int i = 0; i < bullets; i++){
            shootBullet(listener);
        }
        int newEnemies = Integer.parseInt(listener.getMessage());
        for (int i = 0; i < newEnemies; i++){
            addEnemy(listener);
        }
    }
    private static void sendUpdates(ServerListener listener){
        RunningGame game = listener.getClient().getRunningGame();
        listener.sendMessage(game.getClientsInGame().size()+"");
        for (int i = 0; i < game.getClientsInGame().size(); i++){
            if (i != listener.getClient().getPlayerNumber()) {
                Entity entity = game.getEpsilons().get(game.getClientsInGame().get(i));
                File epsilon = MyApplication.readerWriter.convertToFile(entity, entity.getID());
                listener.getUdpServer().getSender().sendFile(epsilon, listener.getSocketAddress());
                epsilon.delete();
                listener.sendMessage(game.getClientData().get(game.getClientsInGame().get(i)).get("xp")+"");
                listener.sendMessage(game.getClientData().get(game.getClientsInGame().get(i)).get("hp")+"");
                ArrayList<Entity> bullets = game.getBullets().get(game.getClientsInGame().get(i));
                listener.sendMessage(bullets.size()+"");
                for (int j = 0; j < bullets.size(); j++){
                    File bullet = MyApplication.readerWriter.convertToFile(bullets.get(j), bullets.get(j).getID());
                    listener.getUdpServer().getSender().sendFile(bullet, listener.getSocketAddress());
                    bullet.delete();
                }
                game.getBullets().put(game.getClientsInGame().get(i), new ArrayList<>());
            }
            ArrayList<Entity> enemies = game.getAddedEnemies().get(game.getClientsInGame().get(i));
            listener.sendMessage(enemies.size()+"");
            for (int j = 0; j < enemies.size(); j++){
                File enemy = MyApplication.readerWriter.convertToFile(enemies.get(j), j+enemies.get(j).getID()+listener.getClient().getUsername());
                listener.getUdpServer().getSender().sendFile(enemy, listener.getSocketAddress());
                enemy.delete();
            }
        }
        game.setEnemiesAdded(game.getEnemiesAdded()+1);
        if (game.getEnemiesAdded() == game.getClientsInGame().size()) {
            for (int i = 0; i < game.getClientsInGame().size(); i++){
                game.getAddedEnemies().put(game.getClientsInGame().get(i), new ArrayList<>());
                game.setEnemiesAdded(0);
            }
        }
    }
    private static void sendGame(ServerListener listener){
        if (listener.getClient().getRunningGame() != null){
            listener.getClient().setStatus(Status.BUSY);
            listener.sendMessage(listener.getClient().getRunningGame().getGame());
            listener.sendMessage(listener.getClient().getPlayerNumber()+"");
        }
        else {
            listener.sendMessage("nothing");
        }
    }

    private static void received(ServerListener listener){
            String ID = listener.getMessage();
            Request request = ServerHandler.getInstance().getServer().getRequests().get(ID);
            listener.getClient().getReceivedRequests().remove(request);
    }
    private static void sent(ServerListener listener){
            String ID = listener.getMessage();
            Request request = ServerHandler.getInstance().getServer().getRequests().get(ID);
            listener.getClient().getSentRequests().remove(request);

    }
    private static void client(ServerListener listener){
            String name = listener.getMessage();
            Client client = ServerHandler.getInstance().getServer().getClients().get(name);
            if (client != null) {
                File file = MyApplication.readerWriter.convertToFile(client, client.getID());
                listener.getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
                file.delete();
            }
    }
    private static void sendClient(ServerListener listener){
            String name = listener.getMessage();
            Client client = ServerHandler.getInstance().getServer().getClients().get(name);
            if (client == null) {
                client = new Client(name, listener);
                ServerHandler.getInstance().getServer().getClients().put(name, client);
            }
            client.setStatus(Status.ONLINE);
            File file = MyApplication.readerWriter.convertToFile(client, client.getID());
            listener.getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
            listener.setClient(client);
            file.delete();
    }
    private static void joinRequest(ServerListener listener){
            String squadName = listener.getMessage();
            Server server = ServerHandler.getInstance().getServer();
            String requestName = "join";
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
            if (server.getSquads().get(squadName) != null) {
                listener.sendMessage(Requests.DECLINED.toString());
            } else {
                listener.sendMessage(Requests.ACCEPTED.toString());
                String name = listener.getMessage();
                Squad squad = new Squad(server.getClients().get(name), squadName);
                server.getClients().get(name).setSquad(squad);
                server.getSquads().put(squad.getName(), squad);
                server.getSquadsName().add(squadName);
                File squadFile = MyApplication.readerWriter.convertToFile(squad, squad.getID());
                listener.getUdpServer().getSender().sendFile(squadFile, listener.getSocketAddress());
                squadFile.delete();
            }
            if (ServerHandler.getInstance().getServer().getSquadsName().size() >= 2) {
                ServerHandler.getInstance().initiateSquadBattle();
            }
    }
    private static void removeMember(ServerListener listener){
            String squadName = listener.getMessage();
            String name = listener.getMessage();
            Server server = ServerHandler.getInstance().getServer();
            server.getSquads().get(squadName).removeMember(name);
            server.getClients().get(name).setSquad(null);
            if (server.getSquads().get(squadName).getMembers().size() == 0){
                server.getSquadsName().remove(squadName);
                server.getSquads().put(squadName, null);
            }

    }
    private static void sendSquads(ServerListener listener){
            ArrayList<String> squads = ServerHandler.getInstance().getServer().getSquadsName();
            listener.getUdpServer().getSender().sendString(squads.size() + "", listener.getSocketAddress());
            for (int i = 0; i < squads.size(); i++) {
                listener.getUdpServer().getSender().sendString(squads.get(i), listener.getSocketAddress());
            }
    }
    private static void sendSquad(ServerListener listener){
            String name = listener.getMessage();
            Squad squad = ServerHandler.getInstance().getServer().getSquads().get(name);
            File file = MyApplication.readerWriter.convertToFile(squad, squad.getID());
            listener.getUdpServer().getSender().sendFile(file, listener.getSocketAddress());
            file.delete();
    }
    private static void battleRequest(ServerListener listener){
            String sender = listener.getMessage();
            String receiver = listener.getMessage();
            int battle = Integer.parseInt(listener.getMessage());
            Server server = ServerHandler.getInstance().getServer();
            String requestName = null;
            boolean declined = false;
            if (battle == 0) {
                if (server.getClients().get(receiver).isMonomachia()) {
                    declined = true;
                } else {
                    requestName = "Monomachia battle";
                }
            } else {
                if (server.getClients().get(receiver).isColosseum()) {
                    declined = true;
                } else {
                    requestName = "Colosseum battle";
                }
            }
            if (requestName != null) {
                Request request = new Request(requestName, sender, receiver);
                server.getClients().get(sender).getSentRequests().add(request);
                if (declined){
                    request.setDeclined(true);
                }
                else {
                    server.getClients().get(receiver).getReceivedRequests().add(request);
                    server.getRequests().put(request.getID(), request);
                }
            }
    }
    private static void requestAccepted(ServerListener listener){
            String requestID = listener.getMessage();
            Server server = ServerHandler.getInstance().getServer();
            server.getRequests().get(requestID).setAccepted(true);
            if (server.getRequests().get(requestID).getRequestName().equals("join")) {
                Client client = server.getClients().get(server.getRequests().get(requestID).getSender());
                listener.getClient().getSquad().getMembers().add(client.getUsername());
                client.setSquad(listener.getClient().getSquad());
            } else if (server.getRequests().get(requestID).getRequestName().equals("Monomachia battle")) {
                Request request = server.getRequests().get(requestID);
                ArrayList<Client> clients = new ArrayList<Client>(){{add(server.getClients().get(request.getSender()));
                add(server.getClients().get(request.getReceiver()));}};
                RunningGame game = new RunningGame(clients, Requests.MONOMACHIA.toString());
                for (int i = 0; i < clients.size(); i++){
                    clients.get(i).setRunningGame(game);
                    clients.get(i).setPlayerNumber(i);
                }
            }
    }
    private static void requestDeclined(ServerListener listener){
            String requestID = listener.getMessage();
            Server server = ServerHandler.getInstance().getServer();
            server.getRequests().get(requestID).setDeclined(true);
    }
    private static void clientData(ServerListener listener){
            String name = listener.getMessage();
            Server server = ServerHandler.getInstance().getServer();
            listener.getUdpServer().getSender().sendString(server.getClients().get(name).getXP() + "",
                    listener.getSocketAddress());
            listener.getUdpServer().getSender().sendString(server.getClients().get(name).getStatus().toString(),
                    listener.getSocketAddress());
    }
}
