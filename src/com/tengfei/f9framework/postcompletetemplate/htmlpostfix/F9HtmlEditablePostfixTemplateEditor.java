package com.tengfei.f9framework.postcompletetemplate.htmlpostfix;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.codeInsight.template.postfix.settings.PostfixTemplateEditorBase;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.editable.JavaPostfixTemplateExpressionCondition;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9HtmlEditablePostfixTemplateEditor extends PostfixTemplateEditorBase<JavaPostfixTemplateExpressionCondition> {
    @NotNull
    private final JPanel myPanel;

    public F9HtmlEditablePostfixTemplateEditor(@NotNull PostfixTemplateProvider provider) {
        super(provider, createEditor(), false);

        myPanel = FormBuilder.createFormBuilder()
                .addComponentFillVertically(myEditTemplateAndConditionsPanel, UIUtil.DEFAULT_VGAP)
                .getPanel();
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("#expr# #lowercaseexpr#");
    }

    @NotNull
    private static Editor createEditor() {
        return createEditor(null, createDocument(ProjectManager.getInstance().getDefaultProject()));
    }

    @NotNull
    @Override
    public F9HtmlEditablePostfixTemplate createTemplate(@NotNull String templateId, @NotNull String templateName) {
        String templateText = myTemplateEditor.getDocument().getText();
        return new F9HtmlEditablePostfixTemplate(templateId, templateName, "", templateText,
                myProvider);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return myPanel;
    }


    private static Document createDocument(@Nullable Project project) {
        if (project == null) {
            return EditorFactory.getInstance().createDocument("");
        }
        PsiFile htmlLanguageFile = PsiFileFactory.getInstance(project).createFileFromText(HTMLLanguage.INSTANCE, "");
        DaemonCodeAnalyzer.getInstance(project).setHighlightingEnabled(htmlLanguageFile, false);
        return PsiDocumentManager.getInstance(project).getDocument(htmlLanguageFile);
    }

    @Override
    protected void fillConditions(@NotNull DefaultActionGroup group) {

    }

    @Override
    public void setTemplate(@Nullable PostfixTemplate template) {
        if (!(template instanceof F9HtmlEditablePostfixTemplate)) {
            return;
        }
        ApplicationManager.getApplication()
                .runWriteAction(() -> this.myTemplateEditor.getDocument().setText(((F9HtmlEditablePostfixTemplate) template).getLiveTemplate().getString()));
    }
}
