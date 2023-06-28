package Enum;

import javafx.scene.image.Image;

public enum FieldType {
    Ground("desert_tile.jpg" , true, true,false),
    //Ground_With_Stone(null, false, true), //same as ground
    Stone("collection27.png", true, true,false), // for stone production
    Iron(null, true, true,false), // for iron production
    Rock(null, false, false,false), // unpassable
    Grass(null, true, true,false),
    Savanna(null, true, true,false), // can't build farm
    OilGround(null, false , true,false),
    Swamp(null , false, false,true), // kills ???????
    LittleLake(null, false, false,true), // can't pass or struct
    BigLake(null, false, false,true), // can't pass or struct
    River(null, false, false,true), // can't pass or struct
    Beach(null , false, false,true), // can't pass or struct
    Ocean(null, false, false,true), // can't pass or struct
    plain(null , false, true,false),
    moat(null , false, false,false); // can' struct but unpassable

    private String fieldURL;

    public boolean isSuitableForBuilding;
    public final boolean canTroopPass;
    public final boolean isAquatic;

//    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
//    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
//    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
//    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
//    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
//    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
//    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
//    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static FieldType getFieldType(String name) {
        for (FieldType value : FieldType.values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }
    public String getName() {
        return this.name().toLowerCase().replaceAll("_", " ");
    }
    public Image getFieldImage() {
        Image image = new Image(FieldType.class.getResource("/Images/field/" + this.fieldURL).toExternalForm());
        return image;
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


    FieldType(String fieldURL, boolean isSuitableForBuilding, boolean canTroopPass, boolean isAquatic) {
        this.fieldURL = fieldURL;
        this.isSuitableForBuilding = isSuitableForBuilding;
        this.canTroopPass = canTroopPass;
        this.isAquatic = isAquatic;
    }
}
