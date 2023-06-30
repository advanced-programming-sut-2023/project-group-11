//define your general methods here

package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Governance;
import model.Stronghold;
import model.User;
import model.map.Map;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Utils {
    public static boolean isValidUsernameFormat(String username) {
        return username.matches("\"[\\w ]+\"|\\w+");
    }

    public static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,}$");
    }

    public static boolean isValidEmailFormat(String email) {
        return email.matches("[\\w.]+@[\\w.]+\\.[\\w.]+");
    }

    public static String encryptField(String field) {
        return new DigestUtils("SHA3-256").digestAsHex(field);
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

    public static void endStronghold() {
        updateDatabase("users");
        System.exit(0);
    }

    public static boolean isValidCoordinates(Map map, int x, int y) {
        int mapSize = map.getSize();
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
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

    public static String[] getCurrentUserFields(ArrayList parameters) {
        User user = Stronghold.getCurrentUser();
        return new String[]{user.getUsername(), user.getEmail(), user.getRecoveryQuestion(),
                user.getNickname(), user.getSlogan()};
    }

    public static String getCurrentUserAvatarFileName(ArrayList<Object> parameters) {
        return Stronghold.getCurrentUser().getAvatarFileName();
    }

    public static void sortUsers() {
        ArrayList<User> users = Stronghold.getUsers();
        Collections.sort(users);
        setRanks(users);
    }

    private static void setRanks(ArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) users.get(i).setRank(i + 1);
    }

    public static ArrayList<User> getUsersObservable(ArrayList<Object> parameters)   {
        ArrayList<User> users = Stronghold.getUsers();
        sortUsers();
        return users;
    }

    public static ObservableList<Governance> getGovernancesObservable(ArrayList<Object> parameters) {
        ArrayList<Governance> governances = Stronghold.getCurrentGame().getGovernances();
        return FXCollections.observableArrayList(governances);
    }
//
//    public static void columnMaker(ArrayList<Object> parameters) {
//        TableView tableView = (TableView) parameters.get(0);
//        String header = (String) parameters.get(1);
//        String userField = (String) parameters.get(2);
//
//        TableColumn<User, String> tableColumn = new TableColumn<>(header);
//        tableColumn.setCellValueFactory(new PropertyValueFactory<>(userField));
//        tableColumn.setSortable(false);
//        tableView.getColumns().add(tableColumn);
//    }

    public static ArrayList<Map> getMaps(ArrayList<Object> parameters) {
        return Stronghold.getMaps();
    }
}
