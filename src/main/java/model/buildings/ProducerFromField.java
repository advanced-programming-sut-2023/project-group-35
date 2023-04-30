package model.buildings;
import Enum.*;
import model.Reign;

public class ProducerFromField extends Producer{

    private FieldType fieldType;
    private Resource produced;

    public ProducerFromField(Reign owner, BuildingType buildingType, FieldType fieldType, int rate, Resource produced) {
        super(owner, buildingType, rate, produced);
        this.fieldType = fieldType;
    }

    @Override
    public void nextTurn(){

    }
}
