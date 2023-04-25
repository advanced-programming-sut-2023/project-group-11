package controller;

import model.Game;
import model.Governance;
import model.Stronghold;
import model.Trade;
import view.enums.messages.TradeMenuMessages;
import model.resources.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class TradeMenuController {

    private static Governance currentGovernance;

    public static TradeMenuMessages checkTrade(Matcher matcher) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String resourceType = matcher.group("resourceType"),
                message = matcher.group("message");
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount")),
                price = Integer.parseInt(matcher.group("price"));
        AllResource resource =  AllResource.getAllResourceByName(resourceType);
        if (resource == null)
            return TradeMenuMessages.INVALID_RESOURCE_TYPE;
        new Trade(resource, resourceAmount, price, message, currentGovernance);
        return TradeMenuMessages.SUCCESS;
    }

    public static void setCurrentGovernance(Governance governance) {
        currentGovernance = governance;
    }

    public static String tradeList() {
        String output = "";
        int index = 1;
        for (Trade trade:Trade.getTrades()){
            output += (index++) + "-" + trade;
        }
        return output;
    }

    public static TradeMenuMessages checkAcceptTrade(Matcher matcher) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int id = Integer.parseInt(matcher.group("id"));
        String message = matcher.group("message");
        ArrayList<Trade> trades = Trade.getTrades();
        if(trades.size() < id)
            return TradeMenuMessages.INVALID_ID;
        Trade trade = trades.get(id);
        if(!trade.isOpen())
            return TradeMenuMessages.TRADE_CLOSED;
        if(currentGovernance.equals(trade.getSender()))
            return TradeMenuMessages.CANT_ACCEPT_YOUR_OWN_TRADE;
        if(currentGovernance.getGold() < trade.getResourceAmount() * trade.getPrice())
            return TradeMenuMessages.NOT_ENOUGH_GOLD;
        if(!trade.getSender().hasEnoughItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_AMOUNT;
        if(!currentGovernance.hasStorageForItem(trade.getResourceType(), trade.getResourceAmount()))
            return TradeMenuMessages.NOT_ENOUGH_STORAGE;
        trade.accept(message,currentGovernance);
        return TradeMenuMessages.SUCCESS;
    }

    public static String tradeHistory() {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        return currentGovernance.tradeHistory();
    }

    public static String showNotifications(){
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";
        int index = 1;
        for(Trade trade: currentGovernance.getTradeNotification()){
            output += (index++) + "-" + trade;
        }
        currentGovernance.getTradeNotification().clear();
        return output;
    }

}
