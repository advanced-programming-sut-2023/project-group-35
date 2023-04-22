package model.buildings;
import Enum.*;
import model.Reign;

public class Producer extends Building{
    public int rate;

    //ArrayList<Resources> resources = new ArrayList<>();
    Resource resource;

    public Producer(Reign owner, int hp, BuildingType buildingType , int rate, Resource resource) {
        super(owner, hp, buildingType);
        this.rate = rate;
        this.resource = resource;
    }

    public void addResources() {

    }

    @Override
    public void nextTurn() {

    }
}
