package model.chat;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Message {
    private String content;
    private final int id;
    private final String senderName;
    private final String sentTime;
    private final ArrayList<Emoji> emojis = new ArrayList<>();
    private boolean isSelected;

    public Message(int id, String content, String senderName, String sentTime) {
        this.id = id;
        this.sentTime = sentTime;
        this.content = content;
        this.senderName = senderName;
    }

    public void addEmoji(Emoji emoji) {
        emojis.add(emoji);
    }

    public void removeEmoji(Emoji emoji) {
        emojis.remove(emoji);
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSentTime() {
        return sentTime;
    }

    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public VBox toVBox() {
        Label content = initLabel(getContent());
        Label sentTime = initLabel(getSentTime());
        Label senderName = initLabel(getSenderName());
        Label id = initLabel(String.valueOf(getId()));
        id.setVisible(false);
        HBox hBox = new HBox(senderName, sentTime, id);
        hBox.setSpacing(5);
        return new VBox(content, hBox);
    }

    private Label initLabel(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setTextFill(Color.WHITE);
        return label;
    }

    public enum Emoji {

    }
}

