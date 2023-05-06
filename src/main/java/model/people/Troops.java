package model.people;

import model.AllResource;
import model.Stronghold;
import model.people.enums.TroopDamage;
import model.people.enums.Speed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Troops extends Units {
    protected AllResource weaponType;
    protected AllResource armorType;
    protected int damage;
    protected boolean hasHorse;
    protected boolean isArab;
    protected boolean hasFiringWeapon;
    protected final boolean canScaleWall;
    protected final boolean canDigKhandagh;
    protected final int range;
    protected boolean revealed;


    public Troops(String name) {
        JSONObject troop = getTroopFromDictionary(name);

        this.name = name;
        this.hp = ((Long) troop.get("hp")).intValue();
        this.speed = Speed.valueOf((String) troop.get("speed"));
        this.leftMoves = speed.getMovesInEachTurn();
        this.weaponType = AllResource.valueOf((String) troop.get("weaponType"));
        this.armorType = AllResource.valueOf((String) troop.get("armorType"));
        this.damage = TroopDamage.valueOf((String) troop.get("damage")).getDamage();
        this.hasHorse = (Boolean) troop.get("hasHorse");
        this.isArab = (Boolean) troop.get("isArab");
        this.hasFiringWeapon = (Boolean) troop.get("hasFiringWeapon");
        this.range = ((Long) troop.get("range")).intValue();
        this.canScaleWall = (Boolean) troop.get("canScaleWall");
        this.canDigKhandagh = (Boolean) troop.get("canDigKhandagh");
        this.revealed = (Boolean) troop.get("revealed");
        this.cost = ((Long) troop.get("cost")).intValue();
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
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

    public int getDamage() {
        return damage;
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

    public int getRange() {
        return range;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
