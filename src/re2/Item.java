package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Item {
    
    public String name;
    public String type;
    public Image image;
    public int x;
    public int y;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean visible;
    
    public Item(int x, int y, String name, String itemFile) {

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(itemFile));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
        this.name = name;
        visible = true;
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
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void move(int dx, int dy) {

        x = dx;
        y = dy;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}