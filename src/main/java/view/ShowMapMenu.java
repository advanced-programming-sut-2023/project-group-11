package view;

import controller.ShowMapMenuController;
import controller.Utils;
import view.enums.commands.ShowMapMenuCommands;
import view.enums.messages.ShowMapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShowMapMenu {
    public static void run(int x, int y) {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command;
        command = scanner.nextLine();

        while (true) {
            if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.END) != null) Utils.endStronghold();
            else if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.BACK) != null) return;
            else if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Show Map Menu");
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.MOVE_IN_MAP)) != null)
                checkMoveInMap(matcher, command, x, y);
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_DETAILS)) != null)
                checkShowDetails(matcher, command);
            command = scanner.nextLine();
        }
    }

    private static void checkShowDetails(Matcher matcher, String command) {
        ShowMapMenuController.checkShowDetails(matcher, command);
    }

    private static void checkMoveInMap(Matcher matcher, String command, int x, int y) {
        ShowMapMenuMessages message = ShowMapMenuController.checkMoveInMap(matcher, command, x, y);
        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_COORDINATE -> System.out.println("Invalid coordinate");
            case SUCCESS ->
                    System.out.println(ShowMapMenuController.showMap(matcher.group("xCoordinate"), matcher.group("yCoordinate")));
        }
    }
}
