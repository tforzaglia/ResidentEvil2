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
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    
    public Board() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;
        
        leon = new Leon();
        initEnemies();
        timer = new Timer(5, this);
        timer.start();
    }
    
    /**
     *
     */
    @Override
    public void addNotify() {
        
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();
    }

//  place the enemies on the screen -- get the position from an acessor method from the enemy class
    public void initEnemies() {
         
        enemies = new ArrayList();

      //  for (int i=0; i<pos.length; i++ ) {
            enemies.add(new Enemy(600,60));
      //  }
    }
 
    /**
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        
        super.paint(graphics);

        if (ingame) { 
            
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.drawImage(leon.getImage(), leon.getX(), leon.getY(), this);
            ArrayList clip = leon.getBullets();

            //draw all of the bullets from the array list
            for (int i = 0; i < clip.size(); i++) {
                Bullet bullet = (Bullet) clip.get(i);
                graphics2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
            }
            
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
        repaint();
    }
    
    public void checkCollisions() {
        
        Rectangle r3 = leon.getBounds();
        
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
                System.out.println("DEBUG : ATTCKED!");
            }
            
            ArrayList clip = leon.getBullets();
            
            for(int i = 0; i < clip.size(); i++) {
                Bullet bullet = (Bullet)clip.get(i);
                
                Rectangle r1 = bullet.getBounds();
                
                for(int k = 0; k < enemies.size(); k++) {
                    Enemy enemy1 = (Enemy)enemies.get(k);
                    Rectangle r4 = enemy.getBounds();
                    
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
    
    private class TAdapter extends KeyAdapter {
        
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
