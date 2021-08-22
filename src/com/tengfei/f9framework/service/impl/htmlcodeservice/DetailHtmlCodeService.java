package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.miniuicomponent.F9FormComponentStrategy;
import com.tengfei.f9framework.miniuicomponent.F9MiniUiComponentFactory;
import com.tengfei.f9framework.util.CollectionUtil;

import java.util.List;

public class DetailHtmlCodeService extends HtmlCodeServiceBase {
    protected DetailHtmlCodeService(Project project, TableInfo tableInfo, VirtualFile directory) {
        super(project, tableInfo, directory);
    }

    @Override
    protected String getFileName() {
        return tableInfo.getDetailHtmlName();
    }

    @Override
    protected String getHtmlToolbar() {
        return "<div class=\"fui-toolbar\">\n" +
                "        <div class=\"btn-group mr10\">\n" +
                "            <a class=\"mini-button\" onclick=\"epoint.closeDialog\">关闭</a>\n" +
                "        </div>\n" +
                "</div>";
    }

    @Override
    protected String getHtmlFuiCondition() {
        return "";
    }

    @Override
    protected String getHtmlFuiContent() {
        List<List<ColumnInfo>> otherColumnInfos = CollectionUtil.splitList(tableInfo.getOtherColumn(), 2);
        F9MiniUiComponentFactory miniUiComponentFactory = F9MiniUiComponentFactory.getInstance(F9FormComponentStrategy.DETAIL_FORM);
        return "<div class=\"fui-content\">\n" +
                "    <div id=\"fui-form\" class=\"fui-form\" style=\"width:90%\">\n" +
                "        <div role=\"form\">\n" +
                miniUiComponentFactory.createDivRows(otherColumnInfos) +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
    }

    @Override
    protected String getHtmlJavaScript() {
        return "<script>\n" +
                "    // 初始化页面\n" +
                "    epoint.initPage('testcolumninfodetailaction');\n" +
                "</script>";
    }
}
