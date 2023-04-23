package view;

import controller.LoginMenuController;
import controller.Utils;
import view.enums.commands.LoginMenuCommands;
import view.enums.messages.LoginMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        Matcher matcher;
        String command = scanner.nextLine();
        while (true) {
            if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.END) != null) Utils.endStronghold();
            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.BACK) != null) return;
            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Login Menu!");
            else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN)) != null) {
                if (checkLogin(matcher) == LoginMenuMessages.SUCCESS) return;
            } else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD)) != null)
                checkForgotPassword(matcher);
            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGOUT) != null)
                System.out.println(Utils.logout());
            else System.out.println("Invalid command!");
            command = scanner.nextLine();
        }
    }

    private static LoginMenuMessages checkLogin(Matcher matcher) {
        LoginMenuMessages message = LoginMenuController.checkLogin(matcher);

        switch (message) {
            case USERNAME_NOT_EXIST -> System.out.println("Username doesn't exist!");
            case INCORRECT_PASSWORD -> System.out.println("Username and password didn't match!");
            case LOCKED_ACCOUNT -> System.out.println("Your account is locked for " +
                    LoginMenuController.getLeftLockedTime(matcher) / 1000.0 + " seconds more!");
            case SUCCESS -> {
                if (Menu.checkCaptchaConfirmation()) {
                    LoginMenuController.loginUser(matcher);
                    System.out.println("Logged in! Entered Main Menu");
                    MainMenu.run();
                }
            }
        }
        return message;
    }

    private static void checkForgotPassword(Matcher matcher) {
        LoginMenuMessages message = LoginMenuController.checkForgotPassword(matcher);
        switch (message) {
            case USERNAME_NOT_EXIST -> System.out.println("Username doesn't exist!");
            case SUCCESS -> showRecoveryQuestion(matcher);
        }
    }

    private static void showRecoveryQuestion(Matcher matcher) {
        System.out.println("Your recovery question is: " + LoginMenuController.showRecoveryQuestion(matcher));
        System.out.println("Print your recovery answer here: ");
        Scanner scanner = EntryMenu.getScanner();
        String recoveryAnswer = scanner.nextLine();
        while (true) {
            if (LoginMenuCommands.getMatcher(recoveryAnswer, LoginMenuCommands.END) != null) Utils.endStronghold();
            else if (LoginMenuCommands.getMatcher(recoveryAnswer, LoginMenuCommands.BACK) != null) return;
            else {
                LoginMenuMessages message = LoginMenuController.checkRecoveryAnswer(matcher, recoveryAnswer);
                switch (message) {
                    case WRONG_RECOVERY_ANSWER -> System.out.println("Wrong recovery answer!");
                    case SUCCESS -> changePassword(matcher);
                }
            }
            recoveryAnswer = scanner.nextLine();
        }
    }

    private static void changePassword(Matcher matcher) {
        System.out.println("Print your new password here:");
        Scanner scanner = EntryMenu.getScanner();
        String newPassword = scanner.nextLine();
        while (true) {
            if (LoginMenuCommands.getMatcher(newPassword, LoginMenuCommands.END) != null) Utils.endStronghold();
            else if (LoginMenuCommands.getMatcher(newPassword, LoginMenuCommands.BACK) != null) return;
            else if (Utils.isStrongPassword(newPassword)) {
                if (Menu.checkCaptchaConfirmation()) {
                    LoginMenuController.setNewPassword(matcher, newPassword);
                    System.out.println("You have set a new password successfully!");
                    return;
                }
            } else System.out.println("New password is weak! Please try again");
            newPassword = scanner.nextLine();
        }
    }
}
