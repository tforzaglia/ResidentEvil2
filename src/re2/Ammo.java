package re2;

public class Ammo extends Item {
    
    private int amountToAdd;
    
    public Ammo(int x, int y, String name, String itemFile, int amountToAdd) {

        super(x, y, name, itemFile);
        this.amountToAdd = amountToAdd;
    }
    
    public int getAmountToAdd() {
        return amountToAdd;
    }
}
