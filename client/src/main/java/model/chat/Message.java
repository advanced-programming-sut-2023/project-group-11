package model.chat;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Message {
    private String content;
    private final int id;
    private final String senderName;
    private final String sentTime;
    private final String avatarAddress;
    private final ArrayList<Emoji> emojis = new ArrayList<>();
    private final boolean seen;

    public Message(int id, String content, String senderName, String sentTime, String avatarAddress, boolean seen) {
        this.id = id;
        this.sentTime = sentTime;
        this.content = content;
        this.senderName = senderName;
        this.avatarAddress = avatarAddress;
        this.seen = seen;
    }

    public void addEmoji(Emoji emoji) {
        emojis.add(emoji);
    }

    public void removeEmoji(Emoji emoji) {
        emojis.remove(emoji);
    }

    private int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    private String getSenderName() {
        return senderName;
    }

    private String getSentTime() {
        return sentTime;
    }


    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    public VBox toVBox() {
        ImageView avatar = getImageView(avatarAddress, 15, 15);
        ImageView check;
        if (seen) check = getImageView(getClass().getResource("/IMG/chat/seen.png").toExternalForm(), 20, 20);
        else check = getImageView(getClass().getResource("/IMG/chat/unseen.png").toExternalForm(), 10, 10);
        Label content = initLabel(getContent());
        Label sentTime = initLabel(getSentTime());
        Label senderName = initLabel(getSenderName());
        Label id = initLabel(String.valueOf(getId()));
        id.setVisible(false);
        HBox hBox = new HBox(5, avatar, content);
        HBox hBox1 = new HBox(5, senderName, sentTime, id, check);
        return new VBox(hBox, hBox1);
    }

    private ImageView getImageView(String avatarAddress, int width, int height) {
        ImageView avatar = new ImageView(new Image(avatarAddress));
        avatar.setFitWidth(width);
        avatar.setFitHeight(height);
        return avatar;
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

