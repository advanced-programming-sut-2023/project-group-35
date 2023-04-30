package model.buildings;
import Enum.*;
import model.Reign;

public class MovingBridge extends Building{

    boolean isBridgeDown;
    public MovingBridge(Reign owner, BuildingType buildingType) {
        super(owner, buildingType);
    }
    public void ChangeBridgeState() {

    }
    @Override
    public void nextTurn() {

    }
}
