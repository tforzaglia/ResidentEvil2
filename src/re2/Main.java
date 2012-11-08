package re2;

import Rooms.Board;
import Rooms.Room1;
import Rooms.Room2;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFrame;

public class Main extends JFrame {
    
    public static String oldRoom;
    public static String newRoom;
    public static boolean changeRoom;
    private static HashMap<String,Board> map = new HashMap<String, Board>();
    
    public static void main(String[] args) { 
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 900);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Resident Evil 2");
        frame.setResizable(false);
        frame.setVisible(true);
        
        //need to create an object for each room -- then will set a current room variable
            //to whatever the current room is
        Room1 room1 = new Room1();
        Room2 room2 = new Room2();
        
        map.put("room1", room1);
        map.put("room2", room2);
        
        frame.getContentPane().add(room1);
        
        //DEUBUG CODE
       // Scanner scan = new Scanner(System.in);
      //  int i = scan.nextInt();
        System.out.println(oldRoom);
        if(map.get(oldRoom).changeRoom){
            frame.setVisible(false);
            frame.getContentPane().removeAll();
            frame.setVisible(true);
            frame.getContentPane().add(map.get(newRoom));
            map.get(newRoom).changeRoom=false;
        }
      //  frame.setVisible(false);
      //  frame.getContentPane().removeAll();
      //  frame.setVisible(true);
      //  frame.getContentPane().add(map.get(newRoom));      
    }
}