package re2;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {

        Board board = new Board();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(board);
        frame.setSize(1500, 900);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Resident Evil 2");
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        new Main();
    }
}