package com.tengfei.f9framework.postcompletetemplate.templatelibray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ws.rest.client.header.HttpHeadersDictionary;
import com.tengfei.f9framework.projectsetting.F9ApplicationSettingState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public class F9PostfixTemplateLibrary {
    private static List<PostfixTemplateInfo> htmlPostfixTemplates;

    private static List<PostfixTemplateInfo> javaPostfixTemplates;


    public static synchronized List<PostfixTemplateInfo> getHtmlPostfixTemplates() {
        String htmlPostfixLibraryPath = F9ApplicationSettingState.getInstance().htmlPostfixLibraryPath;
        if (htmlPostfixTemplates == null) {
            htmlPostfixTemplates = readPostfixTemplates(htmlPostfixLibraryPath);
        }
        return htmlPostfixTemplates;
    }

    public static synchronized List<PostfixTemplateInfo> getJavaPostfixTemplates() {
        String javaPostfixLibraryPath = F9ApplicationSettingState.getInstance().javaPostfixLibraryPath;
        if (javaPostfixTemplates == null) {
            javaPostfixTemplates = readPostfixTemplates(javaPostfixLibraryPath);
        }
        return javaPostfixTemplates;
    }

    private static List<PostfixTemplateInfo> readPostfixTemplates(String filePath) {
        List<PostfixTemplateInfo> result = new ArrayList<>();
        if (StringUtil.isEmpty(filePath)) {
            return result;
        }
        try {
            InputStream stream = new FileInputStream(filePath);
            String file = FileUtil.loadTextAndClose(stream);
            if (StringUtil.isNotEmpty(file)) {
                Gson gson = new Gson();
                result = gson.fromJson(file, new TypeToken<List<PostfixTemplateInfo>>() {
                }.getType());
            }
        }
        catch (IOException exception) {
            Logger.getInstance(HttpHeadersDictionary.class).error(exception);
        }
        return result;


    }
}
