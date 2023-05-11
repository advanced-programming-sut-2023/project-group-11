import controller.EntryMenuController;
import controller.ProfileMenuController;
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
        EntryMenuController.getStayLoggedIn();
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
    @Order(2)
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
    @Order(4)
    public void changeEmail_InvalidEmail() {
        matcher = getMatcher("profile change -e ami$@gmail.com", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.INVALID_EMAIL, ProfileMenuController.checkChangeEmail(matcher));
    }

    @Test
    @Order(5)
    public void changeEmail_EmailExist() {
        matcher = getMatcher("profile change -e amir@gma.co", ProfileMenuCommands.PROFILE_CHANGE);
        assertEquals(ProfileMenuMessages.EMAIL_EXIST, ProfileMenuController.checkChangeEmail(matcher));
    }

    @Test
    @Order(6)
    public void changePassword_InvalidTags() {
        matcher = getMatcher("profile change password -n ali -n 123", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.INVALID_COMMAND, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(7)
    public void changePassword_IncorrectPassword() {
        matcher = getMatcher("profile change password -o Amir12** -n Amir*Sadegh", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.INCORRECT_PASSWORD, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(8)
    public void changePassword_SamePassword() {
        matcher = getMatcher("profile change password -n Amir12* -o Amir12*", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.SAME_PASSWORD, ProfileMenuController.checkChangePassword(matcher));
    }

    @Test
    @Order(9)
    public void changePassword_Success() {
        matcher = getMatcher("profile change password -o Amir12* -n Sadegh#$!@213", ProfileMenuCommands.CHANGE_PASSWORD);
        assertEquals(ProfileMenuMessages.SUCCESS, ProfileMenuController.checkChangePassword(matcher));
    }
}
