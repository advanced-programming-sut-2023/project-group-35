package Enum;

public enum FieldType {
    Ground(null , true, true),
    //Ground_With_Stone(null, false, true), //same as ground
    Stone(null, true, true), // for stone production
    Iron(null, true, true), // for iron production
    Rock(null, false, false), // unpassable
    Grass(null, true, true),
    Savanna(null, true, true), // can't build farm
    OilGround(null, false , true),
    Swamp(null , false, false), // kills ???????
    LittleLake(null, false, false), // can't pass or struct
    BigLake(null, false, false), // can't pass or struct
    River(null, false, false), // can't pass or struct
    Beach(null , false, false), // can't pass or struct
    Ocean(null, false, false), // can't pass or struct
    plain(null , false, true),
    moat(null , false, false); // can' struct but unpassable

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


    private String backGroundColor;

    public final boolean isSuitableForBuildingAndStructure;
    public final boolean canTroopPass;
    // ghabel sakht o saz


    FieldType(String backGroundColor, boolean isSuitableForBuildingAndStructure, boolean canTroopPass) {
        this.backGroundColor = backGroundColor;
        this.isSuitableForBuildingAndStructure = isSuitableForBuildingAndStructure;
        this.canTroopPass = canTroopPass;
    }
}
