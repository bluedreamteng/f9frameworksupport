package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ztf
 */
public class F9JavaPostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final Set<PostfixTemplate> templates = new HashSet<>();

    public F9JavaPostfixTemplateProvider() {
        //
       templates.add(new F9allSetTemplate());
       templates.add(new F9DataGridTemplate());
       templates.add(new F9DeleteTemplate());
       templates.add(new F9ExportTemplate());
       templates.add(new F9ComboboxTemplate());
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