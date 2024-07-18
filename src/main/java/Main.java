import model.enemies.normal.archmire.Archmire;
import model.enemies.normal.archmire.MiniArchmire;
import model.interfaces.movement.Point;
import org.apache.log4j.xml.DOMConfigurator;

public class Main {
    public static void main(String[] args) {
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        new MyProject().run();
    }
}
