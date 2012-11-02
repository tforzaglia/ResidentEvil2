package re2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    
    private Timer timer;
    private Leon leon;
    
    public Board() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        leon = new Leon();
        
        timer = new Timer(5, this);
        timer.start();
    }
    
    public void paint(Graphics graphics) {
        
        super.paint(graphics);
        Graphics2D graphics2d = (Graphics2D)graphics;
        graphics2d.drawImage(leon.getImage(), leon.getX(), leon.getY(), this);
        
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }
    
    //called every 5ms -- move the sprite and repaint the board
    public void actionPerformed(ActionEvent actionEvent) {
        
        leon.move();
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {
        
        public void keyReleased(KeyEvent keyEvent) {
            
            leon.keyReleased(keyEvent);
        }
        
        public void keyPressed(KeyEvent keyEvent) {
            
            leon.keyPressed(keyEvent);
        }
    }
}
