package model.map;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

public enum Color {
    BLUE_BACKGROUND(new AnsiFormat(Attribute.BLUE_BACK())),
    BEIGE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(245, 245, 220))),
    GRAY_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(128, 128, 128))),
    ORANGE_BACKGROUND(new AnsiFormat(Attribute.BACK_COLOR(255,165,0))),

    RED_BACKGROUND(new AnsiFormat(Attribute.RED_BACK())),
    BLACK_BACKGROUND(new AnsiFormat(Attribute.BLACK_BACK())),
    GREEN_BACKGROUND(new AnsiFormat(Attribute.GREEN_BACK())),
    ANSI_WHITE_BACKGROUND(new AnsiFormat(Attribute.WHITE_BACK())),
    ;
    private final AnsiFormat colorCode;

    Color(AnsiFormat colorCode) {
        this.colorCode = colorCode;
    }

    public AnsiFormat getColorCode() {
        return colorCode;
    }
}
