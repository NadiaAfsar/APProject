package view.game.enemies.black_orb;

import java.awt.*;

public class BlackOrbLaserView {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private String ID;

    public BlackOrbLaserView(int x1, int y1, int x2, int y2, String ID) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.ID = ID;
    }

    public static void draw(int x1, int y1, int x2, int y2, Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setStroke(new BasicStroke(20));
        g2D.setColor(Color.CYAN);
        g2D.drawLine(x1, y1, x2, y2);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
    public void update(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public String getID() {
        return ID;
    }
}
