package model.map;

import com.diogonunes.jcolor.Attribute;

public enum Color {
    BLUE_BACKGROUND(Attribute.BACK_COLOR(0, 0, 255)),
    BRIGHT_BLUE_BACKGROUND(Attribute.BACK_COLOR(85, 85, 255)),
    DARK_BLUE_BACKGROUND(Attribute.BACK_COLOR(0, 0, 110)),
    BEIGE_BACKGROUND(Attribute.BACK_COLOR(166, 166, 107)),
    GRAY_BACKGROUND(Attribute.BACK_COLOR(128, 128, 128)),
    SILVER_BACKGROUND(Attribute.BACK_COLOR(108, 108, 108)),
    DARK_GRAY_BACKGROUND(Attribute.BACK_COLOR(33, 43, 39)),
    ORANGE_BACKGROUND(Attribute.BACK_COLOR(152, 77, 1)),
    GREEN_BACKGROUND(Attribute.GREEN_BACK()),
    DARK_GREEN_BACKGROUND(Attribute.BACK_COLOR(0, 47, 30)),
    BRIGHT_GREEN_BACKGROUND(Attribute.BRIGHT_GREEN_BACK()),
    BLACK_BACKGROUND(Attribute.BLACK_BACK()),
    WHITE_BACKGROUND(Attribute.WHITE_BACK()),
    RED_TEXT(Attribute.TEXT_COLOR(150,0,0)),
    BLACK_TEXT(Attribute.BLACK_TEXT()),
    WHITE_TEXT(Attribute.WHITE_TEXT()),
    ;
    private final Attribute color;

    Color(Attribute color) {
        this.color = color;
    }

    public Attribute getColor() {
        return color;
    }
}
