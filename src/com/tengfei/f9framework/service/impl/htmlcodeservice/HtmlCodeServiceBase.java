package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.util.F9FileFacade;

public abstract class HtmlCodeServiceBase {

    public static final String ADD_HTML = "0";
    public static final String LIST_HTML = "1";
    public static final String EDIT_HTML = "2";
    public static final String Detail_HTML = "3";

    protected final Project project;
    private final VirtualFile directory;
    protected TableInfo tableInfo;

    public static HtmlCodeServiceBase getInstance(Project project, TableInfo tableInfo, VirtualFile directory, String type) {
        switch (type) {
            case ADD_HTML:
                return new AddHtmlCodeService(project, tableInfo, directory);
            case EDIT_HTML:
                return new EditHtmlCodeService(project, tableInfo, directory);
            case LIST_HTML:
                return new ListHtmlCodeService(project, tableInfo, directory);
            case Detail_HTML:
                return new DetailHtmlCodeService(project, tableInfo, directory);
            default:
                throw new RuntimeException("can't support this type");
        }
    }


    protected HtmlCodeServiceBase(Project project, TableInfo tableInfo, VirtualFile directory) {
        this.project = project;
        this.directory = directory;
        this.tableInfo = tableInfo;
    }

    public void generateHtmlCode() {
        F9FileFacade.getInstance(project).createHtmlFile(getFileName(), getHtmlText(), directory);
    }

    /**
     * get File Name
     *
     * @return fileName
     */
    protected abstract String getFileName();

    /**
     * get html text
     *
     * @return html text
     */
    protected String getHtmlText() {
        String htmlTemplate = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "%s" +
                "\n" +
                "<body>\n" +
                "<!-- 必须有，加载时的loading效果 -->\n" +
                "<div class=\"page-loading\"></div>\n" +
                "<!-- toolbar区域 -->\n" +
                "%s" +
                "\n" +
                "<!-- 条件区域 -->\n" +
                "%s" +
                "\n" +
                "<!-- 内容区域 -->\n" +
                "%s" +
                "\n" +
                "<!-- 请修改相对路径 -->\n" +
                "<script src=\"../../../../../rest/resource/jsboot\"></script>\n" +
                "%s" +
                "</body>\n" +
                "</html>";
        return String.format(htmlTemplate, getHtmlHead(), getHtmlToolbar(), getHtmlFuiCondition(), getHtmlFuiContent(), getHtmlJavaScript());
    }

    protected String getHtmlHead() {
        String htmlHeadText = "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n" +
                "    <title>%s添加</title>\n" +
                "    <!-- 请修改相对路径 -->\n" +
                "    <script src=\"../../../../../frame/fui/js/cssboot.js\"></script>\n" +
                "</head>\n";
        return String.format(htmlHeadText, tableInfo.getComment());
    }

    protected abstract String getHtmlToolbar();

    protected abstract String getHtmlFuiCondition();

    protected abstract String getHtmlFuiContent();

    protected abstract String getHtmlJavaScript();


}
