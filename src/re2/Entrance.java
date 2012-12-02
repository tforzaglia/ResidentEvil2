package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Entrance {

    private String entrance = "images/door.png";
    private String leadsTo;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private Image image;

    public Entrance(int x, int y, String leadsTo) {

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(entrance));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
        this.leadsTo = leadsTo;
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

    public String getLeadsTo() {
        return leadsTo;
    }

    public void move(int dx, int dy) {

        x = dx;
        y = dy;
    }
}