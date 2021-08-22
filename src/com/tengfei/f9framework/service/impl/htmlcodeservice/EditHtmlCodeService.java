package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.TableInfo;

public class EditHtmlCodeService extends HtmlEditableCodeFormService {
    public EditHtmlCodeService(Project project, TableInfo tableInfo, VirtualFile directory) {
        super(project, tableInfo, directory);
    }

    @Override
    protected String getFileName() {
        return tableInfo.getEditHtmlName();
    }

    @Override
    protected String getHtmlToolbar() {
        return "<div class=\"fui-toolbar\">\n" +
                "        <div class=\"btn-group mr10\">\n" +
                "            <a class=\"mini-button\" id=\"save\" onclick=\"saveModify\">保存并关闭</a>\n" +
                "            <a class=\"mini-button\" onclick=\"epoint.closeDialog\">取消修改</a>\n" +
                "        </div>\n" +
                "</div>";
    }

    @Override
    protected String getHtmlJavaScript() {
        String htmlJavaScriptTemplate =  "<script>\n" +
                "    \t// 初始化页面\n" +
                "    \tepoint.initPage('%s');\n" +
                "    \t\n" +
                "    \tfunction saveModify() {\n" +
                "\t\t    if(epoint.validate()){\n" +
                "\t\t        epoint.execute('save','fui-form',closeCallback);\n" +
                "\t\t    }\n" +
                "   \t \t}\n" +
                "   \t \t\n" +
                "\t    // 关闭操作的回调\n" +
                "\t    function closeCallback(data) {\n" +
                "\t        if (data.msg) {\n" +
                "\t            epoint.alertAndClose(data.msg, '', null, null, 'info');\n" +
                "\t        }\n" +
                "\t    }\n" +
                "</script>";
        return String.format(htmlJavaScriptTemplate, StringUtil.toLowerCase(tableInfo.getEditActionName()));
    }
}
