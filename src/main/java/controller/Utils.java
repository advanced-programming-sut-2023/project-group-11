//define your general methods here

package controller;

import model.Stronghold;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Utils {
    public static boolean checkEmptyField(String username, String password, String email, String nickname, String slogan, boolean hasSlogan) {
        return (username.isEmpty() ||
                password.isEmpty() ||
                email.isEmpty() ||
                nickname.isEmpty()) ||
                (hasSlogan && slogan.isEmpty());
    }

    public static boolean isValidUsernameFormat(String username) {
        return username.matches("\\w+");
    }

    public static boolean usernameExist(String username) {
        return Stronghold.getUserByUsername(username) != null;
    }

    public static boolean isStrongPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,}$");
    }

    public static boolean correctPasswordConfirmation(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

    public static boolean isValidEmailFormat(String email) {
        return email.matches("[\\w.]+@[\\w.]+\\.[\\w.]+");
    }

    public static String encryptField(String field) {
        return new DigestUtils("SHA3-256").digestAsHex(field);
    }
    public static String generateCaptcha(int captchaNumber) {
        Random random = new Random();
        int width = 100;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics2D = image.getGraphics();
        graphics2D.setFont(new Font("SansSerif", Font.BOLD, 22));
        Graphics2D graphics = (Graphics2D) graphics2D;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        StringBuilder captcha = new StringBuilder();
        graphics.drawString(Integer.toString(captchaNumber), 10, 25);

        for (int y = 0; y < height; y++) {
            StringBuilder captchaLine = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int randomNumber = random.nextInt(8);
                if (image.getRGB(x, y) == -16777216 || (x + y) % 8 == randomNumber)
                    captchaLine.append(" ");
                else captchaLine.append("*");
            }
            if (captchaLine.toString().trim().isEmpty()) {
                continue;
            }
            captcha.append(captchaLine).append("\n");
        }
        return captcha.toString();
    }
}