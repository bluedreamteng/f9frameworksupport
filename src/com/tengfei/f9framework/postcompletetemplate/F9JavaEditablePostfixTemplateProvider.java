package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateSettings;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.editable.PostfixTemplateEditor;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import kotlin.LazyKt;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.intellij.codeInsight.template.postfix.templates.PostfixTemplatesUtils.readExternalLiveTemplate;

/**
 * @author ztf
 */
public class F9JavaEditablePostfixTemplateProvider implements PostfixTemplateProvider {

    /**
     * Identifier of template provider. Used for storing settings of provider's templates.
     */
    @NotNull
    @Override
    public String getId() {
        return "custom.java";
    }

    /**
     * Presentation name of editable template type. If null, provider doesn't allow to custom templates.
     */
    @Nullable
    @Override
    public String getPresentableName() {
        return "F9Java";
    }

    /**
     * Returns builtin templates registered in the provider in their original state.
     */
    @NotNull
    @Override
    public Set<PostfixTemplate> getTemplates() {
        return new HashSet<>();
    }

    /**
     * Check symbol can separate template keys
     *
     * @param currentChar
     */
    @Override
    public boolean isTerminalSymbol(char currentChar) {
        return currentChar == '.' || currentChar == '!';
    }

    /**
     * Prepare file for template expanding. Running on EDT.
     * E.g. java postfix templates adds semicolon after caret in order to simplify context checking.
     * <p>
     * File content doesn't contain template's key, it is deleted just before this method invocation.
     * <p>
     * Note that while postfix template is checking its availability the file parameter is a _COPY_ of the real file,
     * so you can do with it anything that you want, but in the same time it doesn't recommended to modify editor state because it's real.
     *
     * @param file
     * @param editor
     */
    @Override
    public void preExpand(@NotNull PsiFile file, @NotNull Editor editor) {

    }

    /**
     * Invoked after template finished (doesn't matter if it finished successfully or not).
     * E.g. java postfix template use this method for deleting inserted semicolon.
     *
     * @param file
     * @param editor
     */
    @Override
    public void afterExpand(@NotNull PsiFile file, @NotNull Editor editor) {

    }

    /**
     * Prepare file for checking availability of templates.
     * Almost the same as {@link this#preExpand(PsiFile, Editor)} with several differences:
     * 1. Processes copy of file. So implementations can modify it without corrupting the real file.
     * 2. Could be invoked from anywhere (EDT, write-action, read-action, completion-thread etc.). So implementations should make
     * additional effort to make changes in file.
     * <p>
     * Content of file copy doesn't contain template's key, it is deleted just before this method invocation.
     * <p>
     * NOTE: editor is real (not copy) and it doesn't represents the copyFile.
     * So it's safer to use currentOffset parameter instead of offset from editor. Do not modify text via editor.
     *
     * @param copyFile
     * @param realEditor
     * @param currentOffset
     */
    @NotNull
    @Override
    public PsiFile preCheck(@NotNull PsiFile copyFile, @NotNull Editor realEditor, int currentOffset) {
        return copyFile;
    }

    /**
     * Return the editor that it able to represent template in UI
     * and create the template from the settings that users set in UI.
     * <p>
     * If templateToEdit is null, it's considered like an editor for a new template.
     *
     * @param templateToEdit
     */
    @Nullable
    @Override
    public PostfixTemplateEditor createEditor(@Nullable PostfixTemplate templateToEdit) {
        if (templateToEdit == null ||
                templateToEdit instanceof F9JavaEditablePostfixTemplate) {
            F9JavaEditablePostfixTemplateEditor f9JavaEditablePostfixTemplateEditor = new F9JavaEditablePostfixTemplateEditor(this);
            f9JavaEditablePostfixTemplateEditor.setTemplate(templateToEdit);
            return f9JavaEditablePostfixTemplateEditor;
        }
        return null;
    }

    /**
     * Instantiates the template that was serialized by the provider to XML.
     *
     * @param id
     * @param name
     * @param template
     */
    @Nullable
    @Override
    public PostfixTemplate readExternalTemplate(@NotNull String id, @NotNull String name, @NotNull Element template) {
        TemplateImpl liveTemplate = readExternalLiveTemplate(template, this);
        if (liveTemplate == null) {
            return null;
        }
        return new F9JavaEditablePostfixTemplate(id, name, "",liveTemplate, this);
    }

    /**
     * Serialized to XML the template that was created by the provider.
     *
     * @param template
     * @param parentElement
     */
    @Override
    public void writeExternalTemplate(@NotNull PostfixTemplate template, @NotNull Element parentElement) {
        if (template instanceof F9JavaEditablePostfixTemplate) {

            Element templateTag = TemplateSettings.serializeTemplate(((F9JavaEditablePostfixTemplate) template).getLiveTemplate(), null,
                                                                     LazyKt.lazyOf(Collections.emptyMap()));
            parentElement.addContent(templateTag);
        }
    }
}
