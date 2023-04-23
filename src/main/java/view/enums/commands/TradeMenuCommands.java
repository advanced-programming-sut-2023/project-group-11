package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE("trade(( (?<resourceTypeTag>-t) (?<resourceType>)\\S+)|" +
                "( (?<resourceAmountTag>-a) (?<resourceAmount>\\d+))|" +
                " (?<priceTag>-p) (?<price>\\d+)|( (?<messageTag>-m) (?<message>\".*?[^\\\\]\"|[^\"\\s]*))){4}"),
    TRADE_LIST("trade list"),
    TRADE_ACCEPT("trade accept(( (?<idTag>-i) (?<id>\\d+))|(( (?<messageTag>-m))" +
                " (?<message>((\".*?[^\\\\]\")|([^\"\\s]*))))){2}"),
    TRADE_HISTORY("trade history"),
    SHOW_CURRENT_MENU("show current menu"),
    BACK("back");
    ;

    private final String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, TradeMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
