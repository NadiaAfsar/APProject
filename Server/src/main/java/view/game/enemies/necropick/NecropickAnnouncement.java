package view.game.enemies.necropick;

import application.MyApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NecropickAnnouncement {
        private int x;
        private int y;
        protected BufferedImage image;
        private int width;
        private String ID;


        public NecropickAnnouncement(int x, int y, String ID) {
            width = MyApplication.configs.NECROPICK_ANNOUNCEMENT_WIDTH;
            this.x = x-width/2;
            this.y = y-width/2;
            this.ID = ID;
            try {
                image = ImageIO.read(new File(MyApplication.configs.NECROPICK_ANNOUNCEMENT));
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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

    public BufferedImage getImage() {
        return image;
    }

    public String getID() {
            return ID;
        }

}
