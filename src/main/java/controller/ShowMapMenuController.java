package controller;

import model.buildings.Building;
import model.buildings.ProductiveBuilding;
import model.map.Map;
import model.map.Tile;

import java.util.ArrayList;

public class ShowMapMenuController {
    private static Map currentMap;

    public static Tile[][] getTiles(int x, int y, int rowsCount, int columnCount) {
        Tile[][] tiles = new Tile[rowsCount][columnCount];
        for (int i = 0; i < rowsCount; i++) {
            tiles[i] = new Tile[columnCount];
            for (int j = 0; j < columnCount; j++)
                tiles[i][j] = currentMap.getTile(x + j, y + i);
        }
        return tiles;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        ShowMapMenuController.currentMap = currentMap;
    }

    public static Tile getSelectedTile(int selectedTileX, int selectedTileY, int firstTileX, int firstTileY) {
        return currentMap.getTile(selectedTileX + firstTileX, selectedTileY + firstTileY);
    }

    public static String getTilesData(ArrayList<Tile> selectedTiles) {
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