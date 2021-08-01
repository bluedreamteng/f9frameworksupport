package com.tengfei.f9framework.entity;

import com.intellij.openapi.util.text.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TypeMapper {
    private final static Map<Pattern, String> typeMappers = new HashMap<>();

    static {
        //初始化类型映射
        typeMappers.put(Pattern.compile("varchar(\\(\\d+\\))?"), "java.lang.String");
        typeMappers.put(Pattern.compile("char(\\(\\d+\\))?"), "java.lang.String");
        typeMappers.put(Pattern.compile("(tiny|medium|long)*text"), "java.lang.String");
        typeMappers.put(Pattern.compile("decimal(\\(\\d+\\))?"), "java.lang.Double");
        typeMappers.put(Pattern.compile("decimal(\\(\\d+,\\d+\\))?"), "java.lang.Double");
        typeMappers.put(Pattern.compile("integer"), "java.lang.Integer");
        typeMappers.put(Pattern.compile("(tiny|small|medium)*int(\\(\\d+\\))?"), "java.lang.Integer");
        typeMappers.put(Pattern.compile("int4"), "java.lang.Integer");
        typeMappers.put(Pattern.compile("int8"), "java.lang.Long");
        typeMappers.put(Pattern.compile("bigint(\\(\\d+\\))?"), "java.lang.Long");
        typeMappers.put(Pattern.compile("datetime"), "java.util.Date");
        typeMappers.put(Pattern.compile("timestamp"), "java.util.Date");
        typeMappers.put(Pattern.compile("boolean"), "java.lang.Boolean");
    }

    public static String getJavaTypeBySqlType(String sqlType) {
        if (StringUtil.isEmpty(sqlType)) {
            return null;
        }

        for (Map.Entry<Pattern, String> item : typeMappers.entrySet()) {
            if (item.getKey().matcher(sqlType).matches()) {
                return item.getValue();
            }
        }
        return "java.lang.Object";
    }

}
