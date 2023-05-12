package view;

import controller.ShowMapMenuController;
import controller.Utils;
import view.enums.commands.ShowMapMenuCommands;
import view.enums.messages.ShowMapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShowMapMenu {
    public static int xCoordinate, yCoordinate;

    public static void run(int x, int y) {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command;
        xCoordinate = x;
        yCoordinate = y;
        System.out.println(ShowMapMenuController.showMap(xCoordinate, yCoordinate));

        command = scanner.nextLine();

        while (true) {
            if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.END) != null) Utils.endStronghold();
            else if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.BACK) != null) {
                System.out.println("Entered Game Menu");
                return;
            } else if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Show Map Menu");
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.MOVE_IN_MAP)) != null)
                checkMoveInMap(matcher, command);
            else System.out.println("Invalid command!");

            System.out.println(ShowMapMenuController.showMap(xCoordinate, yCoordinate));
            command = scanner.nextLine();
        }
    }

    private static void checkMoveInMap(Matcher matcher, String command) {
        ShowMapMenuMessages message = ShowMapMenuController.checkMoveInMap(matcher, command);
        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid move! You wil be out of the map");
            case SUCCESS -> System.out.println("You moved to x=" + xCoordinate + " y=" + yCoordinate + '!');
        }
    }
}
