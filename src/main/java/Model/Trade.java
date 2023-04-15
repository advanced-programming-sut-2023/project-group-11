package Model;

public record Trade(String resourceType, int resourceAmount, int price, String message, User sender) {

}
