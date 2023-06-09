package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class MovingBridge extends Building{

    private boolean isBridgePassable;

    public MovingBridge(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
        this.isBridgePassable = false;
    }

    public void setBridgePassable(boolean bridgePassable) {
        isBridgePassable = bridgePassable;
    }

    public void ChangeBridgeState() {
        isBridgePassable = !isBridgePassable;
    }

}
