package controller;

import model.Stronghold;
import model.buildings.*;
import model.buildings.enums.*;
import model.map.Map;
import model.map.Tile;

public class BuildingUtils {

    public static boolean isValidCoordinates(Map map, int x, int y, int size) {
        int mapSize = map.getSize();
        return x >= 0 && x + size < mapSize && y >= 0 && y + size < mapSize;
    }

    public static boolean isValidBuildingType(String type) {
        return ChurchType.getChurchTypeByName(type) != null
                || FillerType.getFillerTypeByName(type) != null
                || GateHouseType.getGateHouseTypeByName(type) != null
                || ProductiveBuildingType.getProductiveBuildingTypeByName(type) != null
                || StorageType.getStorageTypeByName(type) != null
                || TowerType.getTowerTypeByName(type) != null
                || TrapType.getTrapTypeByName(type) != null
                || UnitMakerType.getUnitMakerTypeByName(type) != null;
    }

    public static int getBuildingSizeByName(String type){
        if(ChurchType.getChurchTypeByName(type) != null)
            return ChurchType.getChurchTypeByName(type).getSize();
        if(FillerType.getFillerTypeByName(type) != null)
            return FillerType.getFillerTypeByName(type).getSize();
        if(GateHouseType.getGateHouseTypeByName(type) != null)
            return GateHouseType.getGateHouseTypeByName(type).getSize();
        if(ProductiveBuildingType.getProductiveBuildingTypeByName(type) != null)
            return ProductiveBuildingType.getProductiveBuildingTypeByName(type).getSize();
        if(StorageType.getStorageTypeByName(type) != null)
            return StorageType.getStorageTypeByName(type).getSize();
        if(TowerType.getTowerTypeByName(type) != null)
            return TowerType.getTowerTypeByName(type).getSize();
        if(TrapType.getTrapTypeByName(type) != null)
            return TrapType.getTrapTypeByName(type).getSize();
        if(UnitMakerType.getUnitMakerTypeByName(type) != null)
            return UnitMakerType.getUnitMakerTypeByName(type).getSize();

        return 0;
    }

    public static boolean isMapEmpty(int x,int y,int size){
        Tile[][] tiles = Stronghold.getCurrentGame().getMap().getMap();
        for(int i = x;i<x+size;i++){
            for(int j=y;j>y-size;j--){
                if(tiles[i][j].isFull())
                    return false;
            }
        }
        return true;
    }

    public static boolean isTextureSuitable(String type,int x,int y,int size){
        Tile[][] tiles = Stronghold.getCurrentGame().getMap().getMap();
        String field;
        if(type.equals("dairy farm") || type.equals("apple orchard")
                || type.equals("wheat farm") || type.equals("hops farm")){
            field = "farm";
        }else if(type.equals("quarry"))
            field = "stone";
        else if(type.equals("iron mine"))
            field = "iron";
        else
            field = "default";
        for(int i = x;i<x+size;i++){
            for(int j=y;j>y-size;j--){
                if(!isSuitable(tiles[i][j],field))
                    return false;
            }
        }
        return true;
    }

    private static boolean isSuitable(Tile tile,String field){
        switch (field){
            case "farm" -> {
                if(!tile.getTexture().isGrass())
                    return false;
            }
            case "iron" -> {
                if(!tile.getTexture().isIron())
                    return false;
            }
            case "stone" -> {
                if(!tile.getTexture().isStone())
                    return false;
            }
            case "default" -> {
                if(!tile.getTexture().isBuildable())
                    return false;
            }
        }
        return true;
    }

    public static void build(String type,int x,int y,int size){

        Building building = new Building() {};
        Tile[][] tiles = Stronghold.getCurrentGame().getMap().getMap();
        if(ChurchType.getChurchTypeByName(type) != null)
            building = new Church(ChurchType.getChurchTypeByName(type));
        if(FillerType.getFillerTypeByName(type) != null)
            building = new Filler(FillerType.getFillerTypeByName(type));
        if(GateHouseType.getGateHouseTypeByName(type) != null)
            building = new GateHouse(GateHouseType.getGateHouseTypeByName(type));
        if(ProductiveBuildingType.getProductiveBuildingTypeByName(type) != null)
            building = new ProductiveBuilding(ProductiveBuildingType.getProductiveBuildingTypeByName(type));
        if(StorageType.getStorageTypeByName(type) != null)
            building = new Storage(StorageType.getStorageTypeByName(type));
        if(TowerType.getTowerTypeByName(type) != null)
            building = new Tower(TowerType.getTowerTypeByName(type));
        if(TrapType.getTrapTypeByName(type) != null)
            building = new Trap(TrapType.getTrapTypeByName(type));
        if(UnitMakerType.getUnitMakerTypeByName(type) != null)
            building = new UnitMaker(UnitMakerType.getUnitMakerTypeByName(type));

        building.setOwner(Stronghold.getCurrentGame().getCurrentGovernance());
        if(building instanceof Storage){
            building.getOwner().getStorages().add((Storage) building);
        }

        for(int i = x;i<x+size;i++){
            for(int j=y;j>y-size;j--){
                tiles[i][j].setBuilding(building);
            }
        }
    }

}
