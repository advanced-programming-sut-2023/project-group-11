package view;

import controller.ShowMapMenuController;
import controller.SignupMenuController;
import controller.Utils;
import model.Stronghold;
import view.enums.commands.ShowMapMenuCommands;
import view.enums.messages.ShowMapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShowMapMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command;
        command = scanner.nextLine();

        while (true) {
            if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.END) != null) Utils.endStronghold();
            else if (ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.BACK) != null) return;
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_MAP)) != null)
                checkShowMap(matcher);
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_MAP)) != null)
                checkShowMap(matcher);
            else if ((matcher = ShowMapMenuCommands.getMatcher(command, ShowMapMenuCommands.SHOW_MAP)) != null)
                checkShowMap(matcher);
            command = scanner.nextLine();
        }
    }

    private static void checkShowDetails(Integer xCoordinate, Integer yCoordinate) {

    }

    private static void checkMoveInMap(Matcher matcher, Integer xCoordinate, Integer yCoordinate) {

    }

    public static void checkShowMap(Matcher matcher) {
        ShowMapMenuMessages message = ShowMapMenuController.checkShowMap(matcher);
        switch (message) {
            case INVALID_COORDINATE -> System.out.println("Invalid coordinate!");
            case SUCCESS -> System.out.println(ShowMapMenuController.showMap(matcher));
        }
    }
}
