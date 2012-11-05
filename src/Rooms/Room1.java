package Rooms;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Timer;
import re2.Entrance;
import re2.Leon;
import re2.Main;

public class Room1 extends Board {
    
    public Room1() {
        
        currentRoom = "room1";
        addKeyListener(new TAdapter()); 
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;
        
        leon = new Leon();
        changeRoom=false;
        Main.oldRoom=currentRoom;
        initEnemies();
        initEntrances();
        timer = new Timer(5, this);
        timer.start();
    }
    /**
     *
     */
    @Override
    public void initEntrances() {
         
        entrances = new ArrayList();

      //  for (int i=0; i<pos.length; i++ ) {
            entrances.add(new Entrance(900,10, "room2"));
      //  }
    }
}