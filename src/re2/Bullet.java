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
    private final int BOARD_HEIGHT = 900;
    private final int BULLET_SPEED = 55;

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

    public void moveRight() {
        x += BULLET_SPEED;
        if(x > BOARD_WIDTH) {
            visible = false;
        }
    }
    
    public void moveLeft() {

        x -= BULLET_SPEED;
        if(x < 0) {
            visible = false;
        }
    }
    
    public void moveDown() {

        y += BULLET_SPEED;
        if(y > BOARD_HEIGHT) {
            visible = false;
        }
    }
    
    public void moveUp() {

        y -= BULLET_SPEED;
        if(y < 0) {
            visible = false;
        }
    }
}