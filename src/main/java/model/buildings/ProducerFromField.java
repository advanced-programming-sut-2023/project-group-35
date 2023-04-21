package model.buildings;
import Enum.*;
import model.Reign;

public class ProducerFromField extends Producer{

    private FieldType fieldType;
    private Resources produced;

    public ProducerFromField(Reign owner, int hp, BuildingType buildingType, FieldType fieldType, int rate, Resources produced) {
        super(owner, hp, buildingType, rate, produced);
        this.fieldType = fieldType;
    }

    @Override
    public void nextTurn(){

    }
}
