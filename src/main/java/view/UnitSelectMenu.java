package view;

import controller.UnitController;
import Enum.*;
import controller.UserController;

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
                System.out.println(unitController.moveUnitCommand(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.PATROL_UNIT, Commands.X1, Commands.X2, Commands.Y1, Commands.Y2)) != null) {
                unitController.patrolUnit(getInt(matcher, "x1"), getInt(matcher, "x2"),
                        getInt(matcher, "y1"), getInt(matcher, "y2"));
            } else if((matcher = getMatcher(input, Commands.SET_UNIT_STATE.regex)) != null) {
                System.out.println(unitController.setUnitState(matcher.group("state")));
            } else if(input.matches(Commands.GET_UNIT_STATE.regex)) {
                System.out.println(unitController.getUnitState());
            } else if((matcher = getRealMatcher(input, Commands.ATTACK_ENEMY, Commands.X, Commands.Y)) != null) {
                result =unitController.attackEnemy(getInt(matcher, "x"), getInt(matcher, "y"));
                if(result.equals("endGame")) System.out.println(unitController.endGame());
                return;
            } else if((matcher = getRealMatcher(input, Commands.DIG_MOAT, Commands.X, Commands.Y)) != null) {
                System.out.println(unitController.digMoat(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.FILL_MOAT, Commands.Y, Commands.X)) != null) {
                System.out.println(unitController.fillMoat(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.PUT_ON_LADDER, Commands.X, Commands.Y)) != null) {
                System.out.println(unitController.putOnLadder(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.PUSH_OFF_LADDER, Commands.X, Commands.Y)) != null) {
                System.out.println(unitController.pushOffLadder(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.POUR_OIL, Commands.DIRECTION)) != null) {
                System.out.println(unitController.pourOil(UserController.checkForQuotation(matcher.group("direction"))));
            } else if((matcher = getRealMatcher(input, Commands.DIG_TUNNEL, Commands.X, Commands.Y)) != null) {
                System.out.println(unitController.digTunnel(getInt(matcher, "x"), getInt(matcher, "y")));
            } else if((matcher = getRealMatcher(input, Commands.BUILD_STRUCTURE, Commands.EQUIPMENT)) != null) {
                System.out.println(unitController.buildStructure(UserController.checkForQuotation(matcher.group("equipment"))));
            } else if((matcher = getRealMatcher(input, Commands.MOVE_STRUCTURE)) != null) {
                System.out.println(unitController.moveStructure(getInt(matcher , "x"), getInt(matcher, "y")));
            }
            else if(input.matches(Commands.DISBAND_UNIT.regex)) {
                System.out.println(unitController.disbandUnit());
            } else if(input.matches(Commands.BACK.regex)) {
                System.out.println("unit deselected!\nexiting unit menu...");
                unitController.deleteSelectedUnits();
                return;
            } else if(input.matches("\\s*show\\s+menu\\s*")) {
                System.out.println("you are in the unit menu right now!");
            }
            else System.out.println("Invalid command!");
        }
    }
}
