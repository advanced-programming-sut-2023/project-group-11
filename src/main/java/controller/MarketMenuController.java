package controller;

import model.Governance;
import model.Stronghold;
import model.AllResource;
import view.MarketMenu;
import view.enums.messages.MarketMenuMessages;

import java.util.regex.Matcher;

public class MarketMenuController {

    private static Governance currentGovernance;

    public static String showPriceList() {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";
        int index = 1;
        for (AllResource item : AllResource.values()) {
            output += (index++) + "- itemName:" + item.getName() +
                    " Buy Price: " + item.getPrice() + " Sell Price: " + (item.getPrice() / 2) +
                    " Your Storage Amount: " + currentGovernance.getResourceCount(item) + "\n";
        }
        return output;
    }

    public static MarketMenuMessages checkBuyItem(Matcher matcher) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        AllResource item = AllResource.getAllResourceByName(matcher.group("itemName"));
        int amount = Integer.parseInt(matcher.group("amount"));
        if (item == null)
            return MarketMenuMessages.INVALID_ITEM;
        if (currentGovernance.getGold() < item.getPrice() * amount)
            return MarketMenuMessages.NOT_ENOUGH_GOLD;
        if (!currentGovernance.hasStorageForItem(item, amount))
            return MarketMenuMessages.NOT_ENOUGH_STORAGE;
        if (MarketMenu.isSure()) {
            currentGovernance.setGold(currentGovernance.getGold() - item.getPrice() * amount);
            currentGovernance.addToStorage(item, amount);
            return MarketMenuMessages.SUCCESS;
        }
        return MarketMenuMessages.CANCEL;
    }

    public static MarketMenuMessages checkSellItem(Matcher matcher) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        AllResource item = AllResource.getAllResourceByName(matcher.group("itemName"));
        int amount = Integer.parseInt(matcher.group("amount"));
        if (item == null)
            return MarketMenuMessages.INVALID_ITEM;
        if (!currentGovernance.hasEnoughItem(item, amount))
            return MarketMenuMessages.NOT_ENOUGH_STORAGE;
        if (MarketMenu.isSure()) {
            currentGovernance.setGold(currentGovernance.getGold() + (item.getPrice() * amount) / 2.0);
            currentGovernance.removeFromStorage(item, amount);
            return MarketMenuMessages.SUCCESS;
        }
        return MarketMenuMessages.CANCEL;
    }
}
