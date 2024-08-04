package view.game;

public class FrameView {
    private int x;
    private int y;
    private int width;
    private int height;
    private String ID;

    public FrameView(int x, int y, int width, int height, String ID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.ID = ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getID() {
        return ID;
    }
    public void update(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
