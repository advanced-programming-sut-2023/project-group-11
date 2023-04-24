package controller;

import view.enums.messages.GameMenuMessages;

import java.util.regex.Matcher;

public class GameMenuController {
    private static String showPopularity() {
        //show popularity factors and show popularity
        return null;
    }

    private static String showFoodList() {
        return null;
    }

    private static GameMenuMessages checkChangeFoodRate(Matcher matcher) {
        return null;
    }

    private static String showFoodRate() {
        return null;
    }

    private static GameMenuMessages checkChangeTaxRate(Matcher matcher) {
        return null;
    }

    private static String showTaxRate() {
        return null;
    }

    private static GameMenuMessages checkChangeFearRate(Matcher matcher) {
        return null;
    }

    private static String showFearRate() {
        return null;
    }

    private static GameMenuMessages checkDropBuilding(Matcher matcher) {
        return null;
    }

    private static GameMenuMessages checkSelectBuilding(Matcher matcher) {
        return null;
    }

    private static void nextTurn() {

    }

    private static void updatePopulation() {

    }

    private static void updateSoldiers() {

    }

    private static void updateStorage() {

    }

    private static void updatePopularityRate() {

    }

    private static void fight() {

    }

    public static GameMenuMessages checkShowMap(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "xCoordinate", "yCoordinate"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        if (!Utils.isValidCoordinates(x, y)) return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }
}
