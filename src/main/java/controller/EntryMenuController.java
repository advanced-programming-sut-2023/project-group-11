package controller;

import model.Stronghold;

public class EntryMenuController {
    public static boolean isStayLoggedIn() {
        return Stronghold.isStayLoggedIn();
    }
}
