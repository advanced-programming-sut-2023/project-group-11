package view.commandLineView;

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
                checkMoveUnit(matcher, currentLocation, unitType, false);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.PATROL_UNIT)) != null)
                checkPatrolUnit(matcher, currentLocation, unitType);
            else if (SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.STOP_PATROL) != null)
                checkStopPatrol(currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.SET_UNIT_STATE)) != null)
                checkSetUnitState(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.AIR_ATTACK)) != null)
                checkAttackUnit(matcher, currentLocation, unitType, "air attack");
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.GROUND_ATTACK)) != null)
                checkAttackUnit(matcher, currentLocation, unitType, "ground attack");
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.DIG_TUNNEL)) != null)
                checkDigTunnel(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.BUILD_MACHINE)) != null)
                checkBuildMachine(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.POUR_OIL)) != null)
                checkPourOil(matcher, currentLocation, unitType);
            else if ((matcher = SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.DIG_PITCH)) != null)
                checkDigPitch(matcher, currentLocation, unitType);
            else if (SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.STOP_PITCH) != null)
                checkStopDigging(currentLocation, unitType);
            else if (SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.DISBAND) != null) {
                disbandUnit(currentLocation, unitType);
                return;
            } else if (SelectUnitMenuCommands.getMatcher(command, SelectUnitMenuCommands.DESELECT) != null) {
                System.out.println("Entered Game Menu");
                return;
            } else System.out.println("Invalid Command!");
        }
    }

    private static void checkStopDigging(int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkStopDigging(currentLocation, unitType);

        switch (message) {
            case SUCCESS -> System.out.println("Unit Stopped Digging!");
            case NOT_DIGGING -> System.out.println("This Units Weren't Digging Pitch!");
        }
    }

    private static void checkMoveUnit(Matcher matcher, int[] currentLocation, String unitType, boolean isPatrol) {
        message = SelectUnitMenuController.checkMoveUnit(matcher, currentLocation, unitType, isPatrol);

        switch (message) {
            case SUCCESS -> System.out.println("Units Moved (patrolled) Successfully!");
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_COORDINATE -> System.out.println("Invalid Coordinates!");
            case INVALID_DESTINATION_TEXTURE -> System.out.println("Invalid Destination: Invalid Texture!");
            case NO_MOVES_NEEDED -> System.out.println("No Move Is Needed!");
            case NO_MOVES_LEFT -> System.out.println("No Moves Left!");
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
        checkMoveUnit(matcher, currentLocation, unitType, true);
    }

    private static void checkStopPatrol(int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkStopPatrol(currentLocation, unitType);

        switch (message) {
            case NOT_PATROLLING -> System.out.println("Selected units are not patrolling!");
            case SUCCESS -> System.out.println("Selected units are now fixed in current position!");
        }
    }

    private static void checkSetUnitState(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkSetUnitState(matcher, currentLocation, unitType);

        switch (message) {
            case INVALID_STATE -> System.out.println("Only these states are valid! standing, defensive, offensive ");
            case SUCCESS ->
                    System.out.println("you have successfully set selected unit state to " + matcher.group("state"));
        }
    }

    private static void checkPourOil(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkPourOil(matcher, currentLocation, unitType);

        switch (message) {
            case INVALID_UNIT_TYPE -> System.out.println("Selected Unit Type Is Invalid!");
            case NOT_ENOUGH_ENGINEERS -> System.out.println("There Are Not Enough Engineers Here!");
            case JUST_ONE_ENGINEER -> System.out.println("There Should Be Just One Engineer!");
            case ENGINEER_WITHOUT_PAIL -> System.out.println("This Engineer Hasn't Pail!");
            case ENGINEER_EMPTY_PAIL -> System.out.println("This Engineer's Pail Is Empty!");
            case CANT_REFILL_THE_PAIL -> System.out.println("Oil Has Been Poured But Couldn't Refill The Pail!");
            case SUCCESS -> System.out.println("Oil Has Been Poured And Pail Has Been Repaired!");
        }
    }

    private static void checkDigTunnel(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkDigTunnel(matcher, currentLocation, unitType);

        switch (message) {
            case INVALID_UNIT_TYPE_TO_DIG_TUNNEL -> System.out.println("Only tunnelers can dig tunnel!");
            case INVALID_DIRECTION -> System.out.println("Direction must be up or left or down or right!");
            case SUCCESS -> System.out.println("Tunnel dug successfully!");
        }
    }

    private static void disbandUnit(int[] currentLocation, String unitType) {
        SelectUnitMenuController.disbandUnit(currentLocation, unitType);
        System.out.println("Units disbanded successfully");
    }

    private static void checkDigPitch(Matcher matcher, int[] currentLocation, String unitType) {
        message = SelectUnitMenuController.checkDigPitch(matcher, currentLocation, unitType);

        switch (message) {
            case SUCCESS -> System.out.println("Digging Pitch Started!");
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_DIRECTION -> System.out.println("Invalid Direction!");
            case INVALID_LENGTH -> System.out.println("Invalid Final Destination Based On Digging Length!");
            case INVALID_UNIT_TYPE_TO_DIG_PITCH -> System.out.println("This Units Cannot Dig Pitch!");
            case INVALID_AREA_FOR_DIGGING_PITCH -> System.out.println("Invalid Area For Digging Pitch!");
        }
    }
}
