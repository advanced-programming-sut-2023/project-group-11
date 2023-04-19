package view;


import controller.EntryMenuController;

import java.util.Scanner;

public class EntryMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        fillAllFieldsWithPreviousData();
        if (EntryMenuController.isStayLoggedIn())
            MainMenu.run();
        else {
            //TODO: welcome, wherever you want to exit print end
            System.out.println("Enter 1 or 2 to choose the menu you want or \"end\" to quit");
            System.out.println("1. Signup menu");
            System.out.println("2. login menu");
            while (true) {
                String menuNumber = scanner.nextLine();
                if (menuNumber.matches("[1-2]"))
                    switch (Integer.parseInt(menuNumber)) {
                        case 1 -> SignupMenu.run();
                        case 2 -> LoginMenu.run();
                    }
                else if (menuNumber.equals("end")) break;
                else System.out.println("only 1 and 2 is acceptable!");
            }
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    private static void fillAllFieldsWithPreviousData() {

    }

}
