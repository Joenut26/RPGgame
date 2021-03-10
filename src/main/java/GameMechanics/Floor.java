package GameMechanics;

public class Floor {

    private int level;
    public Floor(int floorNumber){
        level = floorNumber;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
