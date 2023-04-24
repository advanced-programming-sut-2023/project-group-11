package view;


import controller.TradeMenuController;
import controller.Utils;
import view.enums.commands.SignupMenuCommands;
import view.enums.commands.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        System.out.print(TradeMenuController.showNotifications());
        String command = scanner.nextLine();
        while (true) {
            if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.BACK) != null) return;
            else if(TradeMenuCommands.getMatcher(command, TradeMenuCommands.END) != null) Utils.endStronghold();
            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Trade Menu");
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)) != null) {
                if (Utils.isValidCommandTags(matcher,"resourceTypeGroup","resourceAmountGroup",
                        "priceGroup","messageGroup")) checkTrade(matcher);
                else System.out.println("Invalid command!");
            }
            else if((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_ACCEPT)) != null){
                if (Utils.isValidCommandTags(matcher,"idGroup","messageGroup")) checkAcceptTrade(matcher);
                else System.out.println("Invalid command!");
            }
            else if(TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY) != null)
                tradeHistory();
            else if(TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST) != null)
                tradeList();
            else
                System.out.println("Invalid Command!");
        }

    }

    private static void checkTrade(Matcher matcher) {
        switch (TradeMenuController.checkTrade(matcher)){
            case INVALID_RESOURCE_TYPE -> System.out.println("Invalid Resource Type!");
            case SUCCESS -> System.out.println("Trade Successfully Added!");
        }
    }

    private static void tradeList() {
        System.out.print(TradeMenuController.tradeList());
    }

    private static void checkAcceptTrade(Matcher matcher) {

    }

    private static void tradeHistory() {
        System.out.print(TradeMenuController.tradeHistory());
    }
}
