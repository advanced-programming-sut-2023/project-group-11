package controller;

import model.Game;
import model.Governance;
import model.Stronghold;
import model.buildings.Keep;
import model.buildings.Storage;
import model.buildings.enums.StorageType;
import model.map.Map;
import model.map.Territory;
import model.map.Texture;
import model.map.Tile;
import model.people.Lord;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {
    public static MainMenuMessages checkStartGame(Matcher matcher) {
        if (!Utils.isValidCommandTags(matcher, "mapName", "guests"))
            return MainMenuMessages.INVALID_COMMAND;

        String mapName = Utils.removeDoubleQuotation(matcher.group("mapName"));
        String[] listOfPlayers = makeListOfPlayers(matcher.group("guests")).split("-");

        if (!Stronghold.isMapName(mapName))
            return MainMenuMessages.MAP_NOT_EXIST;
        for (String name : listOfPlayers)
            if (!Stronghold.usernameExist(name)) return MainMenuMessages.USER_NOT_EXIST;
        for (String name : listOfPlayers)
            if (Stronghold.getUserByUsername(name).equals(Stronghold.getCurrentUser().getUsername()))
                return MainMenuMessages.OWNER_IN_GUESTS;


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
        String[] list = guests.split(" (?=[^\"]*(?:(?:\"[^\"]*){2})*$)");
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
        Keep keep = new Keep(currentGovernance);
        x = getXYBySelectedArea(selectedArea, mapSize)[0];
        y = getXYBySelectedArea(selectedArea, mapSize)[1];

        buildKeep(keep, x, y);
        BuildingUtils.build(currentGovernance, new Storage(StorageType.STOCKPILE), x - 1, y, 1);
        BuildingUtils.build(currentGovernance, new Storage(StorageType.GRANARY), x, y - 1, 1);
        dropLord(currentGovernance, lord, x, y);
        currentGovernance.setTerritory(Territory.getTerritoryByArea(selectedArea));

        areas.remove(Integer.valueOf(selectedArea));
    }

    private static void buildKeep(Keep keep, int x, int y) {
        int size = keep.getSize();
        Map map = Stronghold.getCurrentGame().getMap();
        Tile[][] tiles = map.getTiles();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                tiles[x + i][y + j].setBuilding(keep);
                tiles[x + i][y + j].setTexture(Texture.SAND);
            }
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
