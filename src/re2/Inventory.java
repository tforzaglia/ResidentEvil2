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
   // private JPopupMenu popupMenu;
    
    public ArrayList getInventory() {
        return Board.getInventory();
    }
    
    public ArrayList getWeapons() {
        return Board.getWeapons();
    }
    
    public ArrayList getWeaponNames() {
        return Board.getWeaponNames();
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
        
        //Add items for the pop up menu which can be selected by right clicking on the item name
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
    
    public Weapon getEquippedWeapon() {
        
        ArrayList<Weapon> weapons = getWeapons();
        Weapon equippedWeapon = null;
        for(int i = 0; i < weapons.size(); i++) {
            if(weapons.get(i).isEquipped()) {
                equippedWeapon = weapons.get(i);
            }
        }
        return equippedWeapon;
    }


    class PopupActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals("Equip")) {
                
                //get the currently equipped weapon and unequip it 
                Weapon oldWeapon = getEquippedWeapon();
                if(oldWeapon != null) {
                    oldWeapon.unequip();
                }
                  
                //get the source name i.e. the weapon name the user right clicked on
                JMenuItem menuItem = (JMenuItem) actionEvent.getSource();
                JPopupMenu popupMenu = (JPopupMenu) menuItem.getParent();
                JLabel invoker = (JLabel)popupMenu.getInvoker();
                String weaponName = invoker.getText();
                
                //find the position of the desired item in the list of weapons
                int position = getWeaponNames().indexOf(weaponName);
                
                //get the weapon object from the list and equip it
                Weapon newEquipped = (Weapon) getWeapons().get(position);
                newEquipped.equip();
                
                System.out.println(newEquipped.getName());
            }
        }
    }
}