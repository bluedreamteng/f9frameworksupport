package com.tengfei.f9framework.completioncontributor.dictionary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ws.rest.client.header.HttpHeadersDictionary;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class F9HtmlDictionary {
    private static Map<String, List<String>> xmlAttributeValues;
    private static List<String> xmlAttributes;

    @NotNull
    public static synchronized Map<String, List<String>> getXmlAttributeValues() {
        if(xmlAttributeValues == null) {
            xmlAttributeValues = readXmlAttributeValues();
        }
        return xmlAttributeValues;
    }

    @NotNull
    public static synchronized List<String> getXmlAttributes() {
        if(xmlAttributes == null) {
            xmlAttributes = readXmlAttributes();
        }
        return xmlAttributes;
    }

    private static List<String> readXmlAttributes() {
        List<String> result = new ArrayList<>();
        InputStream stream = F9HtmlDictionary.class.getResourceAsStream("xml_attribute_values.json");
        try {
            String file = stream != null ? FileUtil.loadTextAndClose(stream) : "";
            if (StringUtil.isNotEmpty(file)) {
                Gson gson = new Gson();
                Map<String,String> map = gson.fromJson(file,new TypeToken<Map<String,List<String>>>(){}.getType());
                result.addAll(map.keySet());
            }
        } catch (IOException exception) {
            Logger.getInstance(HttpHeadersDictionary.class).error(exception);
        }
        return result;
    }

    @NotNull
    private static Map<String, List<String>> readXmlAttributeValues() {
        Map<String, List<String>> result = new HashMap<>();
        InputStream stream = F9HtmlDictionary.class.getResourceAsStream("xml_attribute_values.json");
        try {
            String file = stream != null ? FileUtil.loadTextAndClose(stream) : "";
            if (StringUtil.isNotEmpty(file)) {
                Gson gson = new Gson();
                result = gson.fromJson(file,new TypeToken<Map<String,List<String>>>(){}.getType());
            }
        } catch (IOException exception) {
            Logger.getInstance(HttpHeadersDictionary.class).error(exception);
        }

        return result;
    }
}
