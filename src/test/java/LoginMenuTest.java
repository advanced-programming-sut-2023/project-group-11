//import controller.EntryMenuController;
//import controller.LoginMenuController;
//import org.junit.jupiter.api.*;
//import view.enums.commands.LoginMenuCommands;
//import view.enums.messages.LoginMenuMessages;
//
//import java.util.regex.Matcher;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class LoginMenuTest {
//    private Matcher matcher;
//
//    @BeforeAll
//    public static void setupAll() {
//        EntryMenuController.fillAllFieldsWithPreviousData();
//    }
//
//    private Matcher getMatcher(String command, LoginMenuCommands loginMenuCommand) {
//        return LoginMenuCommands.getMatcher(command, loginMenuCommand);
//    }
//
//    @Test
//    public void checkLogin_InvalidTags() {
//        matcher = getMatcher("user login -u amir -u Amir12*", LoginMenuCommands.LOGIN);
//        assertEquals(LoginMenuMessages.INVALID_COMMAND, LoginMenuController.checkLogin(matcher));
//    }
//
//    @Test
//    public void checkLogin_UsernameNotExist() {
//        matcher = getMatcher("user login -u amiri -p Amir12*", LoginMenuCommands.LOGIN);
//        assertEquals(LoginMenuMessages.USERNAME_NOT_EXIST, LoginMenuController.checkLogin(matcher));
//    }
//
//    @Test
//    @Order(2)
//    public void checkLogin_IncorrectPassword() {
//        matcher = getMatcher("user login -p Amir -u amir", LoginMenuCommands.LOGIN);
//        assertEquals(LoginMenuMessages.INCORRECT_PASSWORD, LoginMenuController.checkLogin(matcher));
//    }
//
//    @Test
//    @Order(3)
//    public void checkLogin_LockedAccount() {
//        matcher = getMatcher("user login -p Amir12* -u amir", LoginMenuCommands.LOGIN);
//        assertEquals(LoginMenuMessages.LOCKED_ACCOUNT, LoginMenuController.checkLogin(matcher));
//    }
//
//    @Test
//    @Order(1)
//    public void checkLogin_Success() {
//        matcher = getMatcher("user login -p Amir12* -u amir", LoginMenuCommands.LOGIN);
//        assertEquals(LoginMenuMessages.SUCCESS, LoginMenuController.checkLogin(matcher));
//    }
//
//    @Test
//    public void checkForgotPassword_UsernameNotExist() {
//        matcher = getMatcher("forgot my password -u sadeghdr", LoginMenuCommands.FORGOT_PASSWORD);
//        assertEquals(LoginMenuMessages.USERNAME_NOT_EXIST, LoginMenuController.checkForgotPassword(matcher));
//    }
//
//    @Test
//    public void checkForgotPassword_Success() {
//        matcher = getMatcher("forgot my password -u Sadegh", LoginMenuCommands.FORGOT_PASSWORD);
//        assertEquals(LoginMenuMessages.SUCCESS, LoginMenuController.checkForgotPassword(matcher));
//    }
//}
