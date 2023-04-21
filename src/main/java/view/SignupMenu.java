package view;

import controller.SignupMenuController;
import view.Enums.Commands.SignupMenuCommands;
import view.Enums.Messages.SignupMenuMessages;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    public static void run() {
        System.out.println("You have entered signup menu!");
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command = scanner.nextLine();
        while (true) {
            if ((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.REGISTER)) != null) {
                if (checkTags(matcher, SignupMenuCommands.REGISTER)) checkRegister(matcher);
                else System.out.println("Invalid command!");
            } else if (command.equals("end")) System.exit(0);
            else if (command.equals("back")) EntryMenu.run();
            else System.out.println("Invalid command!");
            command = scanner.nextLine();
        }
    }

    private static boolean checkTags(Matcher matcher, SignupMenuCommands signupMenuCommands) {
        switch (signupMenuCommands) {
            case REGISTER -> {
                return matcher.group("usernameTag") != null &&
                        matcher.group("passwordTag") != null &&
                        matcher.group("emailTag") != null &&
                        matcher.group("nicknameTag") != null;//TODO: duplicated options?
            }
            case PICK_QUESTION -> {
                return matcher.group("questionNumberTag") != null &&
                        matcher.group("answerTag") != null &&
                        matcher.group("confirmationTag") != null;
            }
        }
        return false;
    }

    private static void checkRegister(Matcher registerMatcher) {
        String username = registerMatcher.group("username");
        String password = registerMatcher.group("password");
        password = Arrays.asList(password.split(" ")).get(0);
        String slogan = registerMatcher.group("slogan");
        SignupMenuMessages message = SignupMenuController.checkRegister(registerMatcher, username);
        switch (message) {
            case EMPTY_FIELD -> System.out.println("You can't have an empty field");
            case INVALID_USERNAME_FORMAT -> System.out.println("Invalid username format");
            case USERNAME_EXIST -> {
                username = generateRandomUsername(username);
                if (username != null)
                    message = SignupMenuController.checkRegister(registerMatcher, username);
                else return;
            }
        }
        switch (message) {
            case SHORT_PASSWORD -> System.out.println("Password must be more than 6 characters!");
            case NO_UPPERCASE -> System.out.println("Password must have an uppercase character at least!");
            case NO_LOWERCASE -> System.out.println("Password must have an lowercase character at least!");
            case NO_NUMBER -> System.out.println("Password must have one number at least!");
            case NO_SPECIAL -> System.out.println("Password must have a special character at least!");
            case WRONG_PASSWORD_CONFIRMATION -> System.out.println("Password and its confirmation doesn't match!");
            case EMAIL_EXIST -> System.out.println("Email already exist!");
            case INVALID_EMAIL_FORMAT -> System.out.println("Invalid email format!");
            case SUCCESS -> {
                if (slogan != null && slogan.equals("random")) {
                    slogan = checkRandomSlogan();
                    System.out.println("Your slogan is: " + slogan);
                }
                if (password.equals("random"))
                    password = checkRandomPassword();
                checkPickQuestion(registerMatcher, username, password, slogan);
            }
        }
    }

    private static String generateRandomUsername(String username) {
        Scanner scanner = EntryMenu.getScanner();
        System.out.println("Username already exist!");
        System.out.print("Randomly generated username for you is: ");
        username = SignupMenuController.generateRandomUsername(username);
        System.out.println(username);
        System.out.println("If the username is acceptable to you enter 1 else enter 2");
        if (scanner.nextLine().equals("1")) return username;
        else return null;
    }

    private static String checkRandomSlogan() {
        Scanner scanner = EntryMenu.getScanner();
        System.out.println("Please choose a slogan from below list:");
        System.out.print(SignupMenuController.printRandomSlogans());
        String sloganNumber = scanner.nextLine();
        return SignupMenuController.pickRandomSlogan(sloganNumber);
    }

    private static String checkRandomPassword() {
        Scanner scanner = EntryMenu.getScanner();
        String password = SignupMenuController.generateRandomPassword();
        System.out.println("Your random password is: " + password);
        System.out.println("Please re-enter your password here:");
        String confirmation = scanner.nextLine();
        while (!password.equals(confirmation)) {
            if (confirmation.equals("end")) System.exit(0);
            System.out.println("password and its confirmation doesn't match");
            confirmation = EntryMenu.getScanner().nextLine();
        }
        return password;
    }

    private static void checkPickQuestion(Matcher registerMatcher, String username, String password, String slogan) {
        System.out.println("pick a question from the below list:");
        System.out.print(SignupMenuController.getRecoveryQuestions());
        Matcher pickQuestionMatcher;
        Scanner scanner = EntryMenu.getScanner();
        String command = scanner.nextLine();
        SignupMenuMessages message;
        while ((pickQuestionMatcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.PICK_QUESTION)) == null) {
            if (command.equals("end")) System.exit(0);
            System.out.println("Invalid command");
        }
        if (checkTags(pickQuestionMatcher, SignupMenuCommands.PICK_QUESTION))
            message = SignupMenuController.checkPickQuestion(pickQuestionMatcher, registerMatcher, username, password, slogan);
        else {
            System.out.println("Invalid command!");
            return;
        }
        switch (message) {
            case INVALID_QUESTION_NUMBER -> System.out.println("Invalid number!");
            case WRONG_ANSWER_CONFIRMATION -> System.out.println("answer and its confirmation doesn't match!");
            case SUCCESS -> System.out.println("User created successfully!");
        }
    }
}
