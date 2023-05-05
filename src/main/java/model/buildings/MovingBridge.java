package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class MovingBridge extends Building{

    boolean isBridgeDown;

    public MovingBridge(BuildingType buildingType, Reign owner, Block block, boolean isBridgeDown) {
        super(buildingType, owner, block);
        this.isBridgeDown = isBridgeDown;
    }

    public void ChangeBridgeState() {

    }
    @Override
    public void nextTurn() {

    }
}
