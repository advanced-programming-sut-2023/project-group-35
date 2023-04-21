package model;

import model.buildings.*;

import javax.swing.plaf.synth.*;
import java.util.*;

public class Game {
    private Map map;
    private User starter;
    private Reign currentReign;
    private Building selectedBuilding;
    private Block selectedBlock;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Region> regions = new ArrayList<>();
    int turnsPassed;
}
