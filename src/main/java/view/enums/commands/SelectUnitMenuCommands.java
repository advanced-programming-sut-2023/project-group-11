package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SelectUnitMenuCommands {
    MOVE_UNIT("move\\s*unit to(( -x (?<xCoordinate>\\d+))|( -y (?<yCoordinate>\\d+))){2}"),
    PATROL_UNIT("patrol\\s*unit(( -x (?<xCoordinate>-?\\d+))|( -y (?<yCoordinate>-?\\d+))){2}"),
    STOP_PATROL("stop\\s*patrol"),
    SET_UNIT_STATE("set -s (?<state>\\S+)"),
    AIR_ATTACK("attack((?<xGroup> -x (?<xCoordinate>\\d+))|(?<yGroup> -y (?<yCoordinate>\\d+))){2}"),
    GROUND_ATTACK("attack -e (?<xCoordinate>\\d+) (?<yCoordinate>\\d+)"),
    DIG_TUNNEL("dig\\s*tunnel -d (?<direction>(\\S+))"),
    BUILD_MACHINE("build -q (?<machineType>\"[^\"]+\"|\\S+)"),
    DISBAND("disband\\s*unit"),
    DESELECT("deselect|back"),
    POUR_OIL("pour\\s*oil -d (?<direction>(up)|(down)|(left)|(right))"),
    DIG_PITCH("dig\\s*pitch( -d (?<direction>\\S+)| -l (?<length>\\d+)){2}"),
    FILL_PITCH("fill\\s*pitch( -x1 (?<x1>\\d+)| -y1 (?<y1>\\d+)| -x2 (?<x2>\\d+)| -y2 (?<y2>\\d+)){4}"),
    ;

    private final String regex;

    SelectUnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, SelectUnitMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
