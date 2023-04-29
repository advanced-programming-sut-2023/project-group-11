package controller;

import model.Stronghold;
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

    public static GameMenuMessages checkDropBuilding(Matcher matcher) {
        if(!Utils.isValidCommandTags(matcher,"xGroup","yGroup","typeGroup"))
            return GameMenuMessages.INVALID_COMMAND;
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        String type = matcher.group("typeGroup");
        if(!BuildingUtils.isValidBuildingType(type))
            return GameMenuMessages.INVALID_BUILDING_TYPE;
        int size = BuildingUtils.getBuildingSizeByName(type);
        if(!BuildingUtils.isValidCoordinates(Stronghold.getCurrentGame().getMap(),x,y,size))
            return GameMenuMessages.INVALID_COORDINATE;
        if(!BuildingUtils.isMapEmpty(x,y,size))
            return GameMenuMessages.CANT_BUILD_HERE;
        if(!BuildingUtils.isTextureSuitable(type,x,y,size))
            return GameMenuMessages.CANT_BUILD_HERE;
        BuildingUtils.build(type,x,y,size);
        return GameMenuMessages.SUCCESS;
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
        if (!Utils.isValidCoordinates(Stronghold.getCurrentGame().getMap(), x, y)) return GameMenuMessages.INVALID_COORDINATE;
        return GameMenuMessages.SUCCESS;
    }
}
