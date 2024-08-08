package controller.save;

import application.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

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
    public Configs getConfigs() {
        return load(Configs.class, "src/main/resources/data/config.json");
    }

}
