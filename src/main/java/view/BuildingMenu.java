package view;

import controller.BuildingController;
import Enum.*;

public class BuildingMenu extends Menu{
    private BuildingController buildingController;

    public BuildingMenu(BuildingController buildingController) {
        this.buildingController = buildingController;
    }

    public void run() {
        buildingController.showHitPoint();
        while (true) {
            input = scanner.nextLine();
            if(input.matches("back")) {
                buildingController.deleteSelectedBuilding();
                return;
            } else if(input.matches("repair")) {
                System.out.println(buildingController.repair());
            } else if((matcher = getRealMatcher(input , Commands.CREATE_UNIT,Commands.TYPE,Commands.COUNT)) != null) {
                System.out.println(buildingController.createUnit(matcher));
            } else if((matcher = getMatcher(input , Commands.CHANGE_TAX_RATE.regex)) != null) {
                int rate = Integer.parseInt(matcher.group("rate"));
                System.out.println(buildingController.changeTaxRate(rate));
            }
        }


    }
}
