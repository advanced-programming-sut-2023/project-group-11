import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.people.Machine;
import model.people.enums.MachineType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.EntryMenu;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        EntryMenu.run();
        JSONArray machineArray = new JSONArray();
        JSONObject machineJSON = new JSONObject();
        for (MachineType value : MachineType.values()) {
            machineJSON.put(value.getName(), new Machine(value));
            machineArray.add(machineJSON);
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("src/main/resources/Machines.json")){
            gson.toJson(machineArray, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}