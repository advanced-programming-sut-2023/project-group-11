package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarketMenuCommands {

    END("end"),
    BACK("back"),
    SHOW_CURRENT_MENU("show current menu"),
    SHOW_LIST("show price list"),
    BUY("buy(((?<itemGroup> -i (?<itemName>\"[\\w ]+\"|\\w+)))|((?<amountGroup> -a (?<amount>\\d+)))){2}"),
    SELL("sell(((?<itemGroup> -i (?<itemName>\"[\\w ]+\"|\\w+)))|((?<amountGroup> -a (?<amount>\\d+)))){2}"),
    ;

    private final String regex;

    MarketMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MarketMenuCommands mainRegex) {
        Matcher matcher = Pattern.compile(mainRegex.regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }
}
