package com.tengfei.f9framework.postcompletetemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.database.model.DasNamespace;
import com.intellij.database.model.DasTable;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbPsiFacade;
import com.intellij.database.psi.DbTable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Conditions;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlText;
import com.intellij.util.containers.JBIterable;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.DataSourceChooseDialog;
import com.tengfei.f9framework.util.EditorManage;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 列举出所有的setter方法
 * @author ztf
 */
public abstract class F9PostfixTemplateWithTable extends PostfixTemplate {


    protected F9PostfixTemplateWithTable(@NotNull String name, String example) {
        super(name, example);
    }

    @Override
    public boolean isApplicable(@NotNull PsiElement context, @NotNull Document copyDocument, int newOffset) {
        return context.getParent() instanceof XmlText;
    }

    @Override
    public void expand(@NotNull PsiElement context, @NotNull Editor editor) {
        String tableName = context.getText();
        if(StringUtil.isBlank(tableName)) {
            return;
        }
        //选择数据源对话框
        Project project = context.getProject();
        DbDataSource dbDataSource = chooseDataSource(project,editor.getComponent());
        if(dbDataSource == null) {
            return;
        }
        //根据表名查找表信息;
        DasTable dasTable = findDasTable(tableName, dbDataSource);
        if(dasTable == null) {
            return;
        }
        DbTable originalTableInfo = (DbTable)DbPsiFacade.getInstance(project).findElement(dasTable);
        assert originalTableInfo != null;
        TableInfo tableInfo = new TableInfo(originalTableInfo);
        EditorManage.getInstance(editor).removeExpression(context);
        final TemplateManager manager = TemplateManager.getInstance(project);
        final Template template = manager.createTemplate("", "", getTemplate(tableInfo));
        template.setToReformat(true);
        manager.startTemplate(editor, template);
    }

    @NotNull
    public abstract String getTemplate(@NotNull TableInfo tableInfo);


    @Nullable
    private DasTable findDasTable(@NotNull String tableName,@NotNull DbDataSource dbDataSource) {
        DasTable dasTable = null;
        List<DasTable> tables = getTables(dbDataSource);
        for (DasTable table : tables) {
            if(table.getName().equals(tableName)) {
                dasTable = table;
                break;
            }
        }
        return dasTable;
    }

    @NotNull
    private List<DasTable> getTables(@NotNull DbDataSource dataSource) {
        List<DasTable> result = new ArrayList<>();
        JBIterable<DasTable> dasTables = dataSource.getModel().traverser().expandAndSkip(Conditions.instanceOf(DasNamespace.class)).filter(DasTable.class);
        dasTables.forEach((dasTable -> {
            result.add(dasTable);
        }));
        return result;
    }


    @Nullable
    private DbDataSource chooseDataSource(@NotNull Project project, Component parent) {
        DataSourceChooseDialog dataSourceChooseDialog = DataSourceChooseDialog.getInstance(project, parent, "Choose DataSource");
        dataSourceChooseDialog.setModal(true);
        dataSourceChooseDialog.setSingleSelectionMode();
        dataSourceChooseDialog.show();
        List<DbDataSource> chosenElements = dataSourceChooseDialog.getChosenElements();
        if(CollectionUtils.isEmpty(chosenElements)) {
            return null;
        }
        return chosenElements.get(0);
    }
}