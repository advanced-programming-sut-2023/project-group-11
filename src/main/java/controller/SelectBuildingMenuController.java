package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.*;
import view.enums.messages.GameMenuMessages;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.regex.Matcher;

public class SelectBuildingMenuController {
    private static SelectBuildingMenuMessages checkCreateUnit(Matcher matcher) {
        return null;
    }

    public static boolean hasCommand(Matcher matcher){
        Building building = getBuilding(matcher);
        if(!((building instanceof UnitMaker) && (building instanceof Tower)
                && (building instanceof GateHouse) && (building instanceof Trap)))
            return false;
        return true;
    }

    public static boolean isUnitMaker(Matcher matcher){
        Building building = getBuilding(matcher);
        return building instanceof UnitMaker;
    }
    
    public static boolean isRepairable(Matcher matcher){
        Building building = getBuilding(matcher);
        return ((building instanceof UnitMaker) && (building instanceof Tower)
                && (building instanceof GateHouse) && (building instanceof Trap));
    }
    
    public static String selectBuildingDetails(Matcher matcher){
        Building building = getBuilding(matcher);
        return building.toString();
    }

    private static Building getBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("xGroup"));
        int y = Integer.parseInt(matcher.group("yGroup"));
        Building building = Stronghold.getCurrentGame().getMap().getTile(x,y).getBuilding();
        return building;
    }

    private static SelectBuildingMenuMessages repair() {
        return null;
    }

    private static SelectBuildingMenuMessages attackMachine() {
        return null;
    }
}
