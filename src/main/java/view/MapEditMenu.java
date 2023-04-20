package view;

import controller.MapEditMenuController;
import view.Enums.Commands.MapEditMenuCommands;
import view.Enums.Messages.MapEditMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapEditMenu {
    private static MapEditMenuMessages mapEditMenuMessage;


    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        String input;
        Matcher matcher;

        while (true){
            System.out.println("Maps:");
            System.out.println(MapEditMenuController.getMapsList());
            System.out.println("Please choose your menu name (type \"exit\" for going back to main menu):");

            input = scanner.nextLine();

            if(input.matches("exit")) return;
            else if (MapEditMenuController.isMapName(input))
                MapEditMenuController.setCurrentMap(input);
            else {
                System.out.println("Invalid input! Try again!");
                continue;
            }

            while (true) {
                input = scanner.nextLine();

                if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.SET_TEXTURE)) != null)
                    checkSetTexture(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.CLEAR)) != null)
                    clear(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.DROP_CLIFF)) != null)
                    checkDropRock(matcher);
                else if ((matcher = MapEditMenuCommands.getMatcher(input, MapEditMenuCommands.DROP_TREE)) != null)
                    checkDropTree(matcher);
                else if (input.matches("save")) {
                    System.out.println("Map saved!");
                    MapEditMenuController.saveMap();
                    break;
                } else System.out.println("Invalid command!");
            }
        }
    }

    private static void clear(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.clear(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Tile with x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + " successfully cleared!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinate!");
        }
    }

    private static void checkSetTexture(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.checkSetTexture(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Map edited successfully in x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + "!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_TEXTURE_TYPE -> System.out.println("Invalid texture type!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinates!");
            case INVALID_DIRECTION -> System.out.println("Invalid direction!");
            case INVALID_PLACE_TO_DEPLOY -> System.out.println("Invalid place to deploy!");
            case INVALID_COMMAND_FOR_CONSTANT_SIZE ->
                    System.out.println("You can't set size for constant-size textures!");
        }
    }

    private static void checkDropRock(Matcher matcher) {
        mapEditMenuMessage = MapEditMenuController.checkDropRock(matcher);

        switch (mapEditMenuMessage) {
            case SUCCESS -> System.out.println("Rock successfully placed in x=" + matcher.group("x") +
                    " and y=" + matcher.group("y") + "!");
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinates!");
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
