package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Stronghold;
import model.User;
import model.map.Map;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EntryMenuController {
    public static void fillAllFieldsWithPreviousData() {
        parseUsers();
        parseMaps();
    }

    private static void parseUsers() {
        try (FileReader reader = new FileReader("src/main/resources/users.json")) {
            Type userDatabaseType = new TypeToken<ArrayList<User>>() {
            }.getType();
            Stronghold.getUsers().addAll(new Gson().fromJson(reader, userDatabaseType));
        } catch (IOException ignored) {
        }
    }

    private static void parseMaps() {
        try (FileReader reader = new FileReader("src/main/resources/maps.json")) {
            Type mapDatabaseType = new TypeToken<ArrayList<Map>>() {
            }.getType();
            Stronghold.getMaps().addAll(new Gson().fromJson(reader, mapDatabaseType));
        } catch (IOException ignored) {
        }
    }

    public static User getStayLoggedIn() {
        for (User user : Stronghold.getUsers())
            if (user.isStayLoggedIn()) {
                Stronghold.setCurrentUser(user);
                return user;
            }
        return null;
    }
}
