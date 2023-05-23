package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class Producer extends Building{
    public int rate;
    Resource resource;

    public Producer(BuildingType buildingType, Reign owner, Block block, Resource resource) {
        super(buildingType, owner, block);
        this.rate = buildingType.getRelatedInteger();
        this.resource = resource;
    }

    public void addResources() {

    }

    @Override
    public void nextTurn() {
        getOwner().addToResource(resource, rate);
    }
}
