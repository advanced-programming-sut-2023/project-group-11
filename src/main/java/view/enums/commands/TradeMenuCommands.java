package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE("trade((?<resourceTypeGroup> -t (?<resourceType>)\\S+)|" +
                "((?<resourceAmountGroup> -a (?<resourceAmount>\\d+)))|" +
                "((?<priceGroup> -p (?<price>\\d+))|((?<messageGroup> -m (?<message>\".*?[^\\\\]\"|[^\"\\s]*))){4}"),
    TRADE_LIST("trade list"),
    TRADE_ACCEPT("trade accept((?<idGroup> -i (?<id>\\d+)))|((?<messageGroup> -m" +
                " (?<message>((\".*?[^\\\\]\")|([^\"\\s]*))))){2}"),
    TRADE_HISTORY("trade history"),
    SHOW_CURRENT_MENU("show current menu"),
    BACK("back"),
    END("end")
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
