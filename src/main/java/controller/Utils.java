//define your general methods here

package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import model.AllResource;
import model.Stronghold;
import model.User;
import model.map.Map;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;

public class Utils {
    public static boolean checkEmptyField(String username, String password, String email, String nickname, String slogan, boolean hasSlogan) {
        return (username.isEmpty() ||
                password.isEmpty() ||
                email.isEmpty() ||
                nickname.isEmpty()) ||
                (hasSlogan && slogan.isEmpty());
    }

    public static boolean isValidUsernameFormat(String username) {
        return username.matches("\"[\\w ]+\"|\\w+");
    }

    public static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,}$");
    }

    public static boolean correctPasswordConfirmation(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    public static boolean isValidEmailFormat(String email) {
        return email.matches("[\\w.]+@[\\w.]+\\.[\\w.]+");
    }

    public static String encryptField(String field) {
        return new DigestUtils("SHA3-256").digestAsHex(field);
    }

    public static boolean isValidCommandTags(Matcher matcher, String... options) {
        for (String option : options)
            if (matcher.group(option) == null) return false;
        return true;
    }

    public static WritableImage generateCaptcha(int captchaNumber) {
        Random random = new Random();
        int width = 150;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics2D = image.getGraphics();
        graphics2D.setFont(new Font("SansSerif", Font.BOLD, 22));
        Graphics2D graphics = (Graphics2D) graphics2D;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        StringBuilder captcha = new StringBuilder();
        graphics.drawString(Integer.toString(captchaNumber), 15, 25);

        for (int y = 0; y < height; y++) {
            StringBuilder captchaLine = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int randomNumber = random.nextInt(8);
                if (image.getRGB(x, y) == -16777216 || (x + y) % 8 == randomNumber) captchaLine.append(" ");
                else captchaLine.append("*");
            }
            if (captchaLine.toString().trim().isEmpty()) continue;
            captcha.append(captchaLine).append("\n");
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    public static boolean checkCaptchaConfirmation(int enteredCaptcha, int captchaNumber) {
        return captchaNumber == enteredCaptcha;
    }

    public static void updateDatabase(String field) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("src/main/resources/JSON/" + field + ".json")) {
            gson.toJson(getDatabaseList(field), writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Object> getDatabaseList(String field) {
        ArrayList<Object> databaseList = new ArrayList<>();
        switch (field) {
            case "users" -> databaseList.addAll(Stronghold.getUsers());
            case "maps" -> databaseList.addAll(Stronghold.getMaps());
        }
        return databaseList;
    }

    public static String removeDoubleQuotation(String name) {
        if (name != null && name.charAt(0) == '\"') {
            StringBuilder temp = new StringBuilder(name);
            temp.deleteCharAt(name.length() - 1);
            temp.deleteCharAt(0);
            return temp.toString();
        }
        return name;
    }

    public static void endStronghold() {
        updateDatabase("users");
        System.exit(0);
    }

    public static boolean isValidCoordinates(Map map, int x, int y) {
        int mapSize = map.getSize();
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
    }

    public static boolean isFood(AllResource resource) {
        return resource.equals(AllResource.BREAD)
                || resource.equals(AllResource.MEAT)
                || resource.equals(AllResource.CHEESE)
                || resource.equals(AllResource.APPLE);
    }

    static boolean isValidUnitType(String type) {
        return type.equals("archer") || type.equals("crossbowman") || type.equals("spearman") ||
                type.equals("pikeman") || type.equals("maceman") || type.equals("swordsman") ||
                type.equals("knight") || type.equals("tunneler") || type.equals("ladderman") ||
                type.equals("black monk") || type.equals("archer bow") || type.equals("slaves") ||
                type.equals("slinger") || type.equals("assassin") || type.equals("horse archer") ||
                type.equals("arabian swordsman") || type.equals("fire thrower") ||
                type.equals("engineer") || type.equals("lord");
    }

    static boolean isValidMachineType(String type) {
        return type.equals("siege tower") || type.equals("portable shield") ||
                type.equals("battering ram") || type.equals("trebuchets") ||
                type.equals("fire ballista") || type.equals("catapults");
    }

    public static String[] getCurrentUserFields() {
        User user = Stronghold.getCurrentUser();
        return new String[]{user.getUsername(), user.getEmail(), user.getRecoveryQuestion(),
                user.getNickname(), user.getSlogan()};
    }

    public static ImageView getCurrentUserAvatar() {
        User user = Stronghold.getCurrentUser();
        return user.getAvatar();
    }

    public static void sortUsers() {
        ArrayList<User> users = Stronghold.getUsers();
        Collections.sort(users);
        setRanks(users);
    }

    private static void setRanks(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) users.get(i).setRank(i + 1);
    }

    public static ObservableList<User> getUsersObservable() {
        ArrayList<User> users = Stronghold.getUsers();
        return FXCollections.observableArrayList(users);
    }

    public static void columnMaker(TableView tableView, String header, String userField) {
        TableColumn<User, String> tableColumn = new TableColumn<>(header);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(userField));
        tableColumn.setSortable(false);
        tableView.getColumns().add(tableColumn);
    }
}
