package com.tengfei.f9framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ztf
 */
public class StringUtil {

    public static final Pattern PERIODOPERATORPATTERN = Pattern.compile("\\.");

    public static String trim(String s) {
        if (s != null) {
            return s.replaceAll("'", "").replaceAll("\"", "");
        }
        return "";
    }


    public static boolean isEquals(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }


    public static int countPeriodOperator(String text) {
        Matcher matcher = PERIODOPERATORPATTERN.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static boolean isBlank(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

}
