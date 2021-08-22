package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.miniuicomponent.F9FormComponentStrategy;
import com.tengfei.f9framework.miniuicomponent.F9MiniUiComponentFactory;
import com.tengfei.f9framework.util.CollectionUtil;

import java.util.List;

/**
 * 可编辑表单
 *
 * @author ztf
 */
public abstract class HtmlEditableCodeFormService extends HtmlCodeServiceBase {
    public HtmlEditableCodeFormService(Project project, TableInfo tableInfo, VirtualFile directory) {
        super(project, tableInfo, directory);
    }

    @Override
    protected abstract String getFileName();

    @Override
    protected abstract String getHtmlToolbar();

    @Override
    protected String getHtmlFuiCondition() {
        return "";
    }

    @Override
    protected String getHtmlFuiContent() {
        //build fuiContent
        List<List<ColumnInfo>> otherColumnInfos = CollectionUtil.splitList(tableInfo.getOtherColumn(), 2);
        F9MiniUiComponentFactory miniUiComponentFactory = F9MiniUiComponentFactory.getInstance(F9FormComponentStrategy.EDIT_FORM);
        return "<div class=\"fui-content\">\n" +
                "    <div id=\"fui-form\" class=\"fui-form\" style=\"width:90%\">\n" +
                "        <div role=\"form\">\n" +
                miniUiComponentFactory.createDivRows(otherColumnInfos) +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
    }

    protected abstract String getHtmlJavaScript();

}
