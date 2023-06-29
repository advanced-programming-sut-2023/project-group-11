package controller;

import model.AllResource;
import model.Governance;
import model.Stronghold;

import java.util.ArrayList;

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

    public static Message checkBuyItem(ArrayList parameters) {
        AllResource item = (AllResource) parameters.get(0);
        String amountText = (String) parameters.get(1);
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            return Message.INVALID_AMOUNT;
        }
        if (currentGovernance.getGold() < item.getPrice() * amount)
            return Message.NOT_ENOUGH_GOLD;
        if (!currentGovernance.hasStorageForItem(item, amount))
            return Message.NOT_ENOUGH_STORAGE;
        currentGovernance.setGold(currentGovernance.getGold() - item.getPrice() * amount);
        currentGovernance.addToStorage(item, amount);
        return Message.SUCCESS;
    }

    public static AllResource getResourceByName(ArrayList parameters) {
        String name = (String) parameters.get(0);
        return AllResource.getAllResourceByName(name);
    }

    public static Message checkSellItem(ArrayList parameters) {
        AllResource item = (AllResource) parameters.get(0);
        String amountText = (String) parameters.get(1);
        currentGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
        int amount;
        try {
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            return Message.INVALID_AMOUNT;
        }
        if (!currentGovernance.hasEnoughItem(item, amount))
            return Message.NOT_ENOUGH_STORAGE;
        currentGovernance.setGold(currentGovernance.getGold() + (item.getPrice() * amount) / 2.0);
        currentGovernance.removeFromStorage(item, amount);
        return Message.SUCCESS;
    }
}
