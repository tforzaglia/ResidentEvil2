package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bullet {

    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    boolean visible;
    private final int BOARD_WIDTH = 1500;
    private final int MISSLE_SPEED = 2;

    public Bullet(int x, int y) {

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/bullet.png"));
        image = imageIcon.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void move() {

        x += MISSLE_SPEED;
        if (x > BOARD_WIDTH) {
            visible = false;
        }
    }
}