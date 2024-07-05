
import controller.Controller;
import save.Save;

public class MyProject implements Runnable{

    @Override
    public void run() {
        try {
            Controller.runGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Save.load();
    }
}
