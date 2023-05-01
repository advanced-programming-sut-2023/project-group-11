package model.people;

import model.Stronghold;
import model.people.enums.Damage;
import model.people.enums.Speed;
import model.people.enums.TroopTypes;
import model.AllResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Troops extends Units {
    protected final TroopTypes type;
    protected AllResource weaponType;
    protected AllResource armorType;
    protected int damage;
    protected boolean hasHorse;
    protected boolean isArab;
    protected boolean hasFiringWeapon;
    protected final boolean canScaleWall;
    protected final boolean canDigKhandagh;
    protected final int range;
    protected final double damageRatioOnArmor;
    protected boolean revealed;


    public Troops(String name) {
        JSONObject troop = getTroopFromDictionary(name);

        this.type = TroopTypes.valueOf(name.toUpperCase());
        this.hp = ((Long) troop.get("hp")).intValue();
        this.speed = Speed.valueOf((String) troop.get("speed"));
        this.weaponType = AllResource.valueOf((String) troop.get("weaponType"));
        this.armorType = AllResource.valueOf((String) troop.get("armorType"));
        this.damage = Damage.valueOf((String) troop.get("damage")).getDamage();
        this.hasHorse = (Boolean) troop.get("hasHorse");
        this.isArab = (Boolean) troop.get("isArab");
        this.hasFiringWeapon = (Boolean) troop.get("hasFiringWeapon");
        this.range = ((Long) troop.get("range")).intValue();
        this.damageRatioOnArmor = (Double) troop.get("damageRatioOnArmor");
        this.canScaleWall = (Boolean) troop.get("canScaleWall");
        this.canDigKhandagh = (Boolean) troop.get("canDigKhandagh");
        this.revealed = (Boolean) troop.get("revealed");
        this.cost = ((Long) troop.get("cost")).intValue();
        this.ownerGovernance = Stronghold.getCurrentGame().getCurrentGovernance();
    }

    private JSONObject getTroopFromDictionary(String name) {
        JSONParser jsonParser = new JSONParser();
        JSONObject troop = new JSONObject();

        try (FileReader reader = new FileReader("src/main/resources/Troops.json")) {
            troop = (JSONObject) ((JSONObject) ((JSONArray) jsonParser.parse(reader)).get(0)).get(name);
        } catch (IOException | ParseException ignored) {
        }

        return troop;
    }

    public TroopTypes getType() {
        return type;
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

    public double getDamageRatioOnArmor() {
        return damageRatioOnArmor;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    @Override
    public String getName() {
        return getType().toString().toLowerCase();
    }
}
