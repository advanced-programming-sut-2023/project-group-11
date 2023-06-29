package controller;

import model.Stronghold;
import model.User;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.ArrayList;
import java.util.Random;

public class SignupMenuController {
    public static Message checkUsername(ArrayList parameters) {
        String username = (String) parameters.get(0);
        if (username.isEmpty()) return Message.EMPTY_FIELD;
        if (!Utils.isValidUsernameFormat(username)) return Message.INVALID_USERNAME_FORMAT;
        if (Stronghold.usernameExist(username)) return Message.USERNAME_EXIST;
        return Message.SUCCESS;
    }

    public static Message checkEmail(ArrayList parameters) {
        String email = (String) parameters.get(0);
        if (Stronghold.emailExist(email)) return Message.EMAIL_EXIST;
        if (!Utils.isValidEmailFormat(email)) return Message.INVALID_EMAIL_FORMAT;
        return Message.SUCCESS;
    }

    public static ArrayList<String> getRecoveryQuestions(ArrayList<Object> parameters) {
        return Stronghold.getRecoveryQuestions();
    }

    public static String generateRandomSlogan(ArrayList<Object> parameters) {
        ArrayList<String> randomSlogans = Stronghold.getRandomSlogans();
        int n = new Random().nextInt(0, randomSlogans.size());
        return randomSlogans.get(n);
    }

    public static String generateRandomPassword(ArrayList<Object> parameters) {
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

    public static int findHowWeakPasswordIs(ArrayList parameters) {
        String password = (String) parameters.get(0);
        int weakness = 0;
        if (password.length() < 6) weakness++;
        if (!password.matches("(?=.*[A-Z]).*")) weakness++;
        if (!password.matches("(?=.*[a-z]).*")) weakness++;
        if (!password.matches("(?=.*[0-9]).*")) weakness++;
        if (!password.matches("(?=.*[^A-Za-z0-9]).*")) weakness++;
        return weakness;
    }

    public static void createUser(ArrayList parameters) {
        String username = (String) parameters.get(0);
        String password = (String) parameters.get(1);
        String email = (String) parameters.get(2);
        String nickname = (String) parameters.get(3);
        String slogan = (String) parameters.get(4);
        String recoveryQuestion = (String) parameters.get(5);
        String recoveryAnswer = (String) parameters.get(6);
        recoveryAnswer = Utils.encryptField(recoveryAnswer);
        password = Utils.encryptField(password);
        new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan);
    }
}
