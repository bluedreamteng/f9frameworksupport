package com.tengfei.f9framework.template;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author ztf
 */
public class F9PostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final Set<PostfixTemplate> templates;

    public F9PostfixTemplateProvider() {
        templates = ContainerUtil.newHashSet(
                new AllSetTemplate()
        );
    }

    @NotNull
    @Override
    public Set<PostfixTemplate> getTemplates() {
        return templates;
    }
}