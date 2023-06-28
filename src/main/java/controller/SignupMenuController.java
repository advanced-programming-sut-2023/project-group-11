package controller;

import model.Stronghold;
import model.User;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import view.enums.messages.SignupMenuMessages;

import java.util.ArrayList;
import java.util.Random;

public class SignupMenuController {
    public static SignupMenuMessages checkUsername(String username) {
        if (username.isEmpty()) return SignupMenuMessages.EMPTY_FIELD;
        if (!Utils.isValidUsernameFormat(username)) return SignupMenuMessages.INVALID_USERNAME_FORMAT;
        if (Stronghold.usernameExist(username)) return SignupMenuMessages.USERNAME_EXIST;
        return SignupMenuMessages.SUCCESS;
    }

    public static SignupMenuMessages checkEmail(String email) {
        if (Stronghold.emailExist(email)) return SignupMenuMessages.EMAIL_EXIST;
        if (!Utils.isValidEmailFormat(email)) return SignupMenuMessages.INVALID_EMAIL_FORMAT;
        return SignupMenuMessages.SUCCESS;
    }

    public static ArrayList<String> getRecoveryQuestions() {
        return Stronghold.getRecoveryQuestions();
    }

    public static String generateRandomSlogan() {
        ArrayList<String> randomSlogans = Stronghold.getRandomSlogans();
        int n = new Random().nextInt(0, randomSlogans.size());
        return randomSlogans.get(n);
    }

    public static String generateRandomPassword() {
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(1);

        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(1);

        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(1);

        CharacterRule specialCharacterRule = new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return "INSUFFICIENT_SPECIAL";
            }

            @Override
            public String getCharacters() {
                return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
            }
        });
        specialCharacterRule.setNumberOfCharacters(1);

        PasswordGenerator passGen = new PasswordGenerator();

        return passGen.generatePassword(8, specialCharacterRule, lowerCaseRule, upperCaseRule, digitRule);
    }

    public static String generateRandomUsername(String username) {
        Random random = new Random();
        while (Stronghold.usernameExist(username)) username += random.nextInt(10);
        return username;
    }

    public static int findHowWeakPasswordIs(String password) {
        int weakness = 0;
        if (password.length() < 6) weakness++;
        if (!password.matches("(?=.*[A-Z]).*")) weakness++;
        if (!password.matches("(?=.*[a-z]).*")) weakness++;
        if (!password.matches("(?=.*[0-9]).*")) weakness++;
        if (!password.matches("(?=.*[^A-Za-z0-9]).*")) weakness++;
        return weakness;
    }

    public static void createUser(String username, String password, String email, String nickname, String slogan, String recoveryQuestion, String recoveryAnswer) {
        recoveryAnswer = Utils.encryptField(recoveryAnswer);
        password = Utils.encryptField(password);
        new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan);
    }
}
