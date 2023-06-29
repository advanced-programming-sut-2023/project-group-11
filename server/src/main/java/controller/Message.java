package controller;

public enum Message {
    INVALID_COMMAND,
    SUCCESS,
    //SIGNUP:
    INVALID_USERNAME_FORMAT,
    USERNAME_EXIST,
    SHORT_PASSWORD,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_NUMBER,
    NO_SPECIAL,
    WRONG_PASSWORD_CONFIRMATION,
    EMAIL_EXIST,
    INVALID_EMAIL_FORMAT,
    INVALID_QUESTION_NUMBER,
    WRONG_ANSWER_CONFIRMATION,
    EMPTY_FIELD,
    //LOGIN:
    USERNAME_NOT_EXIST,
    INCORRECT_PASSWORD,
    WRONG_RECOVERY_ANSWER,
    LOCKED_ACCOUNT,
    EMPTY_USERNAME_FIELD,
    EMPTY_PASSWORD_FIELD,
    EMPTY_RECOVERY_ANSWER_FIELD,
    WEAK_PASSWORD,
    //GAME
    INVALID_COORDINATE,
    INVALID_BUILDING_TYPE,
    NO_BUILDING_HERE,
    NO_UNIT_HERE,
    NOT_YOUR_UNIT,
    INVALID_UNIT_TYPE,
    CANT_BUILD_HERE,
    NOT_YOUR_BUILDING,
    NOT_ENOUGH_MONEY,
    NOT_ENOUGH_RESOURCE,
    NOT_ENOUGH_SPACE,
    NOT_ENOUGH_POPULATION,
    INVALID_RATE,
    INVALID_TEXTURE,
    CANT_DROP_IN_BUILDING,
    INVALID_LOCATION_DIFFERENT_OWNER_UNIT,
    NO_UNIT_HERE_WITH_THIS_TYPE,
    INVALID_RESOURCE_TYPE,
    USER_NOT_EXIST,
    MAP_NOT_EXIST,
    OWNER_IN_GUESTS,
    //MAP_EDIT
    INVALID_TEXTURE_TYPE,
    INVALID_DIRECTION,
    INVALID_PLACE_TO_DEPLOY,
    INVALID_COMMAND_FOR_CONSTANT_SIZE,
    MAP_NAME_FIELD_EMPTY,
    MAP_SIZE_FIELD_EMPTY,
    INVALID_MAP_SIZE_FORMAT,
    MAP_EXIST,
    INVALID_MAP_SIZE,
    EMPTY_SELECTED_TILES,
    SELECT_ONLY_ONE_TILE,
    //MARKET
    NOT_ENOUGH_GOLD,
    NOT_ENOUGH_STORAGE,
    INVALID_ITEM,
    CANCEL,
    INVALID_AMOUNT,
    //PROFILE
    INVALID_USERNAME,
    INVALID_EMAIL,
    SAME_PASSWORD,
    INVALID_FIELD_TO_DISPLAY,
    EMPTY_SLOGAN,
    //SELECT_BUILDING
    NO_MORE_ACTION,
    INVALID_TYPE,
    CANT_CREATE_HERE,
    NO_NEED_TO_REPAIR,
    CANT_REPAIR,
    ENEMY_AROUND,
    BAD_UNIT_MAKER_PLACE,
    //SELECT_UNIT
    INVALID_DESTINATION_TEXTURE,
    INVALID_DESTINATION_UNCLIMBABLE_BUILDING,
    INVALID_DESTINATION_DIFFERENT_OWNER_UNIT,
    INVALID_DISTANCE,
    INVALID_MACHINE_TYPE,
    NOT_ENOUGH_ENGINEERS,
    INVALID_UNIT_TYPE_TO_ATTACK,
    INVALID_UNIT_TYPE_TO_DIG_TUNNEL,
    EMPTY_TILE,
    FRIENDLY_ATTACK,
    OUT_OF_RANGE,
    NO_ATTACK_LEFT,
    NOT_PATROLLING,
    INVALID_STATE,
    NO_MOVES_NEEDED, NO_MOVES_LEFT,
    JUST_ONE_ENGINEER,
    ENGINEER_WITHOUT_PAIL,
    ENGINEER_EMPTY_PAIL,
    CANT_REFILL_THE_PAIL,
    INVALID_UNIT_TYPE_TO_DIG_PITCH,
    INVALID_AREA_FOR_DIGGING_PITCH,
    INVALID_START_TILE,
    INVALID_LENGTH,
    NOT_DIGGING,
    INVALID_AREA_FOR_DIGGING_TUNNEL,
    //TRADE
    INVALID_ID,
    TRADE_CLOSED,
    UNSUCCESSFUL,
    CANT_ACCEPT_YOUR_OWN_TRADE,
    NOT_ENOUGH_AMOUNT,
}
