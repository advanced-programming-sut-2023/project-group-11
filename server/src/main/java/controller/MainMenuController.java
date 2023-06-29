package controller;

import javafx.collections.ObservableList;
import model.Game;
import model.Governance;
import model.Stronghold;
import model.User;
import model.buildings.Keep;
import model.buildings.Storage;
import model.buildings.enums.StorageType;
import model.map.Map;
import model.map.Territory;
import model.map.Texture;
import model.map.Tile;
import model.people.Lord;

import java.util.ArrayList;

public class MainMenuController {
    public static void startGame(ArrayList<Object> parameters) throws Exception {
        ArrayList<User> users = (ArrayList<User>) parameters.get(0);
        String mapName = (String) parameters.get(1);
        String[] usernames = makeListOfPlayers(users);
        Map map = Stronghold.getMapByName(mapName);
        Stronghold.setCurrentGame(new Game(makeGovernances(usernames), map));
        ArrayList<Integer> areas = new ArrayList<>();
        for (int i = 1; i <= 8; i++) areas.add(i);


        initializeAreas(null, areas, 1);
        for (int i = 0; i < usernames.length; i++)
            initializeAreas(usernames[i], areas, i + 2);

        ShowMapMenuController.setCurrentMap(map);

    }

    public static ArrayList<Governance> makeGovernances(String[] listOfPlayers) {
        ArrayList<Governance> governances = new ArrayList<>();
        governances.add(new Governance(Stronghold.getCurrentUser()));

        for (String name : listOfPlayers)
            governances.add(new Governance(Stronghold.getUserByUsername(name)));

        return governances;
    }

    public static String[] makeListOfPlayers(ArrayList<User> users) {
        ArrayList<String> usernames = new ArrayList<>();

        for (User user : users)
            usernames.add(user.getUsername());
        return usernames.toArray(new String[0]);
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

        buildKeep(currentGovernance, keep, x, y);
        BuildingUtils.build(currentGovernance, new Storage(StorageType.STOCKPILE), x - 2, y);
        BuildingUtils.build(currentGovernance, new Storage(StorageType.GRANARY), x, y - 2);
        currentGovernance.initializeStorages();
        dropLord(currentGovernance, lord, x, y);
        currentGovernance.setTerritory(Territory.getTerritoryByArea(selectedArea));

        areas.remove(Integer.valueOf(selectedArea));
    }

    private static void buildKeep(Governance currentGovernance, Keep keep, int x, int y) {
        int size = keep.getSize();
        Map map = Stronghold.getCurrentGame().getMap();
        Tile[][] tiles = map.getTiles();

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                tiles[y + i][x + j].setBuilding(keep);
                tiles[y + i][x + j].setTexture(Texture.SAND);
            }

        currentGovernance.addBuilding(keep);
        keep.setXCoordinate(x);
        keep.setYCoordinate(y);
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
        return new int[]{coordinate[1], coordinate[0]};
    }


    public static ObservableList<User> removeCurrentUserFromList(ArrayList<Object> parameters) {
        ObservableList<User> userObservableList = (ObservableList<User>) parameters.get(0);
        userObservableList.remove(Stronghold.getCurrentUser());//TODO: ArrayList<Object> parameters
        return userObservableList;
    }

    public static ObservableList<Governance> removeCurrentGovernanceFromList(ArrayList<Object> parameters) {
        ObservableList<Governance> governanceObservableList = (ObservableList<Governance>) parameters.get(0);
        governanceObservableList.remove(Stronghold.getCurrentGame().getCurrentGovernance());
        return governanceObservableList;
    }

    public static void logout(ArrayList<Object> parameters) {
        User currentUser = Stronghold.getCurrentUser();
        currentUser.setStayLoggedIn(false);
        Stronghold.setCurrentUser(null);
    }
}
