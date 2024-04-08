package controller;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final Dimension FRAME_SIZE = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final int UPS = 100;
    public static final double FRAME_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/FPS;
    public static final double MODEL_UPDATE_TIME=(double) TimeUnit.SECONDS.toMillis(1)/UPS;
    public static final int EPSILON_RADIUS = 12;
}
