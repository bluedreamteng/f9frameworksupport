package com.tengfei.f9framework.util;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * 编辑器管理
 * @author ztf
 */
public class EditorManage {
    private final Editor editor;

    private EditorManage(Editor editor) {
        this.editor = editor;
    }

    public static EditorManage getInstance(Editor editor) {
        return new EditorManage(editor);
    }

    public void removeExpression(@NotNull PsiElement expression) {
        Document document = editor.getDocument();
        document.deleteString(expression.getTextRange().getStartOffset(), expression.getTextRange().getEndOffset());
    }
}
