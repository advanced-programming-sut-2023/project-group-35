package Enum;

import javafx.scene.image.Image;

public enum FieldType {
    Ground("desert_tile.jpg" , true, true,false),
    //Ground_With_Stone(null, false, true), //same as ground
    Stone("stone.jpg", true, true,false), // for stone production
    Iron("iron.jpg", true, true,false), // for iron production
    Rock("rock.png", false, false,false), // unpassable
    Grass("plain.jpg", true, true,false),
    Savanna("plain.jpg", true, true,false), // can't build farm
    OilGround("oilGround.jpg", false , true,false),
    Swamp("swamp.png", false, false,true), // kills ???????
    littleLake("littleLake.jpg", false, false,true), // can't pass or struct
    BigLake("sea.jpg", false, false,true), // can't pass or struct
    River("river.PNG", false, false,true), // can't pass or struct
    Beach("beach.jpg" , false, false,true), // can't pass or struct
    sea("sea.jpg", false, false,true), // can't pass or struct
    plain("plain.jpg" , false, true,false),
    moat("moat.jpg" , false, false,false); // can' struct but unpassable

    public final Image fieldImage;
    public boolean isSuitableForBuilding;
    public final boolean canTroopPass;
    public final boolean isAquatic;
    FieldType(String fieldURL, boolean isSuitableForBuilding, boolean canTroopPass, boolean isAquatic) {
        this.fieldImage = new Image(FieldType.class.getResource("/Images/field/" + fieldURL).toExternalForm());
        this.isSuitableForBuilding = isSuitableForBuilding;
        this.canTroopPass = canTroopPass;
        this.isAquatic = isAquatic;
    }

    public static FieldType getFieldType(String name) {
        for (FieldType value : FieldType.values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }
    public static FieldType getFieldTypeByImage(Image image) {
        String url = image.getUrl();
        for (FieldType value : values()) {
            if(url.contains(value.name().toLowerCase())) return value;
            if(url.contains(value.name())) return value;
        }
        return null;
    }
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", " ");
    }
    public Image getFieldImage() {
        return this.fieldImage;
    }


//    public static String getFieldTypeColor(Block block){
//        if(block.getFieldType().isAquatic)
//           return ANSI_BLUE_BACKGROUND;
//        else if(block.getFieldType().equals(Iron))
//            return ANSI_BLACK_BACKGROUND;
//        else if(block.getFieldType().equals(Stone))
//            return ANSI_CYAN_BACKGROUND;
//        else if(block.getFieldType().equals(OilGround))
//            return ANSI_RED_BACKGROUND;
//        else if(block.getFieldType().equals(Grass))
//            return ANSI_GREEN_BACKGROUND;
//        else if(block.getFieldType().equals(Ground))
//            return ANSI_YELLOW_BACKGROUND;
//        else return ANSI_WHITE_BACKGROUND;
//    }
    // ghabel sakht o saz



}
