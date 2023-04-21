package view;


import controller.EntryMenuController;

import java.util.Scanner;

public class EntryMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        EntryMenuController.fillAllFieldsWithPreviousData();
        if (EntryMenuController.getStayLoggedIn() != null)
            MainMenu.run();
        else {
            System.out.println("Welcome to Stronghold Crusader game!");
            System.out.println("Wherever you want to exit print \"end\"!");
            while (true) {
                System.out.println("Enter 1 or 2 to choose the menu you want");
                System.out.println("1. Signup menu");
                System.out.println("2. login menu");
                String menuNumber = scanner.nextLine();
                switch (menuNumber) {
                    case "end" -> System.exit(0);
                    case "1" -> SignupMenu.run();
                    case "2" -> LoginMenu.run();
                    default -> System.out.println("Invalid command: only 1 and 2 is acceptable!");
                }
            }
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
