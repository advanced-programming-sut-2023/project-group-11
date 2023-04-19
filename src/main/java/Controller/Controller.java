//define your general methods here

package Controller;

import Model.Stronghold;

public class Controller {
    public static boolean checkEmptyField(String username, String password, String email, String nickname, String slogan, boolean hasSlogan) {
        return (username.isEmpty() ||
                password.isEmpty() ||
                email.isEmpty() ||
                nickname.isEmpty()) ||
                (hasSlogan && slogan.isEmpty());
    }

    public static boolean isValidUsernameFormat(String username) {
        return username.matches("\\w+");
    }

    public static boolean usernameExist(String username) {
        return Stronghold.getUserByUsername(username) != null;
    }

    public static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,}$");
    }

    public static boolean correctPasswordConfirmation(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    public static boolean isValidEmailFormat(String email) {
        return email.matches("[\\w.]+@[\\w.]+\\.[\\w.]+");
    }
}
