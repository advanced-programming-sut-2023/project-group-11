package view;


import controller.ProfileMenuController;
import controller.Utils;
import view.enums.commands.ProfileMenuCommands;
import view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private static ProfileMenuMessages profileMenuMessage;

    public static void run() {
        Scanner scanner = EntryMenu.getScanner();
        String input;
        Matcher matcher;

        while (true) {
            input = scanner.nextLine();

            if (ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.BACK) != null) {
                System.out.println("You have entered main menu!");
                return;
            } else if (ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.END) != null)
                Utils.endStronghold();
            else if (ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.SHOW_CURRENT_MENU) != null)
                System.out.println("Profile menu");
            else if ((matcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.PROFILE_CHANGE)) != null)
                checkChangeProfile(matcher);
            else if ((matcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.CHANGE_PASSWORD)) != null)
                checkChangePassword(matcher);
            else if ((matcher = ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.PROFILE_DISPLAY)) != null)
                checkDisplayProfile(matcher);
            else if (ProfileMenuCommands.getMatcher(input, ProfileMenuCommands.REMOVE_SLOGAN) != null)
                removeSlogan();
            else System.out.println("Invalid command!");
        }
    }

    private static void checkChangeProfile(Matcher matcher) {
        switch (matcher.group("tag")) {
            case "u" -> checkChangeUsername(matcher);
            case "n" -> checkChangeNickname(matcher);
            case "e" -> checkChangeEmail(matcher);
            case "s" -> checkChangeSlogan(matcher);
            default -> System.out.println("Invalid tag!");
        }
    }

    private static void checkChangeUsername(Matcher matcher) {
        profileMenuMessage = ProfileMenuController.checkChangeUsername(matcher);

        switch (profileMenuMessage) {
            case SUCCESS ->
                    System.out.println("Your username changed to: " + Utils.removeDoubleQuotation(matcher.group("field")));
            case USERNAME_EXIST -> System.out.println("Username exists!");
            case INVALID_USERNAME -> System.out.println("Invalid username format!");
        }
    }

    private static void checkChangeNickname(Matcher matcher) {
        ProfileMenuController.changeNickname(matcher);
        System.out.println("Your nickname changed to: " + Utils.removeDoubleQuotation(matcher.group("field")));
    }

    private static void checkChangeEmail(Matcher matcher) {
        profileMenuMessage = ProfileMenuController.checkChangeEmail(matcher);

        switch (profileMenuMessage) {
            case SUCCESS -> System.out.println("Your email changed to: " + matcher.group("field"));
            case EMAIL_EXIST -> System.out.println("Email exists!");
            case INVALID_EMAIL -> System.out.println("Invalid email format!");
        }
    }

    private static void checkChangeSlogan(Matcher matcher) {
        ProfileMenuController.changeSlogan(matcher);
        System.out.println("Your slogan changed to: " + Utils.removeDoubleQuotation(matcher.group("field")));
    }

    private static void checkChangePassword(Matcher matcher) {
        profileMenuMessage = ProfileMenuController.checkChangePassword(matcher);

        switch (profileMenuMessage) {
            case SUCCESS -> {
                if (Menu.checkCaptchaConfirmation()) System.out.println("Your password changed successfully!");
            }
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case INCORRECT_PASSWORD -> System.out.println("Incorrect password!");
            case SAME_PASSWORD ->
                    System.out.println("Invalid new password: New password is same as the previous password!");
            case NO_NUMBER -> System.out.println("Weak password: Password must have one number at least!");
            case NO_LOWERCASE ->
                    System.out.println("Weak password: Password must have an lowercase character at least!");
            case NO_UPPERCASE ->
                    System.out.println("Weak password: Password must have an uppercase character at least!");
            case SHORT_PASSWORD -> System.out.println("Weak password: Password must be more than 6 characters!");
            case NO_SPECIAL -> System.out.println("Weak password: Password must have a special character at least!");
        }
    }

    private static void checkDisplayProfile(Matcher matcher) {
        profileMenuMessage = ProfileMenuController.checkDisplayProfile(matcher);

        switch (profileMenuMessage) {
            case SUCCESS -> System.out.println(ProfileMenuController.displayProfile(matcher.group("field")));
            case INVALID_COMMAND -> System.out.println("Invalid command!");
            case EMPTY_SLOGAN -> System.out.println("Slogan is empty!");
        }
    }

    private static void removeSlogan() {
        ProfileMenuController.removeSlogan();
    }
}
