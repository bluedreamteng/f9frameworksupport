package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public abstract class F9AbstractPostfixTemplate extends PostfixTemplate {

    private String template;
    /**
     * @param name    name of postfixTemplate
     * @param example 示例
     */
    protected F9AbstractPostfixTemplate(@NotNull String name, @NotNull String example,@NotNull String template,PostfixTemplateProvider provider) {
       super(null,name,example, provider);
       this.template = template;
    }

    /**
     * 元素是否适用此模板
     * @param context context
     * @param copyDocument copyDocument
     * @param newOffset newOffset
     * @return true 适用  false 不适用
     */
    @Override
    public abstract boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset);

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(context);
        Project project = editor.getProject();
        assert project != null;
        final TemplateManager manager = TemplateManager.getInstance(project);
        final Template template = manager.createTemplate("", "", buildStringTemplate(context));
        template.setToReformat(true);
        manager.startTemplate(editor, template);
    }

    /**
     * 构造模板
     * @param context psielement
     * @return string 模板
     */
    public String buildStringTemplate(PsiElement context){
        //替换模板变量
        return template.replaceAll("\\$capitalexpr\\$", StringUtil.capitalize(context.getText())).replaceAll("\\$expr\\$",context.getText());
    }
}