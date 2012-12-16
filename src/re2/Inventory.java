package re2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Inventory {
    private JFrame inventoryWindow;
            
    
    public ArrayList getInventory() {
        return Board.getInventory();
    }
    
    public JFrame getFrame() {
        return inventoryWindow;
    }
    
    //bring up the inventory screen when the game is paused
    public JFrame showInventory() {

        //get the items in the inventory in an array list
        ArrayList<Item> inventory = getInventory();
        
        //create the frame and panel -- setup the panel
        inventoryWindow = new JFrame("Inventory");
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        inventoryPanel.setBackground(Color.WHITE);
                
        //create popup menu and popup menu listener
        JPopupMenu popupMenu = new JPopupMenu("Items");
        ActionListener actionListener = new PopupActionListener();
        
        //Add items for the pop up menu which
        JMenuItem equip = new JMenuItem("Equip");
        equip.addActionListener(actionListener);
        popupMenu.add(equip);
        JMenuItem use = new JMenuItem("Use");
        use.addActionListener(actionListener);
        popupMenu.add(use);
    
        //loop through the inventory and add each item as a label to the panel
        for(int i = 0; i < inventory.size(); i++) {
            JLabel itemLabel = new JLabel(inventory.get(i).getName());
            itemLabel.setOpaque(true);
            itemLabel.setBackground(Color.CYAN);
            inventoryPanel.add(itemLabel);
            itemLabel.setComponentPopupMenu(popupMenu);
        }
        
        inventoryWindow.setContentPane(inventoryPanel);
        inventoryWindow.setSize(700, 500);
        inventoryWindow.setLocation(300, 550);
        
        return inventoryWindow;
    }

}

class PopupActionListener implements ActionListener {
        
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Selected: " + actionEvent.getActionCommand());
    }
}