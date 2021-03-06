package re2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Leon leon;
    private ArrayList enemies;
    private ArrayList entrances;
    private ArrayList scenery;
    private ArrayList items;
    private boolean ingame;
    private boolean paused;
    private int B_WIDTH;
    private int B_HEIGHT;
    private String currentRoom;
    private Inventory inventory;

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        inventory = new Inventory();
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
        enemies.add(new Zombie(600, 90));
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
        //items.add(new Weapon(40, 600, "Shotgun", "images/handgun.png", 1));
        items.add(new Ammo(40, 300, "Handgun Ammo", "images/handgunAmmo.png", 10));
        
        Weapon startGun = new Weapon(0, 0, "9mm Handgun", "images/handgun.png", 1);
        startGun.equip();
        
        inventory.addToInventory(startGun);
        inventory.addToWeapons(startGun);       
        inventory.addToWeaponNames("9mm Handgun");   
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
            
            //display the number of bullets left for the equipped weapon
            graphics2d.setColor(Color.WHITE);
            graphics2d.drawString("Bullets Left: " + inventory.getEquippedAmmoCount(), 5, 890);

            //draw all enemies from the array
            for(int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy) enemies.get(i);
                if(enemy.isVisible()) {
                    graphics2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
                }
            }
        } else {
            //Leon is dead, so display the game over screen
            ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("images/gameOver.png"));
            Image image = imageIcon.getImage();
            
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.drawImage(image, 400, 50, this);
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
                //move the bullet in the direction Leon is facing
                if(leon.getDirection().equals("right")) {
                    bullet.moveRight();
                }
                if(leon.getDirection().equals("left")) {
                    bullet.moveLeft();
                }
                if(leon.getDirection().equals("down")) {
                    bullet.moveDown();
                }
                if(leon.getDirection().equals("up")) {
                    bullet.moveUp();
                }
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
                inventory.addToInventory(item);
                if(item instanceof Weapon) {
                    inventory.addToWeapons((Weapon) item);
                    inventory.addToWeaponNames(item.getName());
                }
                if(item instanceof Ammo) {
                    inventory.increaseAmmo(item.getName(), 10);
                }
                items.remove(item);
            }
        }

        //collision detection between enemies and Leon
        for(int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            Rectangle enemyRect = enemy.getBounds();

            //got attacked -- subtract from Leon's health and move him back a few steps
            if(leonRect.intersects(enemyRect)) {
                int hp = leon.getHealth() - enemy.getStrength();
                leon.setHealth(hp);
                //Leon is dead -- game over -- stop drawing 
                if(hp <= 0) {
                    ingame = false;
                }
                //Leon is attacked but not dead -- push the enemy back
                else {
                    if(enemy.getDirection().equals("up")) {
                        enemy.setY(enemy.getY() + 55);
                    }
                    if(enemy.getDirection().equals("down")) {
                        enemy.setY(enemy.getY() - 55);
                    }
                    if(enemy.getDirection().equals("left")) {
                        enemy.setX(enemy.getX() + 55);
                    }
                    if(enemy.getDirection().equals("right")) {
                        enemy.setX(enemy.getX() - 55);
                    }
                }
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
                    int hp = enemy.getHitPoints();
                    int currentHP = hp - leon.getEquippedWeaponFirepower();
                    enemy.setHitPoints(currentHP);
                    //System.out.println(hp);
                    if(currentHP == 0) {
                        enemy.setVisible(false);
                    }
                    //else maybe make the enemy move back  
                    else {
                        if(enemy.getDirection().equals("up")) {
                            enemy.setY(enemy.getY() + 55);
                        }
                        if(enemy.getDirection().equals("down")) {
                            enemy.setY(enemy.getY() - 55);
                        }
                        if(enemy.getDirection().equals("left")) {
                            enemy.setX(enemy.getX() + 55);
                        }
                        if(enemy.getDirection().equals("right")) {
                            enemy.setX(enemy.getX() - 55);
                        }
                    }
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
/*    
    public static ArrayList getInventory() {
        return inventory;
    }
    
    public static ArrayList getWeapons() {
        return weapons;
    }

    public static ArrayList getWeaponNames() {
        return weaponNames;
    }
*/
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