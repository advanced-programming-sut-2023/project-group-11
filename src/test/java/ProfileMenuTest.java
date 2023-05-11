import controller.EntryMenuController;
import controller.ProfileMenuController;
import model.Stronghold;
import org.junit.jupiter.api.*;
import view.enums.commands.ProfileMenuCommands;
import view.enums.messages.ProfileMenuMessages;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileMenuTest {
    private Matcher matcher;

    @BeforeAll
    public static void setupAll() {
        EntryMenuController.fillAllFieldsWithPreviousData();
        setCurrentUser("amir");
    }

    public static void setCurrentUser(String username) {
        Stronghold.setCurrentUser(Stronghold.getUserByUsername(username));
    }

    private Matcher getMatcher(String command, ProfileMenuCommands profileMenuCommand) {
        return ProfileMenuCommands.getMatcher(command, profileMenuCommand);
    }

    @Test
    @Order(1)
    public void changeUsername_InvalidUsername() {
        matcher = getMatcher("profile change -u @MirR$", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.INVALID_USERNAME, ProfileMenuController.checkChangeUsername(matcher));
    }

    @Test
    @Order(1)
    public void changeUsername_UsernameExist() {
        matcher = getMatcher("profile change -u amir", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.USERNAME_EXIST, ProfileMenuController.checkChangeUsername(matcher));
    }

    @Test
    @Order(3)
    public void changeUsername_Success() {
        matcher = getMatcher("profile change -u sepehr_123", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkChangeUsername(matcher));
    }

    @Test
    @Order(1)
    public void changeEmail_InvalidEmail() {
        matcher = getMatcher("profile change -e ami$@gmail.com", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.INVALID_EMAIL, ProfileMenuController.checkChangeEmail(matcher));
    }

    @Test
    @Order(1)
    public void changeEmail_EmailExist() {
        matcher = getMatcher("profile change -e amir@gma.co", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.EMAIL_EXIST, ProfileMenuController.checkChangeEmail(matcher));
    }

    @Test
    @Order(1)
    public void changeEmail_Success() {
        matcher = getMatcher("profile change -e amir@gmail.com", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkChangeEmail(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_InvalidTags() {
        matcher = getMatcher("profile change password -n ali -n 123", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.INVALID_COMMAND, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_IncorrectPassword() {
        matcher = getMatcher("profile change password -o Amir12** -n Amir*Sadegh", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.INCORRECT_PASSWORD, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_SamePassword() {
        matcher = getMatcher("profile change password -n Amir12* -o Amir12*", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.SAME_PASSWORD, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_ShortPassword() {
        matcher = getMatcher("profile change password -o Amir12* -n Ami$1", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.SHORT_PASSWORD, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_NoUppercase() {
        setCurrentUser("amir");
        matcher = getMatcher("profile change password -o Amir12* -n ami$#1", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.NO_UPPERCASE, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_NoLowercase() {
        matcher = getMatcher("profile change password -o Amir12* -n ALI2$1", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.NO_LOWERCASE, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_NoNumber() {
        matcher = getMatcher("profile change password -o Amir12* -n Ami$rD", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.NO_NUMBER, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void changePassword_NoSpecial() {
        matcher = getMatcher("profile change password -o Amir12* -n Ami1rD", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.NO_SPECIAL, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(2)
    public void changePassword_Success() {
        setCurrentUser("amir");
        matcher = getMatcher("profile change password -o Amir12* -n Sadegh#$!@213", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_InvalidField() {
        matcher = getMatcher("profile display SadeghDr", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.INVALID_FIELD_TO_DISPLAY, ProfileMenuController.checkDisplayProfile(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_EmptySlogan() {
        setCurrentUser("amir");
        matcher = getMatcher("profile display slogan", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.EMPTY_SLOGAN, ProfileMenuController.checkDisplayProfile(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_Success1() {
        setCurrentUser("SadeghDr");
        matcher = getMatcher("profile display slogan", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkDisplayProfile(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_Success2() {
        setCurrentUser("SadeghDr");
        matcher = getMatcher("profile display highscore", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkDisplayProfile(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_Success3() {
        setCurrentUser("SadeghDr");
        matcher = getMatcher("profile display rank", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkDisplayProfile(matcher));
    }

    @Test
    @Order(1)
    public void displayProfile_Success4() {
        setCurrentUser("SadeghDr");
        matcher = getMatcher("profile display", ProfileMenuCommands.PROFILE_DISPLAY);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkDisplayProfile(matcher));
    }
}
