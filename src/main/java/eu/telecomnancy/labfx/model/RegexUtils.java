package eu.telecomnancy.labfx.model;

import java.util.regex.Pattern;

public class RegexUtils {
    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private static Pattern patternMail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static boolean isValidMail(String strMail) {
        if (strMail == null) {
            return false;
        }
        return patternMail.matcher(strMail).matches();
    }
}
