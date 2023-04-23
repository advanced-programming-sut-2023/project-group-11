package model.map;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

public enum Color {
    BLUE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(0, 0, 255))),
    BRIGHT_BLUE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(85, 85, 255))),
    DARK_BLUE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(0, 0, 110))),
    BEIGE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(166, 166, 107))),
    GRAY_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(128, 128, 128))),
    SILVER_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(192, 192, 192))),
    DARK_GRAY_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(33, 43, 39))),
    ORANGE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(152, 77, 1))),
    GREEN_BACKGROUND(new AnsiFormat(Attribute.GREEN_BACK())),
    DARK_GREEN_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(0, 47, 30))),
    BRIGHT_GREEN_BACKGROUND(new AnsiFormat(Attribute.BRIGHT_GREEN_BACK())),
    BLACK_BACKGROUND(new AnsiFormat(Attribute.BLACK_BACK())),
    WHITE_BACKGROUND(new AnsiFormat(Attribute.WHITE_BACK())),
    ;
    private final AnsiFormat colorCode;

    Color(AnsiFormat colorCode) {
        this.colorCode = colorCode;
    }

    public AnsiFormat getColorCode() {
        return colorCode;
    }
}
