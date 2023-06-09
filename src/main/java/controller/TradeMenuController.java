package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AllResource;
import model.Governance;
import model.Stronghold;
import model.Trade;
import view.enums.messages.TradeMenuMessages;

import java.util.ArrayList;
import java.util.Collections;

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

    public static TradeMenuMessages checkAcceptTrade(Trade trade) {
        Governance sender = trade.getSender();
        Governance receiver = trade.getReceiver();
        Governance seller = null, buyer=null;
        switch (trade.getTradeType()) {
            case "buy" -> {
                buyer = sender;
                seller = receiver;
            }
            case "sell" -> {
                buyer = receiver;
                seller = sender;
            }
        }
        if (buyer.getGold() < trade.getResourceAmount() * trade.getPrice())
            return TradeMenuMessages.NOT_ENOUGH_GOLD;
        if (!seller.hasEnoughItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_AMOUNT;
        if (!buyer.hasStorageForItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_STORAGE;
        trade.accept(buyer,seller);
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

    public static ObservableList<Trade> getSentTradesObservable() {
        return FXCollections.observableArrayList(Stronghold.getCurrentGame().getCurrentGovernance().getPreviousSentTrades());
    }

    public static ObservableList<Trade> getReceivedTradesObservable() {
        return FXCollections.observableArrayList(Stronghold.getCurrentGame().getCurrentGovernance().getPreviousReceivedTrades());
    }

    public static void seenNewTrades() {
        for (Trade trade : Stronghold.getCurrentGame().getCurrentGovernance().getPreviousReceivedTrades())
            trade.setSeen(true);
    }
}
