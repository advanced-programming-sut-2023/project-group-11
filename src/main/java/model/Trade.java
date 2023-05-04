package model;

public class Trade {

    private final AllResource resourceType;
    private final int resourceAmount;
    private final double price;
    private final String senderMessage;
    private String receiverMessage;
    private boolean isOpen = true;
    private final Governance sender;
    private Governance receiver;


    public Trade(AllResource resourceType, int resourceAmount, double price, String senderMessage, Governance sender) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.senderMessage = senderMessage;
        this.sender = sender;
        Stronghold.getCurrentGame().getTrades().add(this);
        sender.addTrade(this);
        tradeNotify(this);
    }

    private void tradeNotify(Trade trade) {
        for (Governance governance : Stronghold.getCurrentGame().getGovernances()) {
            if (trade.sender.equals(governance))
                continue;
            governance.getTradeNotification().add(trade);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public AllResource getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public double getPrice() {
        return price;
    }

    public Governance getSender() {
        return sender;
    }

    public void accept(String message, Governance receiver) {
        isOpen = false;
        receiverMessage = message;
        double transfer = price * resourceAmount;
        sender.setGold(sender.getGold() + transfer);
        sender.removeFromStorage(resourceType, resourceAmount);
        receiver.setGold(receiver.getGold() - transfer);
        receiver.addToStorage(resourceType, resourceAmount);
        this.receiver = receiver;
        receiver.addTrade(this);
    }

    @Override
    public String toString() {
        String status = isOpen ? "Open" : "Closed";
        String receiverMessage = "";
        if (status.equals("Closed")) {
            receiverMessage = " ReceiverMessage: " + receiverMessage +
                    " Receiver: " + receiver.getOwner().getUsername();
        }
        return " ResourceType: " + resourceType.name() + " Amount: " + resourceAmount +
                " price: " + price + " SenderMessage: " + senderMessage + " Sender: " +
                sender.getOwner().getUsername() + receiverMessage + " Status: " + status + "\n";
    }


}
