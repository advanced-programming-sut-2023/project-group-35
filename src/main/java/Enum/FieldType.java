package Enum;

public enum FieldType {
    Ground,
    Ground_With_Stone, //same as ground
    stoneGround,
    Stone, // for stone production
    Iron, // for iron production
    Rock, // unpassable
    Grass,
    Savanna, // can't build farm
    OilGround,
    Swamp, // kills
    LittleLake, // can't pass or struct
    BigLake, // can't pass or struct
    River, // can't pass or struct
    Beach, // can't pass or struct
    Ocean, // can't pass or struct
    moat; // can' struct but passable/unpassable





    boolean isPassable;
}
