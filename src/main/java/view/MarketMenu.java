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
        while (true){
            if(MarketMenuCommands.getMatcher(command,MarketMenuCommands.END) != null) Utils.endStronghold();
            else if(MarketMenuCommands.getMatcher(command,MarketMenuCommands.BACK) != null) return;
            else if(MarketMenuCommands.getMatcher(command,MarketMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Market Menu");
            else if(MarketMenuCommands.getMatcher(command,MarketMenuCommands.SHOW_LIST) != null) showPriceList();
            else if((matcher = MarketMenuCommands.getMatcher(command,MarketMenuCommands.BUY)) != null){
                if(Utils.isValidCommandTags(matcher,"itemName","amount")) checkBuyItem(matcher);
                else System.out.println("Invalid Command!");
            }else if((matcher = MarketMenuCommands.getMatcher(command,MarketMenuCommands.SELL)) != null){
                if(Utils.isValidCommandTags(matcher,"itemName","amount")) checkSellItem(matcher);
                else System.out.println("Invalid Command!");
            }else System.out.println("Invalid Command!");
        }

    }

    private static void showPriceList() {
        System.out.print(MarketMenuController.showPriceList());
    }

    private static void checkBuyItem(Matcher matcher) {

    }

    private static void checkSellItem(Matcher matcher) {

    }
}
