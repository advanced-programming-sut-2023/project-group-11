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
import java.util.Map;

public class Troop extends Attacker {
    private final AllResource weaponType;
    private final AllResource armorType;
    private final boolean hasHorse;
    private final boolean isArab;
    private final boolean hasFiringWeapon;
    private final boolean canDigPitch;
    private boolean revealed;
    private boolean isDigging = false;
    private String diggingDirection;
    private int leftDiggingLength;


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
        this.canDigPitch = (Boolean) troop.get("canDigPitch");
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

    public boolean canDigPitch() {
        return canDigPitch;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isDigging() {
        return isDigging;
    }

    public void setDigging(boolean digging) {
        isDigging = digging;
    }

    public String getDiggingDirection() {
        return diggingDirection;
    }

    public void setDiggingDirection(String diggingDirection) {
        this.diggingDirection = diggingDirection;
    }

    public int getLeftDiggingLength() {
        return leftDiggingLength;
    }

    public void setLeftDiggingLength(int leftDiggingLength) {
        this.leftDiggingLength = leftDiggingLength;
    }
}
