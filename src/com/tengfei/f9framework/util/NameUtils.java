package com.tengfei.f9framework.util;

import com.intellij.openapi.util.text.StringUtil;

/**
 * 命名工具类
 */
public class NameUtils {
    public static String toCamelCaseWithFirstUpper(String name) {
        if(name == null) {
            return null;
        }
        name = name.toLowerCase();
        return StringUtil.toTitleCase(name).replaceAll("_","");
    }

    public static String toCamelCaseWithFirstLower(String name) {
        if(name == null) {
            return null;
        }
        String s = toCamelCaseWithFirstUpper(name);
        char[] chars = s.toCharArray();
        chars[0] = StringUtil.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }
}
