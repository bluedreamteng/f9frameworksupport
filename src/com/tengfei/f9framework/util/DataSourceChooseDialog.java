package com.tengfei.f9framework.util;

import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbPsiFacade;
import com.intellij.ide.util.ChooseElementsDialog;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author ztf
 */
public class DataSourceChooseDialog extends ChooseElementsDialog<DbDataSource> {

    public static DataSourceChooseDialog getInstance(Project project,Component parent,String title) {
        List<DbDataSource> dataSourceList = DbPsiFacade.getInstance(project).getDataSources();
        return new DataSourceChooseDialog(parent,dataSourceList,title);
    }

    private DataSourceChooseDialog(Component parent, List<? extends DbDataSource> items, String title) {
        super(parent, items, title);
    }

    @Override
    protected String getItemText(DbDataSource item) {
        return item.getName();
    }

    public void setSingleSelectionMode() {
        myChooser.setSingleSelectionMode();
    }


    @Nullable
    @Override
    protected Icon getItemIcon(DbDataSource item) {
        return item.getIcon();
    }
}
