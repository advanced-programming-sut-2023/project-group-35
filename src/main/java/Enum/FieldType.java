package Enum;

public enum FieldType {
    Ground(null , true),
    Ground_With_Stone(null, false), //same as ground
    stoneGround(null, false),
    Stone(null, true), // for stone production
    Iron(null, true), // for iron production
    Rock(null, false), // unpassable
    Grass(null, true),
    Savanna(null, true), // can't build farm
    OilGround(null, false),
    Swamp(null , false), // kills
    LittleLake(null, false), // can't pass or struct
    BigLake(null, false), // can't pass or struct
    River(null, false), // can't pass or struct
    Beach(null , false), // can't pass or struct
    Ocean(null, false), // can't pass or struct
    plain(null , false),
    moat(null , false); // can' struct but passable/unpassable

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

    public final boolean isSuitableForBuilding;
    // ghabel sakht o saz
    FieldType(String backGroundColor, boolean isSuitableForBuilding) {
        this.backGroundColor = backGroundColor;
        this.isSuitableForBuilding = isSuitableForBuilding;
    }



}
