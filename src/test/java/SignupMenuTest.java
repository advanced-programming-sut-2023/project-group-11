import controller.EntryMenuController;
import controller.SignupMenuController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import view.enums.commands.SignupMenuCommands;
import view.enums.messages.SignupMenuMessages;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class SignupMenuTest {


    @BeforeAll
    public static void setupAll(){
        EntryMenuController.fillAllFieldsWithPreviousData();
    }

    private Matcher getRegisterMatcher(String command){
        return SignupMenuCommands.getMatcher(command,SignupMenuCommands.REGISTER);
    }

    private Matcher getPickMatcher(String command){
        return SignupMenuCommands.getMatcher(command,SignupMenuCommands.PICK_QUESTION);
    }

    @Test
    public void validTagTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -u sadegh -e ali.com -n saeedi");
        assertEquals(SignupMenuMessages.INVALID_COMMAND,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Disabled
    @Test
    public void emptyFieldTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n \"\" -e ali.com -p god god");
        assertEquals(SignupMenuMessages.EMPTY_FIELD,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void invalidUsernameFormat(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr# -n amireza -e ali.com -p god god");
        assertEquals(SignupMenuMessages.INVALID_USERNAME_FORMAT,SignupMenuController.checkRegister(matcher,"sepehr#"));
    }

    @Test
    public void usernameExistTest(){
        Matcher matcher = getRegisterMatcher("user create -u amir -n amireza -e ali.com -p god god");
        assertEquals(SignupMenuMessages.USERNAME_EXIST,SignupMenuController.checkRegister(matcher,"SadeghDr"));
    }

    @Test
    public void shortPasswordTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p god god");
        assertEquals(SignupMenuMessages.SHORT_PASSWORD,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void noUpperCaseTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p strong strong");
        assertEquals(SignupMenuMessages.NO_UPPERCASE,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void noLowerCaseTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p STRONG STRONG");
        assertEquals(SignupMenuMessages.NO_LOWERCASE,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void noNumberTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p Strong Strong");
        assertEquals(SignupMenuMessages.NO_NUMBER,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void noSpecialTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p Strong9 Strong9");
        assertEquals(SignupMenuMessages.NO_SPECIAL,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void WrongConfirmationTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p #Strong9 potato");
        assertEquals(SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void emailExistTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e sadegh.sg.sn@gmail.com -p #Strong9 #Strong9");
        assertEquals(SignupMenuMessages.EMAIL_EXIST,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void invalidEmailFormatTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali.com -p #Strong9 #Strong9");
        assertEquals(SignupMenuMessages.INVALID_EMAIL_FORMAT,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void randomArgsTest(){
        Matcher matcher = getRegisterMatcher("user create -u sepehr -n amireza -e ali@daee.com -p random -s random");
        assertEquals(SignupMenuMessages.SUCCESS,SignupMenuController.checkRegister(matcher,"sepehr"));
    }

    @Test
    public void validTagQuestionPickTest(){
        Matcher matcher = getPickMatcher("question pick -q 2 -a sadegh -a sadegh");
        assertEquals(SignupMenuMessages.INVALID_COMMAND,SignupMenuController.checkPickQuestion(matcher));
    }

    @Test
    public void invalidQuestionNumberTest(){
        Matcher matcher = getPickMatcher("question pick -q 7 -a sadegh -c sadegh");
        assertEquals(SignupMenuMessages.INVALID_QUESTION_NUMBER,SignupMenuController.checkPickQuestion(matcher));
    }

    @Test
    public void wrongAnswerConfirmationTest(){
        Matcher matcher = getPickMatcher("question pick -q 2 -a sadegh -c animal");
        assertEquals(SignupMenuMessages.WRONG_ANSWER_CONFIRMATION,SignupMenuController.checkPickQuestion(matcher));
    }
}
