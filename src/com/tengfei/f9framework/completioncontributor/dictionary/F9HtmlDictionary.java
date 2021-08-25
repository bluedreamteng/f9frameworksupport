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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ztf
 */
public class F9HtmlDictionary {
    private static Map<String, List<F9HtmlAttributeValueDocumentation>> xmlattributDocumentation;
    private static Set<String> xmlAttributes;

    @NotNull
    public static synchronized Map<String, List<F9HtmlAttributeValueDocumentation>> getXmlAttributeDocumentation() {
        if (xmlattributDocumentation == null) {
            xmlattributDocumentation = readXmlAttributeValues();
        }
        return xmlattributDocumentation;
    }

    @NotNull
    public static synchronized Set<String> getXmlAttributes() {
        if (xmlAttributes == null) {
            xmlAttributes = readXmlAttributes();
        }
        return xmlAttributes;
    }

    private static Set<String> readXmlAttributes() {
        Map<String, List<F9HtmlAttributeValueDocumentation>> xmlAttributeDocumentation = getXmlAttributeDocumentation();
        return xmlAttributeDocumentation.keySet();

    }

    @NotNull
    private static Map<String, List<F9HtmlAttributeValueDocumentation>> readXmlAttributeValues() {
        Map<String, List<F9HtmlAttributeValueDocumentation>> result = new HashMap<>();
        InputStream stream = F9HtmlDictionary.class.getResourceAsStream("xml_attribute_documentation.json");
        try {
            String file = stream != null ? FileUtil.loadTextAndClose(stream) : "";
            if (StringUtil.isNotEmpty(file)) {
                Gson gson = new Gson();
                result = gson.fromJson(file, new TypeToken<Map<String, List<F9HtmlAttributeValueDocumentation>>>() {
                }.getType());
            }
        } catch (IOException exception) {
            Logger.getInstance(HttpHeadersDictionary.class).error(exception);
        }

        return result;
    }
}
