package controller;

import model.Path;
import model.Stronghold;
import model.map.Map;
import model.map.Texture;
import model.map.Tile;
import model.people.Engineer;
import model.people.Troops;
import model.people.Units;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.regex.Matcher;

public class SelectUnitMenuController {
    public static SelectUnitMenuMessages checkMoveUnit(Matcher matcher, int[] currentLocation) {
        if (!Utils.isValidCommandTags(matcher, "xGroup", "yGroup"))
            return SelectUnitMenuMessages.INVALID_COMMAND;

        Map map = Stronghold.getCurrentGame().getMap();
        int x = Integer.parseInt(matcher.group("xCoordinate"));
        int y = Integer.parseInt(matcher.group("yCoordinate"));
        int currentX = currentLocation[0];
        int currentY = currentLocation[1];

        if (!Utils.isValidCoordinates(map, x, y))
            return SelectUnitMenuMessages.INVALID_COORDINATE;
        else if (notValidDestinationTexure(map.getTile(x, y)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_TEXTURE;
        else if (notValidDestinationDifferentUnits(map.getTile(currentX, currentY), map.getTile(x, y)))
            return SelectUnitMenuMessages.INVALID_DESTINATION_DIFFERENT_UNIT;
        else if (findRootToDestination(map, x, y) == null)
            return SelectUnitMenuMessages.INVALID_DISTANCE;

//      TODO: do dfs and find shortest path
//      TODO: move moving to select unit menu

        currentLocation[0] = x;
        currentLocation[1] = y;
        return SelectUnitMenuMessages.SUCCESS;
    }

    private static SelectUnitMenuMessages checkPatrolUnit(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkSetUnitState(Matcher matcher) {
        return null;
    }
    private static SelectUnitMenuMessages checkAttackUnit(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkPourOil(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkDigTunnel(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages checkBuildMachine(Matcher matcher) {
        return null;
    }

    private static SelectUnitMenuMessages disbandUnit() {
        return null;
    }

    private static Path findRootToDestination(Map map, int x, int y) {
        return null;
    }

    private static boolean notValidDestinationTexure(Tile destination) {
        return destination.getTexture().equals(Texture.MARSH) ||
                destination.getTexture().equals(Texture.RIVER) ||
                destination.getTexture().equals(Texture.SEA) ||
                destination.getTexture().equals(Texture.SMALL_LAKE) ||
                destination.getTexture().equals(Texture.BIG_LAKE) ||
                destination.getTexture().equals(Texture.CLIFF);
    }

    private static boolean notValidDestinationDifferentUnits(Tile currentTile, Tile destination) {
        for (Units unit : destination.getUnits()) {
            if (!unit.getOwnerGovernance().equals(Stronghold.getCurrentGame().getCurrentGovernance()))
                return true;
            if (unit instanceof Engineer && !(currentTile.getUnits().get(0) instanceof Engineer))
                return true;
            else if (!((Troops) unit).getType().equals(((Troops) currentTile.getUnits().get(0)).getType()))
                return true;
        }
        return false;
    }

    private static void attackMachine() {

    }
}
