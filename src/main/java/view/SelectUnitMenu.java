package view;

import controller.SelectUnitMenuController;
import view.enums.commands.SelectUnitMenuCommands;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectUnitMenu {
    private static SelectUnitMenuMessages message;

    public static void run(int[] currentLocation) {
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.MOVE_UNIT)) != null)
                checkMoveUnit(matcher, currentLocation);
        }
    }

    private static void checkMoveUnit(Matcher matcher, int[] currentLocation) {
        message = SelectUnitMenuController.checkMoveUnit(matcher, currentLocation);

        switch (message) {
            case SUCCESS -> System.out.println("Units Moved Successfully!");
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case INVALID_DESTINATION_TEXTURE -> System.out.println("Invalid Destination: Invalid Texture!");
            case INVALID_DESTINATION_DIFFERENT_UNIT -> System.out.println("Invalid Destination: Invalid Unit type in destination!");
            case INVALID_DISTANCE -> System.out.println("Invalid Destination: Too Far For Going There, Based On Unit's Speed!");
        }
    }

    private static void checkPatrolUnit(Matcher matcher) {

    }

    private static void checkSetUnitState(Matcher matcher) {

    }

    private static void checkAttackUnit(Matcher matcher) {

    }

    private static void checkPourOil(Matcher matcher) {

    }

    private static void checkDigTunnel(Matcher matcher) {

    }

    private static void checkBuildMachine(Matcher matcher) {

    }

    private static void disbandUnit() {

    }
}
