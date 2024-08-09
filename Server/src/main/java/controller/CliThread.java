package controller;

import network.ServerHandler;

import java.util.Scanner;

public class CliThread extends Thread{
    public void run(){
        while (true){
            System.out.println("----------------------------------");
            System.out.println("1-initiate battle");
            System.out.println("2-terminate battle");
            Scanner scanner = new Scanner(System.in);
            String pick = scanner.nextLine();
            if (pick.equals("1")){
                ServerHandler.getInstance().initiateSquadBattle();
            }
            else if (pick.equals("2")){
                ServerHandler.getInstance().terminateBattle();
            }
        }
    }
}
