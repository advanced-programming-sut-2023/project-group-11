package view;

import controller.BuildingUtils;
import controller.SelectBuildingMenuController;
import view.enums.commands.SelectBuildingMenuCommands;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectBuildingMenu {
    public static void run(Matcher matcher) {
        System.out.println(BuildingUtils.getBuilding(matcher).toString());
        if (!SelectBuildingMenuController.hasCommand(matcher)) return;
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher2;
        while (true) {
            command = scanner.nextLine();
            if (SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.BACK) != null) return;
            else if ((matcher2 = SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.CREATE_UNIT)) != null)
                checkCreateUnit(matcher2, matcher);
            else if (SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.REPAIR) != null)
                checkRepair(matcher);
            else System.out.println("Invalid Command!");
        }
    }

    private static void checkCreateUnit(Matcher matcher, Matcher buildingMatcher) {
        SelectBuildingMenuMessages message = SelectBuildingMenuController.checkCreateUnit(matcher, buildingMatcher);
        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_TYPE -> System.out.println("Invalid Type!");
            case CANT_CREATE_HERE -> System.out.println("Can't Create Unit Here!");
            case NOT_ENOUGH_GOLD -> System.out.println("You Don't Have Enough Gold!");
            case NOT_ENOUGH_RESOURCE -> System.out.println("You don't have enough equipment!");
            case SUCCESS -> System.out.println("Troop created successfully!");
        }
    }

    private static void checkRepair(Matcher buildingMatcher) {
        SelectBuildingMenuMessages message = SelectBuildingMenuController.checkRepair(buildingMatcher);
        switch (message) {
            case CANT_REPAIR -> System.out.println("Can't Repair This Building!");
            case NO_NEED_TO_REPAIR -> System.out.println("There's No Need To Repair This Building!");
            case NOT_ENOUGH_RESOURCE -> System.out.println("You Don't Have Enough Resource To Repair!");
            case ENEMY_AROUND -> System.out.println("There is enemy around and ypu can't repair this building!");
            case SUCCESS -> System.out.println("Building Has Been Repaired Successfully!");
        }
    }

    private static void attackMachine() {

    }
}
