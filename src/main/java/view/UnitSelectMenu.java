package view;

import controller.UnitController;
import Enum.*;

public class UnitSelectMenu extends Menu{
    private UnitController unitController;

    public UnitSelectMenu(UnitController unitController) {
        this.unitController = unitController;
    }

    public void run() {
        System.out.println("you are in the unit menu");
        while (true) {
            input = scanner.nextLine();
            if((matcher = getRealMatcher(input, Commands.MOVE_UNIT, Commands.X, Commands.Y)) != null) {
                unitController.moveUnit(getInt(matcher, "x"), getInt(matcher, "y"));
            }
        }
    }
}
