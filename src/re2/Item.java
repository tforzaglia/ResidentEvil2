package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Item {
    
    private String name;
    private Image image;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private boolean visible;
    
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