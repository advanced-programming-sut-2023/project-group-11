package controller;

import model.Stronghold;
import model.User;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import view.Enums.Messages.SignupMenuMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;

public class SignupMenuController {
    public static SignupMenuMessages checkRegister(Matcher registerMatcher, String username) {
        String password = registerMatcher.group("password");
        String passwordConfirmation = null;
        if (!password.equals("random")) {
            passwordConfirmation = Arrays.asList(password.split(" ")).get(1);
            password = Arrays.asList(password.split(" ")).get(0);
        }
        String email = registerMatcher.group("email").toLowerCase();
        String nickname = registerMatcher.group("nickname");
        String slogan = registerMatcher.group("slogan");
        boolean hasSlogan = slogan != null;
        if (Controller.checkEmptyField(username, password, email, nickname, slogan, hasSlogan))
            return SignupMenuMessages.EMPTY_FIELD;
        if (!Controller.isValidUsernameFormat(username))
            return SignupMenuMessages.INVALID_USERNAME_FORMAT;
        if (Controller.usernameExist(username)) return SignupMenuMessages.USERNAME_EXIST;
        if (!Controller.isStrongPassword(password) && !password.equals("random"))
            return findWeakPartOfPassword(password);
        if (!password.equals("random"))
            if (!Controller.correctPasswordConfirmation(password, passwordConfirmation))
                return SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION;
        if (Stronghold.emailExist(email)) return SignupMenuMessages.EMAIL_EXIST;
        if (!Controller.isValidEmailFormat(email)) return SignupMenuMessages.INVALID_EMAIL_FORMAT;
        return SignupMenuMessages.SUCCESS;
    }

    public static SignupMenuMessages checkPickQuestion(Matcher pickQuestionMatcher, Matcher registerMatcher, String username, String password, String slogan) {
        String email = registerMatcher.group("email").toLowerCase();
        String nickname = registerMatcher.group("nickname");
        ArrayList<String> recoveryQuestions = Stronghold.getRecoveryQuestions();
        int questionNumber = Integer.parseInt(pickQuestionMatcher.group("questionNumber"));
        String recoveryQuestion = recoveryQuestions.get(questionNumber - 1);
        String recoveryAnswer = pickQuestionMatcher.group("answer");
        String answerConfirmation = pickQuestionMatcher.group("answerConfirmation");
        if (questionNumber > recoveryQuestions.size()) return SignupMenuMessages.INVALID_QUESTION_NUMBER;
        if (!recoveryAnswer.equals(answerConfirmation)) return SignupMenuMessages.WRONG_ANSWER_CONFIRMATION;
        Stronghold.registerUser(new User(username, password, email, nickname, recoveryQuestion, recoveryAnswer, slogan));
        return SignupMenuMessages.SUCCESS;
    }

    public static String getRecoveryQuestions() {
        return Stronghold.printRecoveryQuestions();
    }

    public static String printRandomSlogans() {
        return Stronghold.printRandomSlogans();
    }

    public static String pickRandomSlogan(String number) {
        ArrayList<String> randomSlogans = Stronghold.getRandomSlogans();
        if (!number.matches("\\d+") || Integer.parseInt(number) > randomSlogans.size()) return null;
        return randomSlogans.get(Integer.parseInt(number) - 1);
    }

    public static String generateRandomPassword() {
        // create character rule for lower case
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        // set number of lower case characters
        lowerCaseRule.setNumberOfCharacters(1);

        // create character rule for upper case
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        // set number of upper case characters
        upperCaseRule.setNumberOfCharacters(1);

        // create character rule for digit
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        // set number of digits
        digitRule.setNumberOfCharacters(1);

        // create character rule for lower case
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
        // set number of special characters
        specialCharacterRule.setNumberOfCharacters(1);

        // create instance of the PasswordGenerator class
        PasswordGenerator passGen = new PasswordGenerator();

        // call generatePassword() method of PasswordGenerator class to get Passay generated password
        return passGen.generatePassword(8, specialCharacterRule, lowerCaseRule, upperCaseRule, digitRule);
    }

    public static String generateRandomUsername(String username) {
        Random random = new Random();
        while (Controller.usernameExist(username)) username += random.nextInt(10);
        return username;
    }

    public static SignupMenuMessages findWeakPartOfPassword(String password) {
        if (password.length() < 6) return SignupMenuMessages.SHORT_PASSWORD;
        if (!password.matches("(?=.*[A-Z]).*")) return SignupMenuMessages.NO_UPPERCASE;
        if (!password.matches("(?=.*[a-z]).*")) return SignupMenuMessages.NO_LOWERCASE;
        if (!password.matches("(?=.*[0-9]).*")) return SignupMenuMessages.NO_NUMBER;
        if (!password.matches("(?=.*[^A-Za-z0-9]).*")) return SignupMenuMessages.NO_SPECIAL;
        return null;
    }
}
