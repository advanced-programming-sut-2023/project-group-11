package controller;

import model.Stronghold;
import model.buildings.Building;
import model.buildings.ProductiveBuilding;
import model.map.Map;
import model.map.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowMapMenuController {
    private static Map currentMap;

    public static Tile[][] getTiles(ArrayList<Object> parameters) {
        int x = (Integer) parameters.get(0);
        int y = (Integer) parameters.get(1);
        int rowsCount = (Integer) parameters.get(2);
        int columnCount = (Integer) parameters.get(3);
        Tile[][] tiles = new Tile[rowsCount][columnCount];

        for (int i = 0; i < rowsCount; i++) {
            tiles[i] = new Tile[columnCount];
            for (int j = 0; j < columnCount; j++)
                tiles[i][j] = currentMap.getTile(x + j, y + i);
        }

        return tiles;
    }

    public static ArrayList<Tile> getTilesList(ArrayList<Object> parameters) {
        int x = (Integer) parameters.get(0);
        int y = (Integer) parameters.get(1);
        int rowsCount = (Integer) parameters.get(2);
        int columnCount = (Integer) parameters.get(3);
        Tile[][] tiles = getTiles(new ArrayList<>(Arrays.asList(x, y, rowsCount, columnCount)));
        ArrayList<Tile> result = new ArrayList<>();

        for (Tile[] tileRow : tiles)
            result.addAll(Arrays.asList(tileRow));

        return result;
    }

    public static int getCurrentMapSize(ArrayList<Object> parameters) {
        return currentMap.getSize();
    }

    public static void setCurrentMap(ArrayList<Object> parameters) {
        String mapName = (String) parameters.get(0);
        currentMap = Stronghold.getMapByName(mapName);
    }

    public static Tile getSelectedTile(ArrayList<Object> parameters) {
        int selectedTileX = (Integer) parameters.get(0);
        int selectedTileY = (Integer) parameters.get(1);
        int firstTileX = (Integer) parameters.get(2);
        int firstTileY = (Integer) parameters.get(3);

        return currentMap.getTile(selectedTileX + firstTileX, selectedTileY + firstTileY);
    }

    public static String getTilesData(ArrayList<Object> parameters) {
        ArrayList<Tile> selectedTiles = (ArrayList<Tile>) parameters.get(0);
        double averageProduction = 0;
        int totalSoldiers = 0;
        int totalProduction = 0;
        int maxProduction = 0;
        int minProduction = 0;
        int buildingProduction;
        int buildingCounts = 0;

        for (Tile selectedTile : selectedTiles) {
            totalSoldiers += selectedTile.getUnits().size();
            Building building = selectedTile.getBuilding();
            if (building instanceof ProductiveBuilding productiveBuilding) {
                buildingProduction = productiveBuilding.getProductionRate();
                totalProduction += buildingProduction;
                maxProduction = Math.max(maxProduction, buildingProduction);
                minProduction = Math.min(minProduction, buildingProduction);
                buildingCounts++;
            }
        }

        if (buildingCounts > 0) averageProduction = (double) totalProduction / buildingCounts;

        return "Total soldiers=" + totalSoldiers +
                "\nAverage production=" + averageProduction +
                "\nMaximum production=" + maxProduction +
                "\nMinimum production=" + minProduction;
    }
}