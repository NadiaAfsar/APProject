package controller.save;

import application.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Client;
import model.Request;
import model.Server;
import model.Squad;

import java.io.*;

public class ReaderWriter {
    private Gson gson;
    public ReaderWriter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new MyExclusionStrategy());
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }
    private <T> T load(Class<T> tClass, String address) {
        File file = new File(address);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader iSReader = new InputStreamReader(fileInputStream);
        JsonReader reader = new JsonReader(iSReader);

        return gson.fromJson(reader,tClass);
    }
    public <T> T getObject(Class<T> tClass, File file){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader iSReader = new InputStreamReader(fileInputStream);
        JsonReader reader = new JsonReader(iSReader);

        return gson.fromJson(reader,tClass);
    }
    public <T> File convertToFile(T object, String name){
        File file = new File("src/main/resources/data/files/"+name);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            gson.toJson(object, object.getClass(), bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
    private <T> void save(String address, T configs) {
        File file = new File(address);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            gson.toJson(configs, configs.getClass(), bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveServerData(Server server){
        Configs configs = new Configs();
        configs.setClients(server.getClientsName());
        configs.setSquads(server.getSquadsName());
        configs.setRequests(server.getRequestsName());
        save("src/main/resources/data/configs.json", configs);
        for (int i = 0; i < server.getClientsName().size(); i++){
            save("src/main/resources/data/clients/"+server.getClientsName().get(i)+".json", server.getClients().
                    get(server.getClientsName().get(i)));
        }
        for (int i = 0; i < server.getSquadsName().size(); i++){
            save("src/main/resources/data/squads/"+server.getSquadsName().get(i)+".json", server.getSquads().
                    get(server.getSquadsName().get(i)));
        }
        for (int i = 0; i < server.getRequestsName().size(); i++){
            save("src/main/resources/data/requests/"+server.getRequestsName().get(i)+".json", server.getRequests().
                    get(server.getRequestsName().get(i)));
        }
    }
    public void loadServerData(Server server){
        Configs configs = load(Configs.class, "src/main/resources/data/configs.json");
        if (configs != null) {
            server.setClientsName(configs.getClients());
            server.setSquadsName(configs.getSquads());
            server.setRequestsName(configs.getRequests());
            for (int i = 0; i < server.getClientsName().size(); i++) {
                server.getClients().put(server.getClientsName().get(i), load(Client.class, "src/main/resources/data/clients/" +
                        server.getClientsName().get(i) + ".json"));
            }
            for (int i = 0; i < server.getSquadsName().size(); i++) {
                server.getSquads().put(server.getSquadsName().get(i), load(Squad.class, "src/main/resources/data/squads/"
                        + server.getSquadsName().get(i) + ".json"));
            }
            for (int i = 0; i < server.getRequestsName().size(); i++) {
                server.getRequests().put(server.getRequestsName().get(i), load(Request.class, "src/main/resources/data/requests/" +
                        server.getRequestsName().get(i) + ".json"));
            }
        }
    }

}
