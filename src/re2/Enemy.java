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
    
    public Enemy(int x, int y) {
        
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(enemy));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        visible = true;
        this.x =x;
        this.y = y;
    }
    
    //for now move the enemy back to the right if they go to far off the screen on the left
    public void move() {
        
        if(x < 0) {
            x = 1500;
        }
        x -= 1;
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

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}