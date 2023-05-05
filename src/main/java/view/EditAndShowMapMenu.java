package view;

import controller.*;
import Enum.*;

import java.util.regex.Matcher;

public class EditAndShowMapMenu extends Menu{
    public MapController mapController;

    public EditAndShowMapMenu(MapController mapController) {
        this.mapController = mapController;
    }

    public void run() {
        while(true) {
            input = scanner.nextLine();
            if(((matcher = getRealMatcher(input, Commands.SHOW_MAP, Commands.X, Commands.Y))) != null) {
                System.out.println(mapController.showMap(matcher));
//            } else if((matcher = getMatcher(input , Commands.MOVE_MAP)) != null) {
//                System.out.println(mapController.moveMap(matcher));
            } else if((matcher = getMatcher(input , Commands.SHOW_MAP_DETAILS.regex)) != null) {
                mapController.showDetail(matcher);
            } else if((matcher = getMatcher(input , Commands.SET_TEXTURE.regex)) != null) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                FieldType fieldType = null;
                mapController.setTextureOfBlock(x, y , null);
            } else if((matcher = getMatcher(input, Commands.DROP_ROCK.regex)) != null) {
                System.out.println(mapController.dropRock(matcher));
            } else if((matcher = getMatcher(input , Commands.CLEAR.regex)) != null) {
                int x = Integer.parseInt(matcher.group("x"));
                int y = Integer.parseInt(matcher.group("y"));
                System.out.println(mapController.clearBlock(x , y));
            } else if((matcher = getMatcher(input , Commands.DROP_ROCK.regex)) != null) {
                System.out.println(mapController.dropRock(matcher));
            } else if((matcher = getMatcher(input , Commands.DROP_TREE.regex)) != null) {
                System.out.println(mapController.dropTree(matcher));
            } else if((matcher = getMatcher(input , Commands.SET_AREA_TEXTURE.regex)) != null) {
                System.out.println(mapController.setTextureOfArea(matcher));
            } else if((matcher = getMatcher(input , Commands.DROP_BUILDING.regex)) != null) {
                System.out.println(mapController.dropBuilding(matcher));
            } else if((matcher = getMatcher(input , Commands.DROP_UNIT.regex)) != null) {
                System.out.println(mapController.dropUnit(matcher));
            }
        }
    }
}
