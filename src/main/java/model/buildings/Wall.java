package model.buildings;

import model.Block;
import model.Reign;
import Enum.*;
public class Wall extends Building {
    public Wall(BuildingType buildingType, Reign owner, Block block) {
        super(buildingType, owner, block);
    }
}
