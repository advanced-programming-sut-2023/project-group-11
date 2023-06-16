package controller;

import model.AllResource;
import model.Governance;
import model.Stronghold;
import model.Trade;
import view.enums.messages.TradeMenuMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

public class TradeMenuController {

    private static Governance currentGovernance;

    public static TradeMenuMessages checkTrade(AllResource resource, int amount, int price, String message, String tradeType, Governance reciever) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        new Trade(resource, amount, price, message, tradeType, currentGovernance, reciever);
        return TradeMenuMessages.SUCCESS;
    }

    public static String tradeList() {
        String output = "";
        int index = 1;
        for (Trade trade : Stronghold.getCurrentGame().getTrades()) {
            output += (index++) + "-" + trade + "\n";
        }
        return output;
    }

    public static TradeMenuMessages checkAcceptTrade(Matcher matcher) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        if (!Utils.isValidCommandTags(matcher, "idGroup", "messageGroup"))
            return TradeMenuMessages.INVALID_COMMAND;
        int id = Integer.parseInt(matcher.group("id"));
        String message = Utils.removeDoubleQuotation(matcher.group("message"));
        ArrayList<Trade> trades = Stronghold.getCurrentGame().getTrades();
        if (trades.size() < id)
            return TradeMenuMessages.INVALID_ID;
        Trade trade = trades.get(id - 1);
        if (!trade.isOpen())
            return TradeMenuMessages.TRADE_CLOSED;
        if (currentGovernance.equals(trade.getSender()))
            return TradeMenuMessages.CANT_ACCEPT_YOUR_OWN_TRADE;
        if (currentGovernance.getGold() < trade.getResourceAmount() * trade.getPrice())
            return TradeMenuMessages.NOT_ENOUGH_GOLD;
        if (!trade.getSender().hasEnoughItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_AMOUNT;
        if (!currentGovernance.hasStorageForItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_STORAGE;
        trade.accept(message, currentGovernance);
        return TradeMenuMessages.SUCCESS;
    }

    public static String tradeHistory() {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        return currentGovernance.tradeHistory();
    }

    public static String showNotifications() {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";
        int index = 1;
        for (Trade trade : currentGovernance.getTradeNotification()) {
            if (index == 1)
                output += "Notifications: \n";
            output += (index++) + "-" + trade + "\n";
        }
        currentGovernance.getTradeNotification().clear();
        return output;
    }

    public static String showAllGovernance() {
        String output = "";
        int index = 1;
        for (Governance governance : Stronghold.getCurrentGame().getGovernances())
            output += (index++) + "- " + governance.getOwner().getNickname() + "'s Governance\n";
        return output;
    }

    public static ArrayList<AllResource> getAllResources() {
        ArrayList<AllResource> allResources = new ArrayList<>();
        Collections.addAll(allResources, AllResource.values());
        return allResources;
    }

}
