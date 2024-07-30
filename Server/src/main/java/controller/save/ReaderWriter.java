package controller.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import controller.GameManager;
import model.game.GameModel;

import java.io.*;

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
    private <T> void save(String address, T configs) {
        File file = new File(address);
        try {
            System.out.println(file.createNewFile());
            System.out.println(file.exists());
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
    public void saveConfigs() {
        save("src/main/resources/data/config.json", GameManager.configs);
    }
    public GameManager getGameManager(){
        GameManager gameManager = null;
        try{
            gameManager = load(GameManager.class, "src/main/resources/data/game.json");
        }
        catch (Exception e){
            return null;
        }
        return gameManager;
    }
    public void saveGameManger() {
        save("src/main/resources/data/game.json", GameManager.getINSTANCE());
    }
    public void realTimeProgressSave(){
        save("src/main/resources/data/progress.json", GameManager.getINSTANCE().getGameModel());
    }
    public GameModel getRealTimeProgress(){
        GameModel gameModel = null;
        try{
            gameModel = load(GameModel.class, "src/main/resources/data/progress.json");
        }
        catch (Exception e){
            return null;
        }
        return gameModel;
    }
    public void gameSave(){
        save("src/main/resources/data/game-model.json", GameManager.getINSTANCE().getGameModel());
    }
    public GameModel getGameModel(){
        GameModel gameModel = null;
        try{
            gameModel = load(GameModel.class, "src/main/resources/data/game-model.json");
        }
        catch (Exception e){
            return null;
        }
        return gameModel;
    }
    public void deleteSavedGames(){
        new File("src/main/resources/data/game-model.json").delete();
        new File("src/main/resources/data/progress.json").delete();
    }
}
