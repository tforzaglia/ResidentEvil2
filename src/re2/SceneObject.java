package re2;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class SceneObject {

    private String sceneObject = "images/fire.png";
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int width;
    private int height;
    private Image image;

    public SceneObject(int x, int y) {

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(sceneObject));
        image = imageIcon.getImage();
        width = image.getHeight(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
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
}