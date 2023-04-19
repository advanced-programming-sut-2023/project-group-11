package Controller;

import Model.Stronghold;

public class EntryMenuController {
    public static boolean isStayLoggedIn() {
        return Stronghold.isStayLoggedIn();
    }
}
