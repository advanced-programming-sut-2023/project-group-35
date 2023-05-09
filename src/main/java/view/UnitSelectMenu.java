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
                unitController.moveUnitCommand(getInt(matcher, "x"), getInt(matcher, "y"));
            } else if((matcher = getRealMatcher(input, Commands.PATROL_UNIT, Commands.X1, Commands.X2, Commands.Y1, Commands.Y2)) != null) {
                unitController.patrolUnit(getInt(matcher, "x1"), getInt(matcher, "x2"),
                        getInt(matcher, "y1"), getInt(matcher, "y2"));
            } else if((matcher = getMatcher(input, Commands.SET_UNIT_STATE.regex)) != null) {
                System.out.println(unitController.setUnitState(matcher.group("state")));
            } else if((matcher = getRealMatcher(input, Commands.ATTACK_ENEMY, Commands.X, Commands.Y)) != null) {
                System.out.println(unitController.attackEnemy(getInt(matcher, "x"), getInt(matcher, "y")));
            }
        }
    }
}
