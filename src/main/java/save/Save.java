package save;

import controller.GameManager;
import model.skills.Skill;
import model.skills.WritOfAceso;
import model.skills.attack.WritOfAres;
import model.skills.WritOfProteus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Save {
    private static File file;
    public static void save() {
    file = new File("data");
    try{
        if (!file.exists()) {
        file.createNewFile();
    }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(GameManager.getINSTANCE().getTotalXP()+"\n");
            if (WritOfAres.isAresUnlocked()) {
                fileWriter.append("unlocked"+"\n");
            }
            else {
               fileWriter.append("locked"+"\n");
            }
            if (WritOfAceso.isAcesoUnlocked()) {
                fileWriter.append("unlocked"+"\n");
            }
            else {
                fileWriter.append("locked"+"\n");
            }
            if (WritOfProteus.isProteusUnlocked()) {
                fileWriter.append("unlocked"+"\n");
            }
            else {
                fileWriter.append("locked"+"\n");
            }
            Skill skill = GameManager.getINSTANCE().getPickedSkill();
            if (skill != null) {
                if (skill instanceof WritOfAres) {
                    fileWriter.append("1\n");
                } else if (skill instanceof WritOfAceso) {
                    fileWriter.append("2\n");
                }
                else {
                    fileWriter.append("3\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void load() {
        file = new File("data");
        Scanner sc = null;
        try{
        if (!file.exists()) {
            file.createNewFile();
        }
            sc = new Scanner(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int x = 0;
        while (sc.hasNext()) {
            if (x == 0) {
                GameManager.getINSTANCE().setTotalXP(Integer.parseInt(sc.next()));
            }
            else if (x == 1) {
                if (sc.next().equals("unlocked")) {
                    WritOfAres.setAresUnlocked(true);
                }
            }
            else if (x == 2) {
                if (sc.next().equals("unlocked")) {
                    WritOfAceso.setAcesoUnlocked(true);
                }
            }
            else if (x == 3) {
                if (sc.next().equals("unlocked")) {
                    WritOfProteus.setProteusUnlocked(true);
                }
            }
            else if (x == 4) {
                int p = Integer.parseInt(sc.next());
                if (p == 1) {
                    GameManager.getINSTANCE().setPickedSkill(new WritOfAres());
                    WritOfAres.setPicked(true);
                }
                else if (p == 2) {
                    GameManager.getINSTANCE().setPickedSkill(new WritOfAceso());
                    WritOfAceso.setPicked(true);
                }
                else if (p == 3) {
                    GameManager.getINSTANCE().setPickedSkill(new WritOfProteus());
                    WritOfProteus.setPicked(true);
                }
            }
            x++;
        }
    }
}
