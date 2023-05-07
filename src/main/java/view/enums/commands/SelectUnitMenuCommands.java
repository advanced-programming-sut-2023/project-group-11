package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SelectUnitMenuCommands {
    MOVE_UNIT("move unit to(( -x (?<xCoordinate>\\d+))|( -y (?<yCoordinate>\\d+))){2}"),
    PATROL_UNIT("patrol unit(( -x1 (?<xCoordinate>-?\\d+))|( -y1 (?<yCoordinate>-?\\d+))){2}"),
    STOP_PATROL("stop patrol"),
    SET_UNIT_STATE("set(( -x (?<xCoordinate>\\d+))" +
            "|( -y (?<yCoordinate>\\d+))" +
            "|( -s (?<state>standing|defensive|offensive))){3}"),
    AIR_ATTACK("attack((?<xGroup> -x (?<xCoordinate>\\d+))|(?<yGroup> -y (?<yCoordinate>\\d+))){2}"),
    GROUND_ATTACK("attack -e (?<xCoordinate>\\d+) (?<yCoordinate>\\d+)"),
    DIG_TUNNEL("dig tunnel(( -x (?<xCoordinate>\\d+))|( -y (?<yCoordinate>\\d+))){2}"),
    BUILD_MACHINE("build -q (?<machineType>\"[^\"]+\"|\\S+)"),
    DISBAND("disband unit"),
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
