package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SelectUnitMenuCommands {
    MOVE_UNIT("move unit to((?<xGroup> -x (?<xCoordinate>\\d+))|(?<yGroup> -y (?<yCoordinate>\\d+))){2}"),
    AIR_ATTACK("attack((?<xGroup> -x (?<xCoordinate>\\d+))|(?<yGroup> -y (?<yCoordinate>\\d+))){2}"),
    GROUND_ATTACK("attack -e (?<xCoordinate>\\d+) (?<yCoordinate>\\d+)"),
    BUILD_MACHINE("build -q (?<machineType>\"[^\"]+\"|\\S+)"),
    DESELECT("deselect|back");

    private final String regex;

    SelectUnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, SelectUnitMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
