package view.commandLineView;

import controller.MainMenuController;
import controller.Utils;
import view.enums.commands.MainMenuCommands;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private static Scanner scanner;

    public static void run() {
        scanner = EntryMenu.getScanner();
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();

            if (MainMenuCommands.getMatcher(input, MainMenuCommands.END) != null)
                Utils.endStronghold();
            else if (MainMenuCommands.getMatcher(input, MainMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Main menu");
            else if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null) {
                System.out.println(Utils.logout() + " Entered entry menu!");
                return;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.ENTER_PROFILE_MENU) != null) {
                System.out.println("Entered profile menu!");
                ProfileMenu.run();
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.ENTER_MAP_EDIT_MENU) != null) {
                System.out.println("Entered map edit menu!");
                MapEditMenu.run();
            } else if ((matcher = MainMenuCommands.getMatcher(input, MainMenuCommands.START_GAME)) != null)
                checkStartGame(matcher);
            else System.out.println("Invalid command!");
        }
    }

    private static void checkStartGame(Matcher matcher) {
        MainMenuMessages mainMenuMessage = MainMenuController.checkStartGame(matcher);

        switch (mainMenuMessage) {
            case SUCCESS -> {
                initializeAreas(MainMenuController.makeListOfPlayers(matcher.group("guests")));
                System.out.println("Game started with: " + MainMenuController.makeListOfPlayers(matcher.group("guests")));
                GameMenu.run();
            }
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case MAP_NOT_EXIST -> System.out.println("Map does not exist!");
            case USER_NOT_EXIST -> System.out.println("At least one of the users does not exist!");
            case OWNER_IN_GUESTS -> System.out.println("You can't start game with yourself!");
        }
    }

    private static void initializeAreas(String guests) {
        String[] players = guests.split("-");
        ArrayList<Integer> areas = new ArrayList<>();
        int selectedArea;

        for (int i = 1; i <= 8; i++) {
            areas.add(i);
        }

        selectedArea = getSelectedArea("Yourself", areas);
        MainMenuController.initializeAreas(null, areas, selectedArea);

        for (String player : players) {
            selectedArea = getSelectedArea(player, areas);
            MainMenuController.initializeAreas(player, areas, selectedArea);
        }

        scanner.nextLine();
    }

    private static int getSelectedArea(String player, ArrayList<Integer> areas) {
        int selectedArea;
        System.out.println("Choose area for: " + player);
        System.out.println("From: " + areas);
        selectedArea = scanner.nextInt();
        while (!areas.contains(selectedArea)) {
            System.out.println("Invalid area!");
            System.out.println("Choose area for: " + player);
            System.out.println("From: " + areas);
            selectedArea = scanner.nextInt();
        }
        return selectedArea;
    }
}
