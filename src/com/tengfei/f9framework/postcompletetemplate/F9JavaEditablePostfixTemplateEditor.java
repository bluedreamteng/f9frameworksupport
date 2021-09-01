package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.codeInsight.template.postfix.settings.PostfixTemplateEditorBase;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.editable.JavaPostfixTemplateExpressionCondition;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.JavaCodeFragmentFactory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author ztf
 */
public class F9JavaEditablePostfixTemplateEditor extends PostfixTemplateEditorBase<JavaPostfixTemplateExpressionCondition> {
    @NotNull private final JPanel myPanel;


    public F9JavaEditablePostfixTemplateEditor(@NotNull PostfixTemplateProvider provider) {
        super(provider, createEditor(),false);
        myPanel = FormBuilder.createFormBuilder()
                .addComponentFillVertically(myEditTemplateAndConditionsPanel, UIUtil.DEFAULT_VGAP)
                .getPanel();
    }

    @Override
    protected void fillConditions(@NotNull DefaultActionGroup group) {

    }

    @NotNull
    private static Editor createEditor() {
        return createEditor(null, createDocument(ProjectManager.getInstance().getDefaultProject()));
    }

    private static Document createDocument(@Nullable Project project) {
        if (project == null) {
            return EditorFactory.getInstance().createDocument("");
        }
        final JavaCodeFragmentFactory factory = JavaCodeFragmentFactory.getInstance(project);
        final JavaCodeFragment fragment = factory.createCodeBlockCodeFragment("", null, true);
        DaemonCodeAnalyzer.getInstance(project).setHighlightingEnabled(fragment, false);
        return PsiDocumentManager.getInstance(project).getDocument(fragment);
    }

    @NotNull
    @Override
    public PostfixTemplate createTemplate(@NotNull String templateId, @NotNull String templateName) {
        String templateText = myTemplateEditor.getDocument().getText();
        return new F9JavaEditablePostfixTemplate(templateId, templateName, "", templateText,
                myProvider);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return myPanel;
    }
}
