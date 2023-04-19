package view;

import controller.SignupMenuController;
import view.Enums.Commands.SignupMenuCommands;
import view.Enums.Messages.SignupMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    public static void run() {
        System.out.println("You have entered signup menu!");
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command = scanner.nextLine();
        if ((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.REGISTER)) != null)
            checkRegister(matcher, scanner);
        else System.out.println("Invalid command!");
    }

    private static void checkRegister(Matcher registerMatcher, Scanner scanner) {
        String username = registerMatcher.group("username");
        SignupMenuMessages message = SignupMenuController.checkRegister(registerMatcher, username);
        switch (message) {
            case EMPTY_FIELD -> System.out.println("You can't have an empty field");
            case INVALID_USERNAME_FORMAT -> System.out.println("Invalid username format");
            case USERNAME_EXIST -> {
                System.out.println("Username Exist");
                System.out.print("Randomly generated username for you is: ");
                username = SignupMenuController.generateRandomUsername(registerMatcher.group("username"));
                System.out.println(username);
                System.out.println("if the username is acceptable to you enter 1 else enter 2");
                if (scanner.nextInt() == 1)
                    message = SignupMenuController.checkRegister(registerMatcher, username);
                else return;
            }
        }
        switch (message) {
            case SHORT_PASSWORD -> System.out.println("Password must be more than 6 characters");
            case NO_UPPERCASE -> System.out.println("Password must have an uppercase character at least");
            case NO_LOWERCASE -> System.out.println("Password must have an lowercase character at least");
            case NO_NUMBER -> System.out.println("Password must have one number at least");
            case NO_SPECIAL -> System.out.println("Password must have an special character at least");
            case WRONG_PASSWORD_CONFIRMATION -> System.out.println("Password and its confirmation doesn't match");
            case EMAIL_EXIST -> System.out.println("Email already exist");
            case INVALID_EMAIL_FORMAT -> System.out.println("Invalid email format");
            case SUCCESS -> checkPickQuestion(registerMatcher);
        }
    }

    private static void checkPickQuestion(Matcher registerMatcher) {
        System.out.print(SignupMenuController.getRecoveryQuestions());
        Matcher pickQuestionMatcher;
        Scanner scanner = EntryMenu.getScanner();
        String command = scanner.nextLine();
        while ((pickQuestionMatcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.PICK_QUESTION)) == null) {
            if (command.equals("end")) return;
            System.out.println("Invalid command");
}
        SignupMenuMessages message = SignupMenuController.checkPickQuestion(pickQuestionMatcher, registerMatcher, "", "");
        switch (message) {
            case INVALID_QUESTION_NUMBER -> System.out.println("Invalid number!");
            case WRONG_ANSWER_CONFIRMATION -> System.out.println("answer and its confirmation doesn't match");
            case SUCCESS -> System.out.println("User created successfully");
        }
    }

    private static boolean checkRandomPasswordConfirmation(String randomPassword, String confirmation) {
        return randomPassword.equals(confirmation);
    }
}
