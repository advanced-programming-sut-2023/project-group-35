package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class Producer extends Building{
    public int rate;

    //ArrayList<Resources> resources = new ArrayList<>();
    Resource resource;

//    public Producer(Reign owner, BuildingType buildingType , int rate, Resource resource) {
//        super(owner, buildingType);
//        this.rate = rate;
//        this.resource = resource;
//    }


    public Producer(BuildingType buildingType, Reign owner, Block block, int rate, Resource resource) {
        super(buildingType, owner, block);
        this.rate = rate;
        this.resource = resource;
    }

    public void addResources() {

    }

    @Override
    public void nextTurn() {

    }
}
