package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.openapi.util.text.StringUtil;
import com.tengfei.f9framework.postcompletetemplate.templatelibray.F9PostfixTemplateLibrary;
import com.tengfei.f9framework.postcompletetemplate.templatelibray.PostfixTemplateInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ztf
 */
public class F9JavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final Set<PostfixTemplate> templates = new HashSet<>();

    public F9JavaPostfixTemplateProvider() {
        //
        templates.add(new F9allSetTemplate());
        List<PostfixTemplateInfo> htmlPostfixTemplates = F9PostfixTemplateLibrary.getJavaPostfixTemplates();
        for (PostfixTemplateInfo htmlPostfixTemplate : htmlPostfixTemplates) {
            if (StringUtil.isNotEmpty(htmlPostfixTemplate.getName()) && StringUtil.isNotEmpty(htmlPostfixTemplate.getTemplate())) {
                templates.add(new F9JavaPostfixTemplate(htmlPostfixTemplate.getName(), htmlPostfixTemplate.getExample(), htmlPostfixTemplate.getTemplate(), this));
            }
        }
    }

    @NotNull
    @Override
    public Set<PostfixTemplate> getTemplates() {
        return templates;
    }

    @NotNull
    @Override
    public String getId() {
        return "custom";
    }
}