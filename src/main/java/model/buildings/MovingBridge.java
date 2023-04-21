package model.buildings;
import Enum.*;
import model.Reign;

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
