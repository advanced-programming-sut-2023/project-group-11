package controller;

import model.Game;
import model.Governance;
import model.Stronghold;
import view.enums.messages.MainMenuMessages;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {
    public static MainMenuMessages checkStartGame(Matcher matcher) {
        if (matcher.group("mapName") == null || matcher.group("guests") == null)
            return MainMenuMessages.INVALID_COMMAND;

        String mapName = matcher.group("mapName");
        String[] listOfPlayers = makeListOfPlayers(matcher.group("guests")).split("-");

        if (!Stronghold.isMapName(mapName))
            return MainMenuMessages.MAP_NOT_EXIST;

        for (String name : listOfPlayers)
            if (!Stronghold.usernameExist(name)) return MainMenuMessages.USER_NOT_EXIST;

        Stronghold.setCurrentGame(new Game(makeGovernances(listOfPlayers), Stronghold.getMapByName(mapName)));
        return MainMenuMessages.SUCCESS;
    }

    private static ArrayList<Governance> makeGovernances(String[] listOfPlayers) {
        ArrayList<Governance> governances = new ArrayList<>();
        governances.add(new Governance(Stronghold.getCurrentUser()));

        for (String name : listOfPlayers)
            governances.add(new Governance(Stronghold.getUserByUsername(name)));

        return governances;
    }

    public static String makeListOfPlayers(String guests) {
        String result = "";
        String[] list = guests.split(" ");
        for (String name : list) {
            result += Utils.removeDoubleQuotation(name) + "-";
        }
        return result.substring(0, result.length()-1);
    }
}
