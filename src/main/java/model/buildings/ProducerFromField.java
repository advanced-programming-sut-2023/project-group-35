package model.buildings;
import Enum.*;
import model.Block;
import model.Reign;

public class ProducerFromField extends Producer{

    //private FieldType fieldType;
    //private Resource produced;

//    public ProducerFromField(Reign owner, BuildingType buildingType, FieldType fieldType, int rate, Resource produced) {
//        super(buildingType, rate, produced);
//        this.fieldType = fieldType;
//    }


    public ProducerFromField(BuildingType buildingType, Reign owner, Block block, int rate, Resource resource) {
        super(buildingType, owner, block, rate, resource);

    }

    @Override
    public void nextTurn(){

    }
}
