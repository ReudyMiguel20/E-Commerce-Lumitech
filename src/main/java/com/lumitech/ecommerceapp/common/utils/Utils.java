package com.lumitech.ecommerceapp.common.utils;

public class Utils {

    // Method that returns the string with the first letter capitalized
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
