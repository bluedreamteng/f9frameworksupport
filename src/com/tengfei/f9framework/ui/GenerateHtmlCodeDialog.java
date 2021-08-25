package com.tengfei.f9framework.ui;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.service.TableCodeGenerateService;
import com.tengfei.f9framework.util.F9ChooseFileUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GenerateHtmlCodeDialog extends DialogWrapper {
    private final Project project;
    private final DbTable dbTable;
    private final TableCodeGenerateService codeGenerateService;
    private JPanel contentPane;
    private JCheckBox addHtml;
    private JCheckBox listHtml;
    private JCheckBox editHtml;
    private JCheckBox detailHtml;
    private JCheckBox all;

    public GenerateHtmlCodeDialog(Project project, DbTable dbTable) {
        super(project);
        setTitle("Generate Html Code");
        init();
        setSize(420,330);
        this.project = project;
        this.dbTable = dbTable;
        codeGenerateService = TableCodeGenerateService.getInstance(project);

        //my code
        all.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHtml.setSelected(all.isSelected());
                listHtml.setSelected(all.isSelected());
                editHtml.setSelected(all.isSelected());
                detailHtml.setSelected(all.isSelected());
            }
        });
    }

    public void open() {
        show();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if(!(addHtml.isSelected()  || listHtml.isSelected() || editHtml.isSelected() || detailHtml.isSelected())) {
            return new ValidationInfo("You should at least select one!");
        }

        return null;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        // add your code here
        TableInfo tableInfo = new TableInfo(dbTable);

        dispose();

        //choose a directory
        VirtualFile directory = F9ChooseFileUtil.chooseDirectory(project);
        if(directory == null) {
            //do nothing
            F9Notifier.notifyMessage(project, "未选择任何目录");
            return;
        }

        if(addHtml.isSelected()) {
            codeGenerateService.generateAddHtmlFile(tableInfo,directory);
            F9Notifier.notifyMessage(project, "add html 生成完毕");
        }

        if(listHtml.isSelected()) {
            codeGenerateService.generateListHtmlFile(tableInfo,directory);
            F9Notifier.notifyMessage(project, "list html 生成完毕");

        }

        if(editHtml.isSelected()) {
            codeGenerateService.generateEditHtmlFile(tableInfo,directory);
            F9Notifier.notifyMessage(project, "edit html 生成完毕");

        }

        if(detailHtml.isSelected()) {
            codeGenerateService.generateDetailHtmlFile(tableInfo,directory);
            F9Notifier.notifyMessage(project, "detail html 生成完毕");

        }
    }

    @Override
    public boolean isAutoAdjustable() {
        return false;
    }
}
