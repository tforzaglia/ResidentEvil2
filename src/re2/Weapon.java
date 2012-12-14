package re2;

public class Weapon extends Item {
    
    private int firepower;
    private boolean equipped = false;
    
    public Weapon(int x, int y, String name, String itemFile, int power) {

        super(x, y, name, itemFile);
        this.firepower = firepower;
    }
    
    public void equip() {
        equipped = true;
    }
    
    public void unequip() {
        equipped = false;
    }
    
    public boolean isEquipped() {
        return equipped;
    } 
}