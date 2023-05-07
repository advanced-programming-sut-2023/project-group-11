package view;

import controller.SelectUnitMenuController;
import view.enums.commands.SelectUnitMenuCommands;
import view.enums.messages.SelectUnitMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectUnitMenu {
    private static SelectUnitMenuMessages message;

    public static void run(int[] currentLocation, String unitType) {
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.MOVE_UNIT)) != null)
                checkMoveUnit(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.PATROL_UNIT)) != null)
                checkPatrolUnit(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.AIR_ATTACK)) != null)
                checkAttackUnit(matcher, currentLocation, unitType, "air attack");
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.GROUND_ATTACK)) != null)
                checkAttackUnit(matcher, currentLocation, unitType, "ground attack");
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.BUILD_MACHINE)) != null)
                checkBuildMachine(matcher, currentLocation, unitType);
            else if (SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.DESELECT) != null)
                return;
            else System.out.println("Invalid Command!");
        }
    }

    private static void checkMoveUnit(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkMoveUnit(matcher, currentLocation, unitType);

        switch (message) {
            case SUCCESS -> System.out.println("Units Moved Successfully!");
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case INVALID_DESTINATION_TEXTURE -> System.out.println("Invalid Destination: Invalid Texture!");
            case INVALID_DESTINATION_UNCLIMBABLE_BUILDING ->
                    System.out.println("Invalid Destination: Invalid Building In Destination!");
            case INVALID_DESTINATION_DIFFERENT_OWNER_UNIT ->
                    System.out.println("Invalid Destination: Invalid Unit type in destination!");
            case INVALID_DISTANCE ->
                    System.out.println("Invalid Destination: Too Far For Going There, Based On Unit's Speed!");
        }
    }

    private static void checkAttackUnit(Matcher matcher, int[] currentLocation, String unitType, String attackType) {
        message = SelectUnitMenuController.checkAttack(matcher, currentLocation, unitType, attackType);

        switch (message) {
            case SUCCESS -> System.out.println(attackType.toUpperCase() + " Done Successfully!");
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinate!");
            case INVALID_UNIT_TYPE_TO_ATTACK ->
                    System.out.println("Cannot \"" + attackType.toUpperCase() + "\" With This Unit!");
            case OUT_OF_RANGE -> System.out.println("Target is Out Of Range!");
            case NO_ATTACK_LEFT -> System.out.println("You Attacked Once This Round With This Unit!");
            case EMPTY_TILE -> System.out.println("There Is No One In Target-Tile!");
            case FRIENDLY_ATTACK -> System.out.println("You Can't Attack To Your Own Troops And Building!");
        }
    }

    private static void checkBuildMachine(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkBuildMachine(matcher, currentLocation, unitType);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_MACHINE_TYPE -> System.out.println("Invalid Machine Type!");
            case NOT_ENOUGH_GOLD -> System.out.println("Not Enough Gold!");
            case NOT_ENOUGH_ENGINEERS -> System.out.println("There's Not Enough Engineers To Build!");
            case SUCCESS -> System.out.println("Machine Has Been Built Successfully!");
        }
    }

    private static void checkPatrolUnit(Matcher matcher, int[] currentLocation, String unitType) {
        checkMoveUnit(matcher, currentLocation, unitType);
        if (message.equals(SelectUnitMenuMessages.SUCCESS))
            SelectUnitMenuController.patrolUnit(matcher, currentLocation, unitType);
    }

    private static void stopPatrol(int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.stopPatrol(currentLocation, unitType);

        switch (message) {
            case NOT_PATROLLING -> System.out.println("Selected units are not patrolling!");
            case SUCCESS -> System.out.println("Selected units successfully are now fixed in current position!");
        }
    }

    private static void checkSetUnitState(Matcher matcher) {

    }

    private static void checkPourOil(Matcher matcher) {

    }

    private static void checkDigTunnel(Matcher matcher) {

    }

    private static void disbandUnit() {

    }
}
