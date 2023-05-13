package view;

import controller.*;
import Enum.*;
import model.Map;

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
            } else if((matcher = getRealMatcher(input , Commands.SHOW_MAP_DETAILS,Commands.X,Commands.Y)) != null) {
                mapController.showDetail(matcher);
            } else if((matcher = getRealMatcher(input , Commands.SET_TEXTURE,Commands.X,Commands.Y,Commands.TYPE)) != null) {
                mapController.setTextureOfBlock(matcher);
                //TODO get field type;
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
            }
            else
                System.out.println("invalid commend!");
        }
    }
}
