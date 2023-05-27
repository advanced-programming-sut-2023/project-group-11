package view.commandLineView;

import controller.BuildingUtils;
import controller.SelectBuildingMenuController;
import view.enums.commands.SelectBuildingMenuCommands;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectBuildingMenu {
    public static void run(int x, int y) {
        if (!SelectBuildingMenuController.hasCommand(x, y)) return;
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.BACK) != null) {
                System.out.println("Entered Game Menu");
                return;
            } else if (SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.SHOW_CURRENT_MENU) != null) {
                System.out.println("Select Building Menu");
                System.out.println(BuildingUtils.getBuilding(x, y));
            } else if ((matcher = SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.CREATE_UNIT)) != null)
                checkCreateUnit(matcher, x, y);
            else if (SelectBuildingMenuCommands.getMatcher(command, SelectBuildingMenuCommands.REPAIR) != null)
                checkRepair(x, y);
            else System.out.println("Invalid Command!");
        }
    }

    private static void checkCreateUnit(Matcher matcher, int x, int y) {
        SelectBuildingMenuMessages message = SelectBuildingMenuController.checkCreateUnit(matcher, x, y);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_TYPE -> System.out.println("Invalid Type!");
            case CANT_CREATE_HERE -> System.out.println("Can't Create Unit Here!");
            case NOT_ENOUGH_GOLD -> System.out.println("You Don't Have Enough Gold!");
            case NOT_ENOUGH_RESOURCE -> System.out.println("You don't have enough equipment!");
            case BAD_UNIT_MAKER_PLACE -> System.out.println("No places are around the unit maker to put the unit(s)!");
            case NOT_ENOUGH_POPULATION -> System.out.println("You don't have enough population!");
            case SUCCESS -> System.out.println("You created " + matcher.group("count") + " Troop(s) in "
                    + "x=" + SelectBuildingMenuController.getUnitCreationCoordinates()[0]
                    + " and y=" + SelectBuildingMenuController.getUnitCreationCoordinates()[1]
                    + " coordinates successfully!");
        }
    }

    private static void checkRepair(int x, int y) {
        SelectBuildingMenuMessages message = SelectBuildingMenuController.checkRepair(x, y);

        switch (message) {
            case CANT_REPAIR -> System.out.println("Can't Repair This Building!");
            case NO_NEED_TO_REPAIR -> System.out.println("There's No Need To Repair This Building!");
            case NOT_ENOUGH_RESOURCE -> System.out.println("You Don't Have Enough Resource To Repair!");
            case ENEMY_AROUND -> System.out.println("There is enemy around and ypu can't repair this building!");
            case SUCCESS -> System.out.println("Building Has Been Repaired Successfully!");
        }
    }
}
