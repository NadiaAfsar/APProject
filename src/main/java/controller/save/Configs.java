package controller.save;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Configs {
    public static final Dimension FRAME_SIZE = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final int UPS = 100;
    public static final double FRAME_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/FPS;
    public static final double MODEL_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/UPS;
    public String EPSILON;
    public String SQUARANTINE;
    public String TRIGORATH;
    public String BACKGROUND;
    public String WYRM;
    public String OMENOCT;
    public String NECROPICK;
    public String ARCHMIRE;
    public String AOE_ATTACK;
    public int EPSILON_RADIUS;
    public int SQUARANTINE_WIDTH;
    public int TRIGORATH_WIDTH;
    public int TRIGORATH_HEIGHT;
    public int WYRM_WIDTH;
    public int WYRM_HEIGHT;
    public int OMENOCT_WIDTH;
    public int OMENOCT_HEIGHT;
    public int NECROPICK_WIDTH;
    public int NECROPICK_HEIGHT;
    public int ARCHMIRE_WIDTH;
    public int ARCHMIRE_HEIGHT;
    public int MINI_ARCHMIRE_WIDTH;
    public int MINI_ARCHMIRE_HEIGHT;
    public String BARRICODES;
    public int BARRICODES_WIDTH;
    public int BARRICODES_HEIGHT;
    public String BLACK_ORB_VERTEX;
    public int BLACKORBVERTEX_RADIUS;
    public String BULLET;
    public String THEME_SONG;
    public String COLLECTING_SOUND;
    public String COLLISION_SOUND;
    public String ENEMY_DYING_SOUND;
    public String ENEMY_ENTERING_SOUND;
    public String GAME_OVER_SOUND;
    public String SHOT;
    public String WAVE_END;
    public String WINNING_SOUND;
    public String COLLECTIBLE;
    public String NECROPICK_ANNOUNCEMENT;
    public int NECROPICK_ANNOUNCEMENT_WIDTH;
}
