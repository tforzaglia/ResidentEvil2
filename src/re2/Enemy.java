package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Enemy {

    private String enemy = "zombie.png";
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;
    private String direction = "null";

    public Enemy(int x, int y) {

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(enemy));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
    }

    //move method just makes sure that the enemies stay on the screen
    public void move() {

        if (x < 0) {
            x = 0;
        }

        if (x > 1450) {
            x = 1450;
        }

        if (y < 1) {
            y = 1;
        }

        if (y > 840) {
            y = 840;
        }
    }

    public void moveLeft() {
        x -= 1;
        direction = "left";
    }

    public void moveRight() {
        x += 1;
        direction = "right";
    }

    public void moveUp() {
        y -= 1;
        direction = "up";
    }

    public void moveDown() {
        y += 1;
        direction = "down";
    }

    public String getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}