package com.tengfei.f9framework.action;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiElement;
import com.tengfei.f9framework.ui.GenerateHtmlCodeDialog;

public class GenerateTableHtmlCodeAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PsiElement data = e.getData(CommonDataKeys.PSI_ELEMENT);
        if(!(data instanceof DbTable)){
            return;
        }
        DbTable selectTable = (DbTable)data;
        GenerateHtmlCodeDialog generateHtmlCodeDialog = new GenerateHtmlCodeDialog(e.getProject(),selectTable);
        generateHtmlCodeDialog.open();
    }
}