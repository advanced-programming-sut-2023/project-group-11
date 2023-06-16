package model;

public class Trade {

    private final AllResource resourceType;
    private final int resourceAmount;
    private final int price;
    private final String senderMessage;
    private String receiverMessage;
    private boolean isOpen = true;
    private boolean isSeen = false;
    private String tradeType;
    private final Governance sender;
    private Governance receiver;


    public Trade(AllResource resourceType, int resourceAmount, int price, String senderMessage,String tradeType, Governance sender,Governance receiver) {
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.senderMessage = senderMessage;
        this.tradeType = tradeType;
        this.sender = sender;
        this.receiver = receiver;
        Stronghold.getCurrentGame().getTrades().add(this);
        sender.addToSentTrades(this);
        receiver.addToReceivedTrades(this);
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

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
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

    public int getPrice() {
        return price;
    }

    public Governance getSender() {
        return sender;
    }

    public String getSenderName() {
        return sender.getOwner().getNickname();
    }

    public String getReceiverName() {
        return receiver.getOwner().getNickname();
    }

    public String getResourceName() {
        return resourceType.getName();
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getStatus(){
        return isOpen ? "Open" : "Closed";
    }

    public String getSeenStatus(){
        return isSeen ? "YES" : "NO";
    }

    public void accept(Governance buyer,Governance seller) {
        isOpen = false;
        int transfer = price * resourceAmount;
        buyer.setGold(buyer.getGold() - transfer);
        seller.setGold(seller.getGold() + transfer);
        seller.removeFromStorage(resourceType, resourceAmount);
        buyer.addToStorage(resourceType, resourceAmount);
    }

    @Override
    public String toString() {
        String status = isOpen ? "Open" : "Closed";
        String receiverMessage = "";
        if (status.equals("Closed")) {
            receiverMessage = " ReceiverMessage: " + receiverMessage +
                    " Receiver: " + receiver.getOwner().getNickname();
        }
        return " ResourceType: " + resourceType.name() + " Amount: " + resourceAmount +
                " price: " + price + " SenderMessage: " + senderMessage + " Sender: " +
                sender.getOwner().getNickname() + receiverMessage + " Status: " + status;
    }


    public Governance getReceiver() {
        return receiver;
    }
}
