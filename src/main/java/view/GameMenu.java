package view;

import controller.GameMenuController;
import view.enums.messages.GameMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

        }
    }

    private static void showMap(Matcher matcher) {

    }

    private static void showPopularity(Matcher matcher) {
        System.out.println(GameMenuController.showPopularity(matcher));
    }

    private static void showFoodList() {
        System.out.println(GameMenuController.showFoodList());
    }

    private static void checkChangeFoodRate(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkChangeFoodRate(matcher);
        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -2 and 2!");
            case SUCCESS -> System.out.println("You have successfully changed your food rate!");
        }
    }

    private static void showFoodRate() {
        System.out.println(GameMenuController.showFoodRate());
    }

    private static void checkChangeTaxRate(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkChangeTaxRate(matcher);
        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -3 and 8!");
            case SUCCESS -> System.out.println("You have successfully changed your tax rate!");
        }
    }

    private static void showTaxRate() {
        System.out.println(GameMenuController.showTaxRate());
    }

    private static void checkChangeFearRate(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkChangeFearRate(matcher);
        switch (message) {
            case INVALID_RATE -> System.out.println("Your rate number must be between -5 and 5!");
            case SUCCESS -> System.out.println("You have successfully changed your fear rate!");
        }
    }

    private static void checkDropBuilding(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkDropBuilding(matcher);
        switch (message){
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_BUILDING_TYPE -> System.out.println("Invalid Building Type!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case CANT_BUILD_HERE -> System.out.println("Can't Build Here!");
            case SUCCESS -> System.out.println("Building Has Been Built Successfully!");
        }
    }

    private static void checkSelectBuilding(Matcher matcher) {
        GameMenuMessages message = GameMenuController.checkSelectBuilding(matcher);
        switch (message){
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case NO_BUILDING_HERE -> System.out.println("There's No Building Here To Select!");
            case NOT_YOUR_BUILDING -> System.out.println("It's Not Your Building");
            case SUCCESS -> {//TODO: implementation needed for create unit and repair
                System.out.println(GameMenuController.selectBuildingDetails(matcher));
            }
        }
    }

    private static void checkSelectUnit(Matcher matcher) {

    }

    private static void checkMoveUnit(Matcher matcher) {

    }

    private static void checkPatrolUnit(Matcher matcher) {

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
}