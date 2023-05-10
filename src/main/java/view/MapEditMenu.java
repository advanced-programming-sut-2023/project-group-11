package view;

import controller.MapEditMenuController;
import controller.Utils;
import model.Stronghold;
import view.enums.commands.MapEditMenuCommands;
import view.enums.messages.MapEditMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapEditMenu {
    private static MapEditMenuMessages mapEditMenuMessage;


    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        String input;
        Matcher matcher;

        while (true) {
            System.out.println("Maps:");
            System.out.print(MapEditMenuController.getMapsList());
            System.out.println("Please choose your map name:");
            System.out.println("(Type \"back\" for going back to main menu)");
            System.out.println("(Type \"new map\" for making a new map)");

            input = scanner.nextLine();

            if (MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.BACK) != null) {
                System.out.println("You have entered main menu!");
                return;
            } else if (MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Map edit menu");
            else if (MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.NEW_MAP) != null) {
                System.out.println("Enter the map's name:");
                String mapName = scanner.nextLine();
                if (Stronghold.isMapName(Utils.removeDoubleQuotation(mapName))) {
                    System.out.println("Repeated map name! Try again!");
                    continue;
                }
                System.out.println("Enter size for the \"" + mapName + "\" map");
                int size = Integer.parseInt(scanner.nextLine());
                if (size <= 50 || size >= 500) {
                    System.out.println("Invalid size! Try again!");
                    continue;
                }
                MapEditMenuController.setNewMapAsCurrentMap(mapName, size);
            } else if (Stronghold.isMapName(input))
                MapEditMenuController.setCurrentMap(input);
            else {
                System.out.println("Invalid input! Try again!");
                continue;
            }

            System.out.println("Now you can edit the map!");

            while (true) {
                input = scanner.nextLine();

                if (MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.SHOW_MAP) != null)
                    checkShowMap();
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.SET_TEXTURE)) != null)
                    checkSetTexture(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.CLEAR)) != null)
                    clear(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.DROP_CLIFF)) != null)
                    checkDropCliff(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.DROP_TREE)) != null)
                    checkDropTree(matcher);
                else if (MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.SAVE) != null) {
                    System.out.println("Map saved!");
                    MapEditMenuController.saveMap();
                    break;
                } else System.out.println("Invalid command!");
            }
        }
    }

    private static void checkShowMap() {
        System.out.println(MapEditMenuController.showMap());
    }

    private static void clear(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.clear(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Tile with x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + " successfully cleared!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinate!");
        }
    }

    private static void checkSetTexture(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.checkSetTexture(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Map edited successfully!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_TEXTURE_TYPE -> System.out.println("Invalid texture type!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinates!");
            case INVALID_DIRECTION -> System.out.println("Invalid direction!");
            case INVALID_PLACE_TO_DEPLOY -> System.out.println("Invalid place to deploy!");
            case INVALID_COMMAND_FOR_CONSTANT_SIZE ->
                    System.out.println("You can't set size for constant-size textures!");
        }
    }

    private static void checkDropCliff(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.checkDropCliff(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Rock successfully placed in x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + "!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinates!");
            case INVALID_PLACE_TO_DEPLOY -> System.out.println("Invalid place to deploy!");
            case INVALID_DIRECTION -> System.out.println("Invalid direction!");
        }
    }

    private static void checkDropTree(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.checkDropTree(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Tree successfully placed in x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + "!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinates!");
            case INVALID_PLACE_TO_DEPLOY -> System.out.println("Invalid place for deploy!");
        }
    }
}
