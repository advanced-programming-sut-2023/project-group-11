package view;

import controller.MarketMenuController;
import controller.Utils;
import view.enums.commands.MarketMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MarketMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command = scanner.nextLine();
        while (true) {
            if (MarketMenuCommands.getMatcher(command, MarketMenuCommands.BACK) != null) return;
            else if (MarketMenuCommands.getMatcher(command, MarketMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Market Menu");
            else if (MarketMenuCommands.getMatcher(command, MarketMenuCommands.SHOW_LIST) != null) showPriceList();
            else if ((matcher = MarketMenuCommands.getMatcher(command, MarketMenuCommands.BUY)) != null) {
                if (Utils.isValidCommandTags(matcher, "itemName", "amount")) checkBuyItem(matcher);
                else System.out.println("Invalid Command!");
            } else if ((matcher = MarketMenuCommands.getMatcher(command, MarketMenuCommands.SELL)) != null) {
                if (Utils.isValidCommandTags(matcher, "itemName", "amount")) checkSellItem(matcher);
                else System.out.println("Invalid Command!");
            } else System.out.println("Invalid Command!");
            command = scanner.nextLine();
        }

    }

    private static void showPriceList() {
        System.out.print(MarketMenuController.showPriceList());
    }

    private static void checkBuyItem(Matcher matcher) {
        switch (MarketMenuController.checkBuyItem(matcher)) {
            case INVALID_ITEM -> System.out.println("Invalid Item!");
            case NOT_ENOUGH_GOLD -> System.out.println("Your Gold Is Not Enough!");
            case NOT_ENOUGH_STORAGE -> System.out.println("Your Storage Is Not Enough For Storing This Item!");
            case CANCEL -> System.out.println("Deal Canceled!");
            case SUCCESS -> System.out.println("Item Bought Successfully!");
        }
    }

    private static void checkSellItem(Matcher matcher) {
        switch (MarketMenuController.checkSellItem(matcher)) {
            case INVALID_ITEM -> System.out.println("Invalid Item!");
            case NOT_ENOUGH_STORAGE -> System.out.println("You Dont Have Enough Of This Item!");
            case CANCEL -> System.out.println("Deal Canceled!");
            case SUCCESS -> System.out.println("Item Sold Successfully!");
        }
    }

    public static boolean isSure() {
        System.out.println("Are You Sure About This Deal?[Y/N]");
        String answer;
        while (true) {
            answer = EntryMenu.getScanner().nextLine();
            if (answer.equals("Y"))
                return true;
            if (answer.equals("N"))
                return false;
            System.out.println("Invalid Answer!");
            System.out.println("Are You Sure About This Deal?[Y/N]");
        }
    }
}
