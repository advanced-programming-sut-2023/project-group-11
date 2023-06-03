package view;

import controller.EntryMenuController;
import view.SignupMenu;

public class Main {
    public static void main(String[] args) throws Exception {
        EntryMenuController.fillAllFieldsWithPreviousData();
        SignupMenu.main(args);
    }
}