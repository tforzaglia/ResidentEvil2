package re2;

import javax.swing.ImageIcon;

public class Zombie extends Enemy {
    
    private String enemy = "images/zombie.png";
    
    public Zombie(int x, int y) {
        super(x, y);
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(enemy));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        hitPoints = 3;
    }
    
    
}