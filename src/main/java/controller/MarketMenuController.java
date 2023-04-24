package controller;

import model.Governance;
import model.Stronghold;
import model.resources.AllResource;
import view.enums.messages.MarketMenuMessages;

import java.util.regex.Matcher;

public class MarketMenuController {

    private static Governance currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
    public static String showPriceList() {
        String output = "";
        int index = 1;
        for(AllResource item:AllResource.values()){
            output += (index++) + "- itemName:" + item.getName() +
                      " Buy Price: " + item.getPrice() + " Sell Price: " + (item.getPrice()/2) +
                      " Your Storage Amount: " + currentGovernance.getResourceCount(item) + "\n";
        }
        return output;
    }

    private MarketMenuMessages checkBuyItem(Matcher matcher) {
        return null;
    }

    private MarketMenuMessages checkSellItem(Matcher matcher) {
        return null;
    }
}
