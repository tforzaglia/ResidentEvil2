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
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    private String currentRoom;
    
    public Board() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.GRAY);
        setDoubleBuffered(true);
        
        ingame = true;
        leon = new Leon();
        initEnemies();
        initEntrances();
        
        timer = new Timer(5, this);
        timer.start();
    }
    
    @Override
    public void addNotify() {
        
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();
    }

    public void initEnemies() {
         
        enemies = new ArrayList();
        enemies.add(new Enemy(600,60));
    }
    
    public void initEntrances() {
        
        setCurrentRoom("room1"); 
        entrances = new ArrayList();
        entrances.add(new Entrance(300,10, "room2"));
    }
 
    /**
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        
        super.paint(graphics);

        if(ingame) { 
            
            Graphics2D graphics2d = (Graphics2D) graphics;
            ArrayList clip = leon.getBullets();

            for (int i = 0; i < entrances.size(); i++) {
                Entrance entrance = (Entrance)entrances.get(i);
                graphics2d.drawImage(entrance.getImage(), entrance.getX(), entrance.getY(), this);  
            }
            
            //draw all of the bullets from the array list
            for (int i = 0; i < clip.size(); i++) {
                Bullet bullet = (Bullet) clip.get(i);
                graphics2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
            }
            
            graphics2d.drawImage(leon.getImage(), leon.getX(), leon.getY(), this);
            
            //draw all enemies from the array (have position set in each enemy object)
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = (Enemy)enemies.get(i);
                if(enemy.isVisible()) {
                    graphics2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
                }
            }      
        }
        else {
            String message = "YOU ARE DEAD";
            Font font = new Font("Helvetica", Font.BOLD, 30);
            FontMetrics metrics = this.getFontMetrics(font);
            
            graphics.setColor(Color.red);
            graphics.setFont(font);
            graphics.drawString(message, (B_WIDTH = metrics.stringWidth(message)) / 2, B_HEIGHT / 2);
        }
        
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }
    
    /**
     *
     * @param actionEvent
     * called every 5ms -- move the sprite and repaint the board
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        
        ArrayList clip = leon.getBullets();
        
        for( int i = 0; i < clip.size(); i++) {
            Bullet bullet = (Bullet) clip.get(i);
            if(bullet.isVisible()) {
                bullet.move();
            }
            else {
                clip.remove(i);
            }
        }
        
        for( int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);
            if(enemy.isVisible()) {
                enemy.move();
            }
            else {
                enemies.remove(i);
            }
        }
        
        leon.move();
        checkCollisions();
        System.out.println("DEBUG :" + getCurrentRoom());
        checkChangeRoom();
        repaint();
    }
    
    public void checkCollisions() {
        
        Rectangle r3 = leon.getBounds();
        for(int l = 0; l < entrances.size(); l++) {
                Entrance entrance = (Entrance) entrances.get(l);
                Rectangle r5 = entrance.getBounds();
                if(r3.intersects(r5)) {                 
                    setCurrentRoom(entrance.getLeadsTo());//parameter needs to be variable -- entrance.getLeadsTo()
                }   
            }

        for(int j = 0; j < enemies.size(); j++) {
            Enemy enemy = (Enemy) enemies.get(j);
            Rectangle r2 = enemy.getBounds();
            
            //got attacked -- subtract from Leon's health and move him back a few steps
            if(r3.intersects(r2)) {
                //health--
                //if health != 0
                    //move leon backwards a bit
                //else (health == 0)
                    //ingame = false (game over)
                //System.out.println("DEBUG : ATTCKED!");
            }
            
            ArrayList clip = leon.getBullets();
            
            for(int i = 0; i < clip.size(); i++) {
                Bullet bullet = (Bullet)clip.get(i);
                
                Rectangle r1 = bullet.getBounds();
                
                for(int k = 0; k < enemies.size(); k++) {
                    Enemy enemy1 = (Enemy)enemies.get(k);
                    Rectangle r4 = enemy1.getBounds();
                    
                    //bullet hit and enemy
                    if(r1.intersects(r4)) {
                        bullet.setVisible(false);
                        //if enemy health == 0
                        enemy.setVisible(false);
                        //else maybe make the enemy flash or move back                        
                    }
                }
            }   
        }  
    }
    
    public void checkChangeRoom() {
         
        if(getCurrentRoom().equals("room2")) {
            //remove all the objects for the previous room
            for(int i = 0; i < entrances.size(); i++) {
                entrances.remove(i);
            }
            for(int i = 0; i < enemies.size(); i++) {
                enemies.remove(i);
            }
            for(int i = 0; i < leon.getBullets().size(); i++) {
                leon.getBullets().remove(i);
            }
            //add the elements for the new, current room
            setBackground(Color.BLACK);
            entrances.add(new Entrance(500,10, "room3"));
            
        }
    }
    
    public String getCurrentRoom() {
        return currentRoom;
    }
    
    public void setCurrentRoom(String room) {
        currentRoom=room;
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
