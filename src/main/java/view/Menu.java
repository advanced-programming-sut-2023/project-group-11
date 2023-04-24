package view;

import controller.Utils;

import java.util.Random;
import java.util.Scanner;

public class Menu {
    static boolean checkCaptchaConfirmation() {
        Scanner scanner = EntryMenu.getScanner();
        while (true) {
            System.out.println("Enter the captcha below!");
            int bound = (int) Math.pow(10, new Random().nextInt(3, 9));
            int captchaNumber = new Random().nextInt(1000, bound);
            System.out.println(Utils.generateCaptcha(captchaNumber));
            String enteredCaptcha = scanner.nextLine();
            if (enteredCaptcha.equals("end")) Utils.endStronghold();
            else if (enteredCaptcha.equals("back")) return false;
            else if (enteredCaptcha.equals("generate another captcha")) continue;
            else if (enteredCaptcha.matches("\\d+")) {
                if (Utils.checkCaptchaConfirmation(Integer.parseInt(enteredCaptcha), captchaNumber)) return true;
                else System.out.println("Wrong captcha confirmation!");
            } else System.out.println("Wrong captcha confirmation!");
        }
    }
}
