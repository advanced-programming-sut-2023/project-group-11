package view;

import controller.Utils;
import controller.SignupMenuController;
import view.enums.commands.SignupMenuCommands;
import view.enums.messages.SignupMenuMessages;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command = scanner.nextLine();
        while (true) {
            if (SignupMenuCommands.getMatcher(command, SignupMenuCommands.END) != null)
                Utils.endStronghold();
            else if (SignupMenuCommands.getMatcher(command, SignupMenuCommands.BACK) != null) {
                System.out.println("Entered Entry Menu!");
                return;
            } else if (SignupMenuCommands.getMatcher(command, SignupMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Signup Menu");
            else if ((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.REGISTER)) != null)
                checkRegister(matcher);
            else System.out.println("Invalid command!");
            command = scanner.nextLine();
        }
    }

    private static void checkRegister(Matcher registerMatcher) {
        String username = registerMatcher.group("username");
        String password = registerMatcher.group("password");
        String slogan = registerMatcher.group("slogan");
        username = Utils.removeDoubleQuotation(username);
        slogan = Utils.removeDoubleQuotation(slogan);
        password = Arrays.asList(password.split(" ")).get(0);

        SignupMenuMessages message = SignupMenuController.checkRegister(registerMatcher, username);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case EMPTY_FIELD -> System.out.println("You can't have an empty field");
            case INVALID_USERNAME_FORMAT -> System.out.println("Invalid username format");
            case USERNAME_EXIST -> {
                username = generateRandomUsername(username);
                if (username != null) message = SignupMenuController.checkRegister(registerMatcher, username);
                else return;
            }
        }
        switch (message) {
            case SHORT_PASSWORD -> System.out.println("Weak password: Password must be more than 6 characters!");
            case NO_UPPERCASE ->
                    System.out.println("Weak password: Password must have an uppercase character at least!");
            case NO_LOWERCASE ->
                    System.out.println("Weak password: Password must have an lowercase character at least!");
            case NO_NUMBER -> System.out.println("Weak password: Password must have one number at least!");
            case NO_SPECIAL -> System.out.println("Weak password: Password must have a special character at least!");
            case WRONG_PASSWORD_CONFIRMATION -> System.out.println("Password and its confirmation doesn't match!");
            case EMAIL_EXIST -> System.out.println("Email already exist!");
            case INVALID_EMAIL_FORMAT -> System.out.println("Invalid email format!");
            case SUCCESS -> {
                if (slogan != null && slogan.equals("random")) {
                    slogan = checkRandomSlogan();
                    if (slogan == null) return;
                    System.out.println("Your slogan is: " + slogan);
                }
                if (password.equals("random")) password = checkRandomPassword();
                if (password != null) checkPickQuestion(registerMatcher, username, password, slogan);
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
        String userAnswer = scanner.nextLine();
        while (true) {
            if (userAnswer.equals("1")) return username;
            else if (userAnswer.equals("2") || SignupMenuCommands.getMatcher(userAnswer, SignupMenuCommands.BACK) != null)
                return null;
            else if (SignupMenuCommands.getMatcher(userAnswer, SignupMenuCommands.END) != null) Utils.endStronghold();
            else System.out.println("Invalid command: only 1 and 2 is acceptable!");
            userAnswer = scanner.nextLine();
        }
    }

    private static String checkRandomSlogan() {
        Scanner scanner = EntryMenu.getScanner();
        System.out.println("Please choose a slogan from below list:");
        System.out.print(SignupMenuController.printRandomSlogans());
        String sloganNumber = scanner.nextLine();
        String randomSlogan;
        while (true) {
            if (SignupMenuCommands.getMatcher(sloganNumber, SignupMenuCommands.BACK) != null) return null;
            if ((randomSlogan = SignupMenuController.pickRandomSlogan(sloganNumber)) != null) return randomSlogan;
            sloganNumber = scanner.nextLine();
        }
    }

    private static String checkRandomPassword() {
        Scanner scanner = EntryMenu.getScanner();
        String password = SignupMenuController.generateRandomPassword();
        System.out.println("Your random password is: " + password);
        System.out.println("Please re-enter your password here:");
        String confirmation = scanner.nextLine();
        while (!password.equals(confirmation)) {
            if (SignupMenuCommands.getMatcher(confirmation, SignupMenuCommands.END) != null) Utils.endStronghold();
            else if (SignupMenuCommands.getMatcher(confirmation, SignupMenuCommands.BACK) != null) return null;
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
        while ((pickQuestionMatcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.PICK_QUESTION)) == null) {
            if (SignupMenuCommands.getMatcher(command, SignupMenuCommands.END) != null) Utils.endStronghold();
            else if (SignupMenuCommands.getMatcher(command, SignupMenuCommands.BACK) != null) return;
            System.out.println("Invalid command");
            command = scanner.nextLine();
        }

        SignupMenuMessages message = SignupMenuController.checkPickQuestion(pickQuestionMatcher);

        switch (message) {
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INVALID_QUESTION_NUMBER -> System.out.println("Invalid number!");
            case WRONG_ANSWER_CONFIRMATION -> System.out.println("answer and its confirmation doesn't match!");
            case SUCCESS -> {
                if (Menu.checkCaptchaConfirmation()) {
                    System.out.println("User created successfully!");
                    SignupMenuController.createUser(pickQuestionMatcher, registerMatcher, username, password, slogan);
                }
            }
        }
    }
}