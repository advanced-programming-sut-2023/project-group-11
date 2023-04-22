package view;


import controller.EntryMenuController;

import java.util.Scanner;

public class EntryMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        System.out.println("Welcome to Stronghold Crusader game!");
        System.out.println("Wherever you want to exit print \"end\"!");
        EntryMenuController.fillAllFieldsWithPreviousData();
        if (EntryMenuController.getStayLoggedIn() != null) {
            System.out.println("Entered Main Menu!");
            MainMenu.run();
        }
        while (true) {
            System.out.println("Enter 1 or 2 to choose the menu you want");
            System.out.println("1. Signup menu");
            System.out.println("2. Login menu");
            String menuNumber = scanner.nextLine();
            switch (menuNumber) {
                case "end" -> System.exit(0);
                case "1" -> {
                    System.out.println("Entered Signup Menu!");
                    SignupMenu.run();
                }
                case "2" -> {
                    System.out.println("Entered Login Menu!");
                    LoginMenu.run();
                }
                default -> System.out.println("Invalid command: only 1 and 2 is acceptable!");
            }
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
