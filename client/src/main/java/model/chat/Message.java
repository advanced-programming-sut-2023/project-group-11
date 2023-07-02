package model.chat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Message {
    private final String content;
    private final int id;
    private final String senderName;
    private final String sentTime;
    private final String avatarAddress;
    private final ArrayList<Emoji> emojis = new ArrayList<>();
    private final boolean seen;
    private final boolean isOwnerCurrentUser;

    public Message(int id, String content, String senderName, String sentTime, String avatarAddress, boolean seen, boolean isOwnerCurrentUser) {
        this.id = id;
        this.sentTime = sentTime;
        this.content = content;
        this.senderName = senderName;
        this.avatarAddress = avatarAddress;
        this.seen = seen;
        this.isOwnerCurrentUser = isOwnerCurrentUser;
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
        ImageView check = null;
        Label content = initLabel(getContent());
        Label sentTime = initLabel(getSentTime());
        Label senderName = initLabel(getSenderName());
        Label id = initLabel(String.valueOf(getId()));
        id.setVisible(false);
        HBox hBox = new HBox(5);
        HBox hBox1 = new HBox(5, senderName, sentTime, id);
        VBox vBox = new VBox(hBox, hBox1);
        if (isOwnerCurrentUser) {
            hBox.getChildren().addAll(content, avatar);
            setAlignment(hBox, hBox1, Pos.CENTER_RIGHT);
            if (seen) check = getImageView(getClass().getResource("/IMG/chat/seen.png").toExternalForm(), 20, 20);
            else check = getImageView(getClass().getResource("/IMG/chat/unseen.png").toExternalForm(), 10, 10);
        } else {
            hBox.getChildren().addAll(avatar, content);
            setAlignment(hBox, hBox1, Pos.CENTER_LEFT);
        }
        vBox.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        if (check != null) hBox1.getChildren().add(check);
        return vBox;
    }

    private void setAlignment(HBox hBox, HBox hBox1, Pos pos) {
        hBox.setAlignment(pos);
        hBox1.setAlignment(pos);
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

