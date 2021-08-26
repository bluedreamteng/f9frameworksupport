package com.tengfei.f9framework.completioncontributor.dictionary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ws.rest.client.header.HttpHeadersDictionary;
import com.tengfei.f9framework.projectsetting.F9ApplicationSettingState;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
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
            Map<String, List<F9HtmlAttributeValueDocumentation>> xmlAttributeDocumentation = getXmlAttributeDocumentation();
            xmlAttributes = xmlAttributeDocumentation.keySet();
        }
        return xmlAttributes;
    }

    @NotNull
    private static Map<String, List<F9HtmlAttributeValueDocumentation>> readXmlAttributeValues() {
        Map<String, List<F9HtmlAttributeValueDocumentation>> result = new HashMap<>();
        String htmlAttributeDictionaryPath = F9ApplicationSettingState.getInstance().htmlAttributeDictionaryPath;
        if(StringUtil.isEmpty(htmlAttributeDictionaryPath)) {
            return result;
        }
        try {
            InputStream stream = new FileInputStream(htmlAttributeDictionaryPath);
            String file = FileUtil.loadTextAndClose(stream);
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
