package controller;

import model.Game;
import model.Governance;
import model.Stronghold;
import model.buildings.Keep;
import model.map.Tile;
import model.people.Lord;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {
    public static MainMenuMessages checkStartGame(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "mapName", "guests"))
            return MainMenuMessages.INVALID_COMMAND;

        String mapName = matcher.group("mapName");
        String[] listOfPlayers = makeListOfPlayers(matcher.group("guests")).split("-");

        if (!Stronghold.isMapName(mapName))
            return MainMenuMessages.MAP_NOT_EXIST;

        for (String name : listOfPlayers)
            if (!Stronghold.usernameExist(name)) return MainMenuMessages.USER_NOT_EXIST;


        Stronghold.setCurrentGame(new Game(makeGovernances(listOfPlayers), Stronghold.getMapByName(mapName)));
        return MainMenuMessages.SUCCESS;
    }

    private static ArrayList<Governance> makeGovernances(String[] listOfPlayers) {
        ArrayList<Governance> governances = new ArrayList<>();
        governances.add(new Governance(Stronghold.getCurrentUser()));

        for (String name : listOfPlayers)
            governances.add(new Governance(Stronghold.getUserByUsername(name)));

        return governances;
    }

    public static String makeListOfPlayers(String guests) {
        String result = "";
        String[] list = guests.split(" ");
        for (String name : list) {
            result += Utils.removeDoubleQuotation(name) + "-";
        }
        return result.substring(0, result.length() - 1);
    }

    public static void initializeAreas(String playerName, ArrayList<Integer> areas, int selectedArea) {
        int x, y;
        int mapSize = Stronghold.getCurrentGame().getMap().getSize();
        Governance currentGovernance = Stronghold.getCurrentGame().getGovernances().get(0);

        if (playerName != null) {
            for (Governance governance : Stronghold.getCurrentGame().getGovernances()) {
                if (governance.getOwner().getUsername().equals(playerName)) {
                    currentGovernance = governance;
                    break;
                }
            }
        }

        Lord lord = new Lord(currentGovernance);
        Keep keep = new Keep();
        x = getXYBySelectedArea(selectedArea, mapSize)[0];
        y = getXYBySelectedArea(selectedArea, mapSize)[1];

        BuildingUtils.build(currentGovernance, keep, x, y, keep.getSize());
        dropLord(currentGovernance, lord, x, y);

        areas.remove(Integer.valueOf(selectedArea));
    }

    private static void dropLord(Governance currentGovernance, Lord lord, int x, int y) {
        Tile tile = Stronghold.getCurrentGame().getMap().getTile(x, y);
        tile.getUnits().add(lord);
        lord.setLocation(new int[]{x, y});
        currentGovernance.addUnit(lord);
    }

    private static int[] getXYBySelectedArea(int selectedArea, int mapSize) {
        int[] coordinate = new int[2];
        switch (selectedArea) {
            case 1 -> {
                coordinate[0] = mapSize / 6;
                coordinate[1] = mapSize / 6;
            }
            case 2 -> {
                coordinate[0] = mapSize / 6;
                coordinate[1] = mapSize / 2;
            }
            case 3 -> {
                coordinate[0] = mapSize / 6;
                coordinate[1] = 5 * mapSize / 6;
            }
            case 4 -> {
                coordinate[0] = mapSize / 2;
                coordinate[1] = mapSize / 6;
            }
            case 5 -> {
                coordinate[0] = mapSize / 2;
                coordinate[1] = 5 * mapSize / 6;
            }
            case 6 -> {
                coordinate[0] = 5 * mapSize / 6;
                coordinate[1] = mapSize / 6;
            }
            case 7 -> {
                coordinate[0] = 5 * mapSize / 6;
                coordinate[1] = mapSize / 2;
            }
            case 8 -> {
                coordinate[0] = 5 * mapSize / 6;
                coordinate[1] = 5 * mapSize / 6;
            }
        }
        return coordinate;
    }
}
