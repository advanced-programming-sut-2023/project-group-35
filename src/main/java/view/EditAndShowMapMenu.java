package view;

import controller.*;

import java.util.*;
import java.util.regex.Matcher;

public class EditAndShowMapMenu extends Menu{
    private boolean isInTheGame;
    public MapController mapController;

    public EditAndShowMapMenu(MapController mapController, boolean isInTheGame) {
        this.isInTheGame = isInTheGame;
        this.mapController = mapController;
    }

    public void run() {


    }
}
