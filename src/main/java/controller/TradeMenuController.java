package controller;

import model.Governance;
import model.Trade;
import view.enums.messages.TradeMenuMessages;
import model.resources.*;

import java.util.regex.Matcher;

public class TradeMenuController {

    private static Governance currentGovernance;

    public static TradeMenuMessages checkTrade(Matcher matcher) {
        String resourceType = matcher.group("resourceType"),
                message = matcher.group("message");
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount")),
                price = Integer.parseInt(matcher.group("price"));
        AllResource resource = (AllResource) AllResource.getAllResourceByName(resourceType);
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
            output += "" + (index++) + "-" + trade;
        }
        return output;
    }

    public static TradeMenuMessages checkAcceptTrade(Matcher matcher) {
        return null;
    }

    public static String tradeHistory() {
        return currentGovernance.tradeHistory();
    }
}
