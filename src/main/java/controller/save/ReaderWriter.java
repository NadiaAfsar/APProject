package controller.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class ReaderWriter {
    private Gson gson;
    public ReaderWriter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
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
    public Configs getConfigs() {
        return load(Configs.class, "src/main/resources/data/config.json");
    }
}
