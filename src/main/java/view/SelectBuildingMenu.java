package view;

import controller.SelectBuildingMenuController;
import view.enums.commands.SelectBuildingMenuCommands;
import view.enums.messages.SelectBuildingMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SelectBuildingMenu {
    public static void run(Matcher matcher){
        System.out.println(SelectBuildingMenuController.selectBuildingDetails(matcher));
        if(!SelectBuildingMenuController.hasCommand(matcher)) return;
        Scanner scanner = EntryMenu.getScanner();
        String command;
        Matcher matcher2;
        while (true){
            command = scanner.nextLine();
            if(SelectBuildingMenuCommands.getMatcher(command,SelectBuildingMenuCommands.BACK) != null) return;
            else if((matcher2 = SelectBuildingMenuCommands.getMatcher(command,SelectBuildingMenuCommands.CREATE_UNIT)) != null
                    && SelectBuildingMenuController.isUnitMaker(matcher) )
                checkCreateUnit(matcher2,matcher);
            else if(SelectBuildingMenuCommands.getMatcher(command,SelectBuildingMenuCommands.REPAIR) != null)
                repair();
            else System.out.println("Invalid Command!");
        }
    }

    private static void checkCreateUnit(Matcher matcher,Matcher BuildingMatcher) {
        SelectBuildingMenuMessages message = SelectBuildingMenuController.checkCreateUnit(matcher,BuildingMatcher);
        switch (message){
            case INVALID_COMMAND -> System.out.println("Invalid Command!");
            case INVALID_TYPE -> System.out.println("Invalid Type!");
            case CANT_CREATE_HERE -> System.out.println("Can't Create Unit Here!");
            case NOT_ENOUGH_GOLD -> System.out.println("You Don't Have Enough Gold!");
        }
    }

    private static void repair() {

    }

    private static void attackMachine() {

    }
}
