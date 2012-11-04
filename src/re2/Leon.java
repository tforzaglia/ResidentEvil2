package re2;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Leon {
    
    private String leon = "leon.png";
    
    //change in coordinates
    private int dx;
    private int dy;
    
    //current poisition
    private int x;
    private int y;
    
    
    private int height;
    private int width;
    
    
    private Image image;
    
    private ArrayList bullets;
    private final int LEON_SIZE = 61;
    
    //constructor: set up the image and set the location
    public Leon() {
        
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(leon));
        image = imageIcon.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        bullets = new ArrayList();
        x = 40;
        y = 60;
    }
    
    //changes the coordinates of the sprite
    public void move() {
        
        x += dx;
        y += dy;
        
        if(x < 1) {
            x = 1;
        }
        
        if(y < 1) {
            y = 1;
        }
    }
    
    public int getX() {       
        return x;
    }
    
    public int getY() {      
        return y;
    }
    
    public Image getImage() {      
        return image;
    }
    
    public ArrayList getBullets() {
        return bullets;
    }
    
    //returns the bounds of the Leon image
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    //change the coordinates of Leon when the arrow keys are pressed
    public void keyPressed(KeyEvent keyEvent) {
        
        int key = keyEvent.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
            shoot();
        }
        
        if(key == KeyEvent.VK_LEFT) {
            dx = -1;
        }
        
        if(key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }
        
        if(key == KeyEvent.VK_UP) {
            dy = -1;
        }
        
        if(key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }
    
    public void shoot() {
        bullets.add(new Bullet(x + LEON_SIZE/2, y + LEON_SIZE/2));
    }
    
    //no change in the coordinates when noo keys are being pressed
    public void keyReleased(KeyEvent keyEvent) {
        
        int key = keyEvent.getKeyCode();
        
        if(key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        
        if(key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        
        if(key == KeyEvent.VK_UP) {
            dy = 0;
        }
        
        if(key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
