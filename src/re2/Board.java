package re2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Leon leon;
    private ArrayList enemies;
    private ArrayList entrances;
    private ArrayList scenery;
    private ArrayList items;
    private static ArrayList inventory;
    private boolean ingame;
    private boolean paused;
    private int B_WIDTH;
    private int B_HEIGHT;
    private String currentRoom;

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        ingame = true;
        paused = false;
        leon = new Leon();
        initEnemies();
        initEntrances();
        initScenery();
        initItems();

        timer = new Timer(7, this);
        timer.start();
    }

    @Override
    public void addNotify() {

        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();
    }

    private void initEnemies() {

        enemies = new ArrayList();
        enemies.add(new Enemy(600, 90));
    }

    private void initEntrances() {

        setCurrentRoom("room1");
        entrances = new ArrayList();
        entrances.add(new Entrance(400, 700, "room2"));
    }

    private void initScenery() {

        scenery = new ArrayList();
        for (int i = 1; i < 1470; i = i + 77) {
            scenery.add(new SceneObject(i, 1));
            scenery.add(new SceneObject(i, 42));
        }
        //testing collision detection
        for (int i = 200; i < 500; i = i + 77) {
            scenery.add(new SceneObject(i, 300));
            scenery.add(new SceneObject(i, 342));
        }
    }
    
    private void initItems() {

        items = new ArrayList();
        items.add(new Weapon(40, 300, "9mm Handgun", "images/handgun.png", 1));
        inventory = new ArrayList<Item>();
    }

    /**
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);

        if(ingame) {
            checkPause();

            Graphics2D graphics2d = (Graphics2D) graphics;
            ArrayList clip = leon.getBullets();

            //Draw all of the doors/entrances
            for(int i = 0; i < entrances.size(); i++) {
                Entrance entrance = (Entrance) entrances.get(i);
                graphics2d.drawImage(entrance.getImage(), entrance.getX(), entrance.getY(), this);
            }

            //draw all of the scenery objects
            for(int i = 0; i < scenery.size(); i++) {
                SceneObject sceneObject = (SceneObject) scenery.get(i);
                graphics2d.drawImage(sceneObject.getImage(), sceneObject.getX(), sceneObject.getY(), this);
            }

            //draw all of the bullets
            for(int i = 0; i < clip.size(); i++) {
                Bullet bullet = (Bullet) clip.get(i);
                graphics2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
            }

            //draw all of the items
            for(int i = 0; i < items.size(); i++) {
                Item item = (Item) items.get(i);
                if(item.isVisible()) {
                    graphics2d.drawImage(item.getImage(), item.getX(), item.getY(), this);
                }
            }
            
            //draw Leon
            graphics2d.drawImage(leon.getImage(), leon.getX(), leon.getY(), this);

            //draw all enemies from the array
            for(int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if(enemy.isVisible()) {
                    graphics2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
                }
            }
        } else {

            //if Leon's health == 0 display the game over screen below and disbale resume
            /*
            String message = "YOU ARE DEAD";
            Font font = new Font("Helvetica", Font.BOLD, 30);
            FontMetrics metrics = this.getFontMetrics(font);

            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString(message, (B_WIDTH = metrics.stringWidth(message)) / 2, B_HEIGHT / 2);*/
        }

        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }

    /**
     *
     * @param actionEvent called every 7ms -- move the sprite and repaint the
     * board
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        ArrayList clip = leon.getBullets();

        //move the bullets across the screen
        for(int i = 0; i < clip.size(); i++) {
            Bullet bullet = (Bullet) clip.get(i);
            if(bullet.isVisible()) {
                bullet.move();
            } else {
                clip.remove(i);
            }
        }

        //move the enemies based on Leon's position
        if(!paused) {
            for(int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if(enemy.isVisible()) {
                    enemy.move();
                    if(enemy.getX() > leon.getX()) {
                        enemy.moveLeft();
                    }
                    if(enemy.getX() < leon.getX()) {
                        enemy.moveRight();
                    }
                    if(enemy.getY() < leon.getY()) {
                        enemy.moveDown();
                    }
                    if(enemy.getY() > leon.getY()) {
                        enemy.moveUp();
                    }
                } else {
                    enemies.remove(i);
                }
            }
            leon.move();
        }
        checkCollisions();
        //System.out.println("DEBUG :" + getCurrentRoom());
        checkChangeRoom();
        repaint();
    }

    //method to act on collisions between various objects
    public void checkCollisions() {

        Rectangle leonRect = leon.getBounds();
        
        //collision detection between Leon and the entrances
        for(int i = 0; i < entrances.size(); i++) {
            Entrance entrance = (Entrance) entrances.get(i);
            Rectangle entranceRect = entrance.getBounds();
            if(leonRect.intersects(entranceRect)) {
                setCurrentRoom(entrance.getLeadsTo());
                for(int j = 0; j < leon.getBullets().size(); j++) {
                    Bullet bullet = (Bullet) leon.getBullets().get(j);
                    bullet.setVisible(false);
                }
            }
        }

        //collision detection scenery and Leon
        for(int i = 0; i < scenery.size(); i++) {
            SceneObject sceneObject = (SceneObject) scenery.get(i);
            Rectangle sceneryRect = sceneObject.getBounds();
            if(leonRect.intersects(sceneryRect)) {
                if(leon.getDirection().equals("up")) {
                    leon.setY(leon.getY() + 1);
                }
                if(leon.getDirection().equals("down")) {
                    leon.setY(leon.getY() - 1);
                }
                if(leon.getDirection().equals("left")) {
                    leon.setX(leon.getX() + 1);
                }
                if(leon.getDirection().equals("right")) {
                    leon.setX(leon.getX() - 1);
                }
            }
            
            //collision detection scenery and enemies
            for(int j = 0; j < enemies.size(); j++) {
                Enemy enemy = (Enemy) enemies.get(j);
                Rectangle enemyRect = enemy.getBounds();
                if(enemyRect.intersects(sceneryRect)) {
                    if(enemy.getDirection().equals("up")) {
                        enemy.setY(enemy.getY() + 1);
                    }
                    if(enemy.getDirection().equals("down")) {
                        enemy.setY(enemy.getY() - 1);
                    }
                    if(enemy.getDirection().equals("left")) {
                        enemy.setX(enemy.getX() + 1);
                    }
                    if(enemy.getDirection().equals("right")) {
                        enemy.setX(enemy.getX() - 1);
                    }
                }
            }
        }
        
        //collision detection between Leon and items
        for(int i = 0; i < items.size(); i++) {
            Item item = (Item) items.get(i);
            Rectangle itemRect = item.getBounds();
            if(leonRect.intersects(itemRect)) {
                item.setVisible(false);
                inventory.add(item);
                items.remove(item);
            }
        }

        //collision detection between enemies and Leon
        for(int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            Rectangle enemyRect = enemy.getBounds();

            //got attacked -- subtract from Leon's health and move him back a few steps
            if (leonRect.intersects(enemyRect)) {
                //health--
                //if health != 0
                //move leon backwards a bit
                //else (health == 0)
                //ingame = false (game over)
                //System.out.println("DEBUG : ATTCKED!");
            }
        } 

        ArrayList clip = leon.getBullets();

        //collision detection between enemies and bullets
        for(int i = 0; i < clip.size(); i++) {
            Bullet bullet = (Bullet) clip.get(i);
            Rectangle bulletRect = bullet.getBounds();

            for(int j = 0; j < enemies.size(); j++) {
                Enemy enemy = (Enemy) enemies.get(j);
                Rectangle enemyRect = enemy.getBounds();

                //bullet hit an enemy
                if(bulletRect.intersects(enemyRect)) {
                    bullet.setVisible(false);
                    //if enemy health == 0
                    enemy.setVisible(false);
                    //else maybe make the enemy flash or move back                        
                }
            }
        }
    }

    //remove the old objects and add new ones based on the current room
    public void checkChangeRoom() {

        if(getCurrentRoom().equals("room2")) {
            removeElements();
            //add the elements for the new, current room
            setBackground(Color.BLACK);
            entrances.add(new Entrance(500, 10, "room3"));
        }
    }

    //remove all the old objects--called when Leon enters a new room
    public void removeElements() {

        //remove all the objects for the previous room
        for(int i = 0; i < entrances.size(); i++) {
            entrances.remove(i);
        }
        for(int i = 0; i < enemies.size(); i++) {
            enemies.remove(i);
        }
        for(int i = 0; i < scenery.size(); i++) {
            scenery.remove(i);
        }
        for(int i = 0; i < items.size(); i++) {
            items.remove(i);
        }
    }

    //return the room number the user is currently in
    public String getCurrentRoom() {
        return currentRoom;
    }

    //set the current room -- used when Leon walks over amn entrance
    public void setCurrentRoom(String room) {
        currentRoom = room;
    }
    
    //set the paused boolean based on key presses
    public void checkPause() {
        
        if(leon.isPaused()) {
            paused = true;
        }
        else {
            paused = false;
        }
    }
    
    public static ArrayList getInventory() {
        return inventory;
    }

    public class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            leon.keyReleased(keyEvent);
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            leon.keyPressed(keyEvent);
        }
    }
}