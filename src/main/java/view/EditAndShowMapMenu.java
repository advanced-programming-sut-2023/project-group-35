package view;

import controller.*;
import Enum.*;

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
                FieldType fieldType = null; //todo complete
                mapController.setTextureOfBlock(x, y , null);
            }
        }
    }
}
