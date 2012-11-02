package re2;

import javax.swing.JFrame;

public class Main extends JFrame {
    
    public Main() {
        
        add(new Board());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setTitle("Resident Evil 2");
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        
        new Main();
    }
}