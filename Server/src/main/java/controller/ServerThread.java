package controller;

import application.MyApplication;
import model.Server;

public class ServerThread extends Thread{
    private Server server;
    public ServerThread(Server server){
        this.server = server;
    }
    public void run(){
        while (true){
            MyApplication.readerWriter.saveServerData(server);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
