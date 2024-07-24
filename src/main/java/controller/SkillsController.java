package controller;

import model.skills.WritOfAceso;
import model.skills.attack.WritOfAres;
import model.skills.WritOfProteus;

public class SkillsController {
    public static void setAres(boolean ares) {
        WritOfAres.setPicked(ares);
        if (ares) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfAres());
        }
    }

    public static void setAceso(boolean aceso) {
        WritOfAceso.setPicked(aceso);
        if (aceso) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfAceso());
        }
    }

    public static void setProteus(boolean proteus) {
        WritOfProteus.setPicked(proteus);
        if (proteus) {
            GameManager.getINSTANCE().setPickedSkill(new WritOfProteus());
        }
    }

    public static boolean isAresUnlocked() {
        return WritOfAres.isAresUnlocked();
    }

    public static void setAresUnlocked(boolean aresUnlocked) {
        WritOfAres.setAresUnlocked(aresUnlocked);

    }

    public static boolean isAcesoUnlocked() {
        return WritOfAceso.isAcesoUnlocked();
    }

    public static void setAcesoUnlocked(boolean acesoUnlocked) {
        WritOfAceso.setAcesoUnlocked(acesoUnlocked);
    }

    public static boolean isProteusUnlocked() {
        return WritOfProteus.isProteusUnlocked();
    }

    public static void setProteusUnlocked(boolean proteusUnlocked) {
        WritOfProteus.setProteusUnlocked(proteusUnlocked);
    }
    public static boolean isAresPicked() {
        return WritOfAres.isPicked();
    }
    public static boolean isAcesoPicked() {
        return WritOfAceso.isPicked();
    }
    public static boolean isProteusPicked() {
        return WritOfProteus.isPicked();
    }
}
