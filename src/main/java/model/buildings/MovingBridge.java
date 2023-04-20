package main.java.model.buildings;
import main.java.Enum.*;
import main.java.model.Reign;

public class MovingBridge extends Building{

    boolean isBridgeDown;
    public MovingBridge(Reign owner, int hp, BuildingType buildingType) {
        super(owner, hp,  buildingType);
    }
    public void ChangeBridgeState() {

    }
    @Override
    public void nextTurn() {

    }
}
