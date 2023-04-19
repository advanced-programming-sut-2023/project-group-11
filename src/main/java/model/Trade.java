package model;

public record Trade(String resourceType, int resourceAmount, int price, String message, User sender) {

}
