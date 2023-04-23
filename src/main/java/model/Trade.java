package model;

import model.resources.AllResource;

import java.util.ArrayList;

public record Trade(AllResource resourceType, int resourceAmount, int price, String message, Governance sender) {

    private static ArrayList<Trade> trades = new ArrayList<>();
    private static ArrayList<Trade> newTrades = new ArrayList<>();

    public Trade(AllResource resourceType, int resourceAmount, int price, String message, Governance sender) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        this.sender = sender;
        trades.add(this);
        newTrades.add(this);
        sender.addTrade(this);
    }

    public static ArrayList<Trade> getTrades() {
        return trades;
    }

    public static ArrayList<Trade> getNewTrades() {
        return newTrades;
    }

    @Override
    public String toString() {
        return " ResourceType: " + resourceType.name() + " Amount: " + resourceAmount +
               " price: " + price + " message: " + message + " Sender: " + sender.getOwner().getUsername() + "\n";
    }
}
