import controller.EntryMenuController;
import controller.SignupMenuController;
import controller.Utils;
import view.enums.commands.SignupMenuCommands;
import view.enums.messages.SignupMenuMessages;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class SignupMenuTest {
    private Matcher matcher;
    private static String username;
    private static String password;
    private static String email;
    private static String nickname;
    private static String slogan;
    private static int questionNumber;
    private static String recoveryAnswer;
    private static String answerConfirmation;

    public static void setCreateUserFields(Matcher matcher) {
        username = matcher.group("username");
        password = matcher.group("password");
        email = matcher.group("email");
        nickname = Utils.removeDoubleQuotation(matcher.group("nickname"));
        slogan = Utils.removeDoubleQuotation(matcher.group("slogan"));
    }

    private static void setPickQuestionFields(Matcher matcher) {
        questionNumber = Integer.parseInt(matcher.group("questionNumber"));
        recoveryAnswer = matcher.group("answer");
        answerConfirmation = matcher.group("answerConfirmation");
    }

    @BeforeAll
    public static void setupAll() {
        EntryMenuController.fillAllFieldsWithPreviousData();
    }

    private Matcher getRegisterMatcher(String command) {
        return SignupMenuCommands.getMatcher(command, SignupMenuCommands.REGISTER);
    }

    private Matcher getPickMatcher(String command) {
        return SignupMenuCommands.getMatcher(command, SignupMenuCommands.PICK_QUESTION);
    }

    @Test
    public void validTagTest() {
        matcher = getRegisterMatcher("user create -u sepehr -u sadegh -e ali.com -n saeedi");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.INVALID_COMMAND, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void emptyFieldTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n \"\" -e ali.com -p god god");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.EMPTY_FIELD, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void invalidUsernameFormat() {
        matcher = getRegisterMatcher("user create -u sepehr# -n AmirReza -e ali.com -p god god");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.INVALID_USERNAME_FORMAT, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void usernameExistTest() {
        matcher = getRegisterMatcher("user create -u amir -n AmirReza -e ali.com -p god god");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.USERNAME_EXIST, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void shortPasswordTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p god god");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.SHORT_PASSWORD, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void noUpperCaseTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p strong strong");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.NO_UPPERCASE, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void noLowerCaseTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p STRONG STRONG");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.NO_LOWERCASE, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void noNumberTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p Strong Strong");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.NO_NUMBER, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void noSpecialTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p Strong9 Strong9");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.NO_SPECIAL, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void WrongConfirmationTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p #Strong9 potato");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void emailExistTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e sadegh.sg.sn@gmail.com -p #Strong9 #Strong9");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.EMAIL_EXIST, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void invalidEmailFormatTest() {
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali.com -p #Strong9 #Strong9");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void randomArgsTest() {
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali@daee.com -p random -s random");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.SUCCESS, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }
    @Test
    public void createUserTest(){
        matcher = getRegisterMatcher("user create -u sepehr -n AmirReza -e ali@daee.com -p Amir123# Amir123# -s \"hello all\"");
        setCreateUserFields(matcher);
        assertEquals(SignupMenuMessages.SUCCESS, SignupMenuController.checkRegister(matcher, username, password, email, nickname, slogan));
    }

    @Test
    public void validTagQuestionPickTest() {
        matcher = getPickMatcher("question pick -q 2 -a sadegh -a sadegh");
        setPickQuestionFields(matcher);
        assertEquals(SignupMenuMessages.INVALID_COMMAND, SignupMenuController.checkPickQuestion(matcher, questionNumber, recoveryAnswer, answerConfirmation));
    }

    @Test
    public void invalidQuestionNumberTest() {
        matcher = getPickMatcher("question pick -q 7 -a sadegh -c sadegh");
        setPickQuestionFields(matcher);
        assertEquals(SignupMenuMessages.INVALID_QUESTION_NUMBER, SignupMenuController.checkPickQuestion(matcher, questionNumber, recoveryAnswer, answerConfirmation));
    }

    @Test
    public void wrongAnswerConfirmationTest() {
        matcher = getPickMatcher("question pick -q 2 -a sadegh -c animal");
        setPickQuestionFields(matcher);
        assertEquals(SignupMenuMessages.WRONG_ANSWER_CONFIRMATION, SignupMenuController.checkPickQuestion(matcher, questionNumber, recoveryAnswer, answerConfirmation));
    }
}
