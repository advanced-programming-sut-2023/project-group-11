package model.people;

import model.people.enums.Speed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Machine extends Attacker {
    private final int engineersNeededToActivate;
    private final ArrayList<Engineer> engineers = new ArrayList<>();
    private boolean isActive = false;

    public Machine(String name) {
        JSONObject machine = getMachineFromDictionary(name);

        this.name = name;
        speed = Speed.valueOf((String) machine.get("speed"));
        leftMoves = speed.getMovesInEachTurn();
        cost = ((Long) machine.get("cost")).intValue();
        hp = ((Long) machine.get("hp")).intValue();
        engineersNeededToActivate = ((Long) machine.get("engineersNeededToActivate")).intValue();
        damage = ((Long) machine.get("damage")).intValue();
        range = ((Long) machine.get("range")).intValue();
    }

    public int getEngineersNeededToActivate() {
        return engineersNeededToActivate;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private JSONObject getMachineFromDictionary(String name) {
        JSONParser jsonParser = new JSONParser();
        JSONObject machine = new JSONObject();

        try (FileReader reader = new FileReader("src/main/resources/JSON/Machines.json")) {
            machine = (JSONObject) ((JSONObject) ((JSONArray) jsonParser.parse(reader)).get(0)).get(name);
        } catch (IOException | ParseException ignored) {
        }

        return machine;
    }
}
