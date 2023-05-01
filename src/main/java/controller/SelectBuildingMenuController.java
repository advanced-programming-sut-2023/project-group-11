package controller;

import model.Governance;
import model.Stronghold;
import model.buildings.*;
import model.people.Troops;
import model.people.enums.TroopTypes;
import model.resources.AllResource;
import view.enums.messages.GameMenuMessages;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.regex.Matcher;

public class SelectBuildingMenuController {
    public static SelectBuildingMenuMessages checkCreateUnit(Matcher matcher,Matcher BuildingMatcher) {

        if(!Utils.isValidCommandTags(matcher,"typeGroup","countGroup"))
            return SelectBuildingMenuMessages.INVALID_COMMAND;
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        Building building = getBuilding(BuildingMatcher);
        if(!isUnitMaker(building))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if(type.equals("engineer")){
            if(!building.getName().equals("engineer guild"))
                return SelectBuildingMenuMessages.CANT_CREATE_HERE;
            if(governance.getGold() < count * 30)
                return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;
            return SelectBuildingMenuMessages.SUCCESS;
        }
        try {
            TroopTypes.valueOf(type.toUpperCase());
        }catch (IllegalArgumentException e){
            return SelectBuildingMenuMessages.INVALID_TYPE;
        }
        Troops troop = new Troops(type);
        if((troop.isArab() && !building.getName().equals("mercenary tent")) || !building.getName().equals("barracks"))
            return SelectBuildingMenuMessages.CANT_CREATE_HERE;
        if(governance.getGold() < count * troop.getCost())
            return SelectBuildingMenuMessages.NOT_ENOUGH_GOLD;
        return SelectBuildingMenuMessages.SUCCESS;
    }

    public static boolean hasCommand(Matcher matcher){
        Building building = getBuilding(matcher);
        if(!((building instanceof UnitMaker) && (building instanceof Tower)
                && (building instanceof GateHouse) && (building instanceof Trap)))
            return false;
        return true;
    }

    public static boolean isUnitMaker(Building building){
        return building instanceof UnitMaker;
    }
    
    public static boolean isRepairable(Building building){
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

    public static SelectBuildingMenuMessages checkRepair(Matcher buildingMatcher) {
        Building building = getBuilding(buildingMatcher);
        Governance governance = Stronghold.getCurrentGame().getCurrentGovernance();
        if(!isRepairable(building))
            return SelectBuildingMenuMessages.CANT_REPAIR;
        if(building.getHitPoint() == building.getMaxHitPoint())
            return SelectBuildingMenuMessages.NO_NEED_TO_REPAIR;
        if(!governance.hasEnoughItem(AllResource.STONE,8))
            return SelectBuildingMenuMessages.NOT_ENOUGH_RESOURCE;
        governance.removeFromStorage(AllResource.STONE,8);
        building.repair();
        return SelectBuildingMenuMessages.SUCCESS;
    }

    private static SelectBuildingMenuMessages attackMachine() {
        return null;
    }
}
