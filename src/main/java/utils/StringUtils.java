package utils;

import app.AppState;

public class StringUtils {

    public static String removeLeadingWhitespace(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("^\\s+", "");
    }

    public static String getQRCodeURL(int id) {
        return Constants.URL + id;
    }

    public static void main(String[] args) {
        System.out.println(getQRCodeURL(10));
    }
}
