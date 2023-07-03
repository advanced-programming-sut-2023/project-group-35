package Enum;

import javafx.scene.image.Image;
import model.people.LadderMen;
import view.Menu;

import java.util.ArrayList;

public enum UnitType {
    ARCHER("archer.png", BuildingType.BARRACK , Resource.BOW,null, 5, 10 , 15 ,3 , 8 ),
    LADDERMAN("ladderMan.png", BuildingType.ENGINEER_GUILD,null,null,0,2,5,3,1),
    CROSSBOWMAN("crossBowMan.png", BuildingType.BARRACK,Resource.BOW,null,10,20,12,2,12),
    SPEARMAN("spearMan.png", BuildingType.BARRACK,Resource.SPEAR,null,8,10,20,3,1),
    PIKEMAN("pikeMan.png", BuildingType.BARRACK,Resource.SPEAR,Resource.ARMOR,15,20,25,1,1),
    SWORDMAN("swordMan.png", BuildingType.BARRACK,Resource.SWORD,Resource.ARMOR,15,25,20,2,1),
    KNIGHT("knight.png", BuildingType.BARRACK,Resource.SWORD,Resource.HORSE,20,20,30,5,1),
    BLACKMONK("blackMonk.png", BuildingType.CATHEDRAL,null,null,20,15,20,2,1),
    ARABIANBOWS("arabianBow.png", BuildingType.MERCENARY_CAMP,null,null,10,10,15,3,5),
    SLAVE("slave.png", BuildingType.MERCENARY_CAMP,null,null,2,5,10,4,1),
    SLINGER("slinger.png", BuildingType.MERCENARY_CAMP,null,null,12,15,15,2,4),
    ASSASIN("assassin.png", BuildingType.MERCENARY_CAMP,null,null,30,15,15,4,1),
    HORSEARCHER("horseArcher.png", BuildingType.MERCENARY_CAMP,null,null,20,15,25,5,6),
    ARABIANSWORDMAN("arabianSwordMan.png", BuildingType.MERCENARY_CAMP,null,null,20,20,15,3,1),
    FIRETHROWER("fireThrower.png", BuildingType.MERCENARY_CAMP,null,null,20,25,10,3,5),
    ENGINEER("engineer.png", BuildingType.ENGINEER_GUILD,null,null,20,0,20,3,1),
    TUNNELER("tunneler.png", BuildingType.ENGINEER_GUILD,null,null,15,0,15,3,1),
    ;
    //DOG(null,null,null,0,10,18,5,1);

    //public final String name;

    public final Image image = null;
    public final BuildingType buildingProducedIn;
    public final Resource resourceToBuild;
    public final Resource secondResource;
    public final int cost;
    private int attackPower; // variable?

    public static UnitType getUnitByImage(Image image) {
        for (UnitType value : UnitType.values()) {
            if(image.getUrl().equals(value.getImage().getUrl())) return value;
        }
        return null;
    }

    public int getDefencePower() {
        return defencePower;
    }
    private int defencePower; // variable?
    public final int speed; // variable?
    public int id; // ????
    public final int range;

    UnitType(String url, BuildingType producedIn, Resource resourceToBuild,Resource secondResource, int cost,
             int attackPower, int defencePower, int speed, int range) {
        //this.name = name;
        //this.image = new Image(Menu.class.getResource("/Images/units/" + url).toExternalForm(), 60, 60, false, true);
        this.buildingProducedIn = producedIn;
        this.resourceToBuild = resourceToBuild;
        this.secondResource = secondResource;
        this.cost = cost;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.speed = speed;
        this.range = range;
    }

    public static ArrayList<UnitType> getUnitsProducedIn(BuildingType buildingType) {
        ArrayList<UnitType> units = new ArrayList<>();
        for (UnitType value : UnitType.values()) {
            if(value.buildingProducedIn.equals(buildingType)) units.add(value);
        }
        return units;
    }
    public static UnitType getUnitTypeByName(String name) {
        for (UnitType value : UnitType.values()) {
            if(value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
    public Image getImage() {
        return this.image;
    }
    public void setDefencePower(int defencePower) {
        this.defencePower = defencePower;
    }


    public int getAttackPower() {
        return attackPower;
    }
    public int getRange(){
        return range;
    }
}
