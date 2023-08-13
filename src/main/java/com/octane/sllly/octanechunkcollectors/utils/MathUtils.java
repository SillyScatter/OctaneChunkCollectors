package com.octane.sllly.octanechunkcollectors.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MathUtils {

    public static String formatDouble(double num) {
        if (num == Math.floor(num))
            return new DecimalFormat("###,###").format(num);
        String s = new DecimalFormat("###,###.##").format(num);
        if (s.split(Pattern.quote("."))[1].length() == 1)
            return s + "0";
        return s;
    }

    public static String formatInt(int num) {
        return new DecimalFormat("###,###").format(num);
    }
}
