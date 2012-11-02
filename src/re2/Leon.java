package re2;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Leon {
    
    private String leon = "leon.png";
    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;
    
    //constructor: set up the image and set the location
    public Leon() {
        
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(leon));
        image = imageIcon.getImage();
        x = 40;
        y = 60;
    }
    
    //changes the coordinates of the sprite
    public void move() {
        
        x += dx;
        y += dy;
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
    
    //change the coordinates of Leon when the arrow keys are pressed
    public void keyPressed(KeyEvent keyEvent) {
        
        int key = keyEvent.getKeyCode();
        
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
