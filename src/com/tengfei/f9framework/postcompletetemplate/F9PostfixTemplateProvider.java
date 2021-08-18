package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ztf
 */
public class F9PostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final Set<PostfixTemplate> templates = new HashSet<>();

    public F9PostfixTemplateProvider() {
        //
       templates.add(new AllSetTemplate());
       templates.add(new F9DataGridTemplate());
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