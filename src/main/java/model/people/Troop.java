package model.people;

import model.AllResource;
import model.people.enums.Hp;
import model.people.enums.Speed;
import model.people.enums.TroopDamage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Troop extends Attacker {
    private final AllResource weaponType;
    private final AllResource armorType;
    private final boolean hasHorse;
    private final boolean isArab;
    private final boolean hasFiringWeapon;
    private final boolean canScaleWall;
    private final boolean canDigKhandagh;
    private boolean revealed;

    public Troop(String name) {
        JSONObject troop = getTroopFromDictionary(name);

        this.name = name;
        this.hp = Hp.valueOf((String) troop.get("hp")).getHp();
        this.speed = Speed.valueOf((String) troop.get("speed"));
        this.leftMoves = speed.getMovesInEachTurn();
        this.damage = TroopDamage.valueOf((String) troop.get("damage")).getDamage();
        this.range = ((Long) troop.get("range")).intValue();
        this.weaponType = AllResource.valueOf((String) troop.get("weaponType"));
        this.armorType = AllResource.valueOf((String) troop.get("armorType"));
        this.hasHorse = (Boolean) troop.get("hasHorse");
        this.isArab = (Boolean) troop.get("isArab");
        this.hasFiringWeapon = (Boolean) troop.get("hasFiringWeapon");
        this.canScaleWall = (Boolean) troop.get("canScaleWall");
        this.canDigKhandagh = (Boolean) troop.get("canDigKhandagh");
        this.revealed = (Boolean) troop.get("revealed");
        this.cost = ((Long) troop.get("cost")).intValue();
        this.setOwnerGovernance();
    }

    private JSONObject getTroopFromDictionary(String name) {
        JSONParser jsonParser = new JSONParser();
        JSONObject troop = new JSONObject();

        try (FileReader reader = new FileReader("src/main/resources/JSON/Troops.json")) {
            troop = (JSONObject) ((JSONObject) ((JSONArray) jsonParser.parse(reader)).get(0)).get(name);
        } catch (IOException | ParseException ignored) {
        }

        return troop;
    }

    public AllResource getWeaponType() {
        return weaponType;
    }

    public AllResource getArmorType() {
        return armorType;
    }

    public boolean hasHorse() {
        return hasHorse;
    }

    public boolean isArab() {
        return isArab;
    }

    public boolean hasFiringWeapon() {
        return hasFiringWeapon;
    }

    public boolean canScaleWall() {
        return canScaleWall;
    }

    public boolean canDigKhandagh() {
        return canDigKhandagh;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
