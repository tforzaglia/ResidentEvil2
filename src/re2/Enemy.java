package re2;

import java.awt.Image;
import java.awt.Rectangle;

public class Enemy {

    private int x;
    private int y;
    public int width;
    public int height;
    private boolean visible;
    public Image image;
    private String direction = "null";
    public int hitPoints;
    public int strength;

    public Enemy(int x, int y) {

        visible = true;
        this.x = x;
        this.y = y;
    }

    //move method just makes sure that the enemies stay on the screen
    public void move() {

        if(x < 0) {
            x = 0;
        }

        if(x > 1450) {
            x = 1450;
        }

        if(y < 1) {
            y = 1;
        }

        if(y > 840) {
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
    
    public int getHitPoints() {
        return hitPoints;
    }
    
    public void setHitPoints(int newHitPoints) {
        hitPoints = newHitPoints;
    }
    
    public int getStrength() {
        return strength;
    }
}