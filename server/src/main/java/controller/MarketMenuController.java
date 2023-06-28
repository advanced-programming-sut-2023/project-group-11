package controller;

import model.AllResource;
import model.Governance;
import model.Stronghold;
import view.enums.messages.MarketMenuMessages;

public class MarketMenuController {

    private static Governance currentGovernance;

    public static String showPriceList() {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        String output = "";
        int index = 1;
        for (AllResource item : AllResource.values()) {
            if (!item.equals(AllResource.NONE))
                output += (index++) + item.toString() +
                        " Your Storage Amount: " + currentGovernance.getAllResources().get(item) + "\n";
        }
        return output;
    }

    public static MarketMenuMessages checkBuyItem(AllResource item, String amountText) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            return MarketMenuMessages.INVALID_AMOUNT;
        }
        if (currentGovernance.getGold() < item.getPrice() * amount)
            return MarketMenuMessages.NOT_ENOUGH_GOLD;
        if (!currentGovernance.hasStorageForItem(item, amount))
            return MarketMenuMessages.NOT_ENOUGH_STORAGE;
        currentGovernance.setGold(currentGovernance.getGold() - item.getPrice() * amount);
        currentGovernance.addToStorage(item, amount);
        return MarketMenuMessages.SUCCESS;
    }

    public static AllResource getResourceByName(String name) {
        return AllResource.getAllResourceByName(name);
    }

    public static MarketMenuMessages checkSellItem(AllResource item, String amountText) {
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            return MarketMenuMessages.INVALID_AMOUNT;
        }
        if (!currentGovernance.hasEnoughItem(item, amount))
            return MarketMenuMessages.NOT_ENOUGH_STORAGE;
        currentGovernance.setGold(currentGovernance.getGold() + (item.getPrice() * amount) / 2.0);
        currentGovernance.removeFromStorage(item, amount);
        return MarketMenuMessages.SUCCESS;
    }
}
