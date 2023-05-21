package view;

import controller.*;
import Enum.*;
import model.Map;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            } else if(input.matches(Commands.MOVE_MAP.regex)) {
                HashMap<Direction, Integer> directions = getDirections(input);
                System.out.println(mapController.moveMap(directions));
            } else if((matcher = getRealMatcher(input , Commands.SHOW_MAP_DETAILS,Commands.X,Commands.Y)) != null) {
                mapController.showDetail(matcher);
            } else if((matcher = getRealMatcher(input , Commands.SET_TEXTURE,Commands.X,Commands.Y,Commands.TYPE)) != null) {
                mapController.setTextureOfBlock(matcher);
            } else if((matcher = getRealMatcher(input, Commands.DROP_ROCK,Commands.DIRECTION,Commands.X,Commands.Y)) != null) {
                System.out.println(mapController.dropRock(matcher));
            } else if((matcher = getRealMatcher(input , Commands.CLEAR,Commands.X,Commands.Y)) != null) {
                System.out.println(mapController.clearBlock(matcher));
            } else if((matcher = getRealMatcher(input , Commands.DROP_TREE,Commands.X,Commands.Y,Commands.TREE)) != null) {
                System.out.println(mapController.dropTree(matcher));
            } else if((matcher = getRealMatcher(input , Commands.SET_AREA_TEXTURE,Commands.X1,Commands.Y1,Commands.X2,Commands.Y2,Commands.TYPE)) != null) {
                System.out.println(mapController.setTextureOfArea(matcher));
            } else if((matcher = getRealMatcher(input , Commands.DROP_BUILDING,Commands.X,Commands.Y,Commands.TYPE)) != null) {
                System.out.println(mapController.dropBuilding(matcher));
            } else if((matcher = getRealMatcher(input , Commands.DROP_UNIT,Commands.X,Commands.Y,Commands.TYPE,Commands.AMOUNT)) != null) {
                System.out.println(mapController.dropUnit(matcher));
            }else if((matcher = getRealMatcher(input,Commands.SAVE)) != null){
                Map.getTemplateMaps().add(mapController.getMap());
                MapController.saveTheMaps();
            }
            else if((matcher = getRealMatcher(input,Commands.REMOVE_BASE,Commands.X,Commands.Y)) != null){
                System.out.println(mapController.removeBase(matcher));
            } else if(input.matches(Commands.BACK.regex)) {
                System.out.println("exiting map menu...");
                return;
            }
            else System.out.println(ResponseToUser.INVALID_COMMAND);
        }
    }

    public HashMap<Direction, Integer> getDirections(String input) {
        HashMap<Direction, Integer> directions = new HashMap<>();
        for (Direction value : Direction.values()) {
            if(!value.isMajor) continue;
            int number = getDirectionNumber(input, value);
            if(number == -1) return null;
            directions.put(value, number);
        }
        return directions;
    }
    public int getDirectionNumber(String input, Direction dir) {
        Matcher matcher = Pattern.compile(dir.getNumberRegex()).matcher(input);
        if(!matcher.find()) return 0;
        if(matcher.group("number") == null) return 1;
        int number = getInt(matcher, "number");
        if(matcher.find()) return -1;
        return number;
    }
}
