package view.enums.messages;

public enum SignupMenuMessages {
    EMPTY_FIELD(""),
    INVALID_USERNAME_FORMAT("invalid format"),
    USERNAME_EXIST("username exist"),
    SHORT_PASSWORD(("")),
    NO_UPPERCASE(""),
    NO_LOWERCASE(""),
    NO_NUMBER(""),
    NO_SPECIAL(""),
    WRONG_PASSWORD_CONFIRMATION(""),
    EMAIL_EXIST(""),
    INVALID_EMAIL_FORMAT(""),
    INVALID_QUESTION_NUMBER(""),
    WRONG_ANSWER_CONFIRMATION(""),
    INVALID_COMMAND(""),
    SUCCESS("");

    private String message;

    SignupMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
