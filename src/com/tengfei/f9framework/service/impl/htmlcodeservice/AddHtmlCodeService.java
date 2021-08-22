package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.TableInfo;

public class AddHtmlCodeService extends HtmlEditableCodeFormService {
    public AddHtmlCodeService(Project project, TableInfo tableInfo, VirtualFile directory) {
        super(project, tableInfo, directory);
    }

    @Override
    public String getFileName() {
        return tableInfo.getAddHtmlName();
    }

    @Override
    public String getHtmlToolbar() {
        return "<div class=\"fui-toolbar\">\n" +
                "    <div class=\"btn-group mr10\">\n" +
                "        <a class=\"mini-button\" id=\"addNew\" onclick=\"saveAndNew\">保存并新建</a>\n" +
                "        <a class=\"mini-button\" id=\"addClose\" onclick=\"saveAndClose\">保存并关闭</a>\n" +
                "        <a class=\"mini-button\" onclick=\"epoint.closeDialog\">关闭</a>\n" +
                "    </div>\n" +
                "</div>";
    }

    @Override
    public String getHtmlJavaScript() {
        String htmlJavaScriptTemplate =  "<script>\n" +
                "    // 初始化页面\n" +
                "    epoint.initPage('%s');\n" +
                "\n" +
                "    function saveAndNew() {\n" +
                "        if (epoint.validate()) {\n" +
                "            epoint.execute('addNew', 'fui-form', newCallback);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    function saveAndClose() {\n" +
                "        if (epoint.validate()) {\n" +
                "            epoint.execute('add', 'fui-form', closeCallback);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    // 新增操作的回调\n" +
                "    function newCallback(data) {\n" +
                "        if (data.msg) {\n" +
                "            epoint.alert(data.msg, '', null, 'info');\n" +
                "            // 清空表单\n" +
                "            epoint.clear('fui-form');\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    // 关闭操作的回调\n" +
                "    function closeCallback(data) {\n" +
                "        if (data.msg) {\n" +
                "            epoint.alertAndClose(data.msg, '', null, null, 'info');\n" +
                "        }\n" +
                "    }\n" +
                "</script>";
        return String.format(htmlJavaScriptTemplate, StringUtil.toLowerCase(tableInfo.getAddActionName()));
    }
}
