package view;

import controller.BuildingUtils;
import controller.GameMenuController;
import controller.Utils;
import view.enums.commands.GameMenuCommands;
import view.enums.messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private static GameMenuMessages message;

    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher;

        GameMenuController.setCurrentGame();

        while (true) {
            command = scanner.nextLine();

            if (GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN) != null)
                nextTurn();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                checkShowMap(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY)) != null)
                showPopularity(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FOOD_LIST) != null)
                showFoodList();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_FOOD_RATE)) != null)
                checkChangeFoodRate(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FOOD_RATE) != null)
                showFoodRate();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_TAX_RATE)) != null)
                checkChangeTaxRate(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_TAX_RATE) != null)
                showTaxRate();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_FEAR_RATE)) != null)
                checkChangeFearRate(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FEAR_RATE) != null)
                showFearRate();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)) != null)
                checkDropBuilding(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null)
                checkSelectBuilding(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)) != null)
                checkSelectUnit(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_UNIT)) != null)
                checkDropUnit(matcher);
            else System.out.println("Invalid command!");
        }
    }

    private static void nextTurn() {

    }

    public static void checkShowMap(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkShowMap(matcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid command");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinate!");
            case SUCCESS -> {
                System.out.println("Entered Show Map Menu");
                int x = Integer.parseInt(matcher.group("xCoordinate"));
                int y = Integer.parseInt(matcher.group("yCoordinate"));
                ShowMapMenu.run(x, y);
            }
        }
    }

    private static void showPopularity(Matcher matcher) {
        System.out.println(GameMenuController.showPopularity(matcher));
    }

    private static void showFoodList() {
        System.out.print(GameMenuController.showFoodList());
    }

    private static void checkChangeFoodRate(Matcher matcher) {
        message = GameMenuController.checkChangeFoodRate(matcher);

        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -2 and 2!");
            case SUCCESS -> System.out.println("You have successfully changed your food rate!");
        }
    }

    private static void showFoodRate() {
        System.out.println(GameMenuController.showFoodRate());
    }

    private static void checkChangeTaxRate(Matcher matcher) {
        message = GameMenuController.checkChangeTaxRate(matcher);

        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -3 and 8!");
            case SUCCESS -> System.out.println("You have successfully changed your tax rate!");
        }
    }

    private static void showTaxRate() {
        System.out.println(GameMenuController.showTaxRate());
    }

    private static void checkChangeFearRate(Matcher matcher) {
        message = GameMenuController.checkChangeFearRate(matcher);

        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -5 and 5!");
            case SUCCESS -> System.out.println("You have successfully changed your fear rate!");
        }
    }

    private static void showFearRate() {
        System.out.println(GameMenuController.showFearRate());
    }

    private static void checkDropBuilding(Matcher matcher) {
        message = GameMenuController.checkDropBuilding(matcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_BUILDING_TYPE -> System.out.println("Invalid Building Type!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case CANT_BUILD_HERE -> System.out.println("Can't Build Here!");
            case NOT_ENOUGH_MONEY -> System.out.println("Gold needed!");
            case NOT_ENOUGH_RESOURCE -> System.out.println("More resource needed");
            case NOT_ENOUGH_POPULATION -> System.out.println("Need more workers!");
            case SUCCESS -> System.out.println("Building Has Been Built Successfully!");
        }
    }

    private static void checkSelectBuilding(Matcher matcher) {
        message = GameMenuController.checkSelectBuilding(matcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case NO_BUILDING_HERE -> System.out.println("There's No Building Here To Select!");
            case NOT_YOUR_BUILDING -> System.out.println("It's Not Your Building!");
            case SUCCESS -> {
                int xCoordinate = Integer.parseInt(matcher.group("xCoordinate"));
                int yCoordinate = Integer.parseInt(matcher.group("yCoordinate"));
                System.out.println(BuildingUtils.getBuilding(xCoordinate, yCoordinate));
                SelectBuildingMenu.run(xCoordinate, yCoordinate);
            }
        }
    }

    private static void checkSelectUnit(Matcher matcher) {
        message = GameMenuController.checkSelectUnit(matcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case INVALID_UNIT_TYPE -> System.out.println("Invalid Unit type");
            case NO_UNIT_HERE -> System.out.println("There's No Unit Here To Select!");
            case NOT_YOUR_UNIT -> System.out.println("It's Not Your Unit!");
            case SUCCESS -> {
                System.out.println(Utils.removeDoubleQuotation(matcher.group("type")) +
                        " Unit(s) Selected (Entered SelectUnit Menu)!");
                int[] currentLocation = {
                        Integer.parseInt(matcher.group("xCoordinate")),
                        Integer.parseInt(matcher.group("yCoordinate"))
                };
                SelectUnitMenu.run(currentLocation, Utils.removeDoubleQuotation(matcher.group("type")));
            }
        }
    }

    private static void checkDropUnit(Matcher matcher) {
        message = GameMenuController.checkDropUnit(matcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinate!");
            case NOT_ENOUGH_SPACE -> System.out.println("There's Not Enough Space For Unit!");
            case INVALID_UNIT_TYPE -> System.out.println("Invalid Unit Type!");
            case INVALID_TEXTURE -> System.out.println("Can't Drop Units In This Texture!");
            case CANT_DROP_IN_BULDING -> System.out.println("Can't Drop Units In This Building!");
            case SUCCESS -> System.out.println("Units Have Been Dropped Successfully!");
        }
    }
}