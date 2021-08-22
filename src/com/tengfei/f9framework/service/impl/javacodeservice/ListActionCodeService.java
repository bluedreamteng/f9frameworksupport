package com.tengfei.f9framework.service.impl.javacodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class ListActionCodeService extends ActionCodeServiceBase {
    public ListActionCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
    }

    @Override
    protected String getClassName() {
        return tableInfo.getListActionName();
    }

    @Override
    protected String buildActionTemplate() {
        String actionTemplate = "\n" +
                "\n" +
                "/**\n" +
                " * 数据表list页面对应的后台\n" +
                " * \n" +
                " * @author idea\n" +
                " */\n" +
                "@org.springframework.web.bind.annotation.RestController(\"%s\")\n" +
                "@org.springframework.context.annotation.Scope(\"request\")\n" +
                "public class %s  extends com.epoint.basic.controller.BaseController\n" +
                "{\n" +
                "\t@org.springframework.beans.factory.annotation.Autowired\n" +
                "    private %s service;\n" +
                "    \n" +
                "    /**\n" +
                "     * 数据表实体对象\n" +
                "     */\n" +
                "    private %s dataBean;\n" +
                "  \n" +
                "    /**\n" +
                "     * 表格控件model\n" +
                "     */\n" +
                "    private DataGridModel<%s> model;\n" +
                "  \t\n" +
                "    /**\n" +
                "     * 导出模型\n" +
                "     */\n" +
                "    private com.epoint.basic.faces.export.ExportModel exportModel;\n" +
                "    \n" +
                "    \n" +
                "\n" +
                "    @Override\n" +
                "    public void pageLoad() {\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 删除选定\n" +
                "     * \n" +
                "     */\n" +
                "    public void deleteSelect() {\n" +
                "        java.util.List<String> select = getDataGridData().getSelectKeys();\n" +
                "        for (String sel : select) {\n" +
                "             service.deleteByGuid(sel);\n" +
                "        }\n" +
                "        addCallbackParam(\"msg\", \"成功删除！\");\n" +
                "    }\n" +
                "    \n" +
                "    public DataGridModel<%s> getDataGridData() {\n" +
                "        // 获得表格对象\n" +
                "        if (model == null) {\n" +
                "            model = new DataGridModel<%s>()\n" +
                "            {\n" +
                "\n" +
                "                @Override\n" +
                "                public List<%s> fetchData(int first, int pageSize, String sortField, String sortOrder) {\n" +
                "                    // 获取where条件Map集合\n" +
                "                    java.util.Map<String, Object> conditionMap = com.epoint.frame.service.metadata.mis.util.ListGenerator.getSearchMap(getRequestContext().getComponents(),\n" +
                "                            sortField, sortOrder);\n" +
                "                    PageData<%s> pageData = service.paginatorList(conditionMap, first, pageSize);\n" +
                "                    this.setRowCount(pageData.getRowCount());\n" +
                "                    return pageData.getList();\n" +
                "                }\n" +
                "\n" +
                "            };\n" +
                "        }\n" +
                "        return model;\n" +
                "    }\n" +
                " \n" +
                "    public %s getDataBean() {\n" +
                "    \tif(dataBean == null){\n" +
                "    \t\tdataBean = new %s();\n" +
                "    \t}\n" +
                "        return dataBean;\n" +
                "    }\n" +
                "\n" +
                "    public void setDataBean(%s dataBean) {\n" +
                "        this.dataBean = dataBean;\n" +
                "    }\n" +
                "    \n" +
                "    public com.epoint.basic.faces.export.ExportModel getExportModel() {\n" +
                "        if (exportModel == null) {\n" +
                "            exportModel = new com.epoint.basic.faces.export.ExportModel(\"\", \"\");\n" +
                "        }\n" +
                "        return exportModel;\n" +
                "    }\n" +
                "    \n" +
                "\t\n" +
                "}\n";
        return String.format(actionTemplate,
                StringUtil.toLowerCase(
                        tableInfo.getListActionName()),
                tableInfo.getListActionName(),
                tableInfo.getServiceInterfaceName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName()
        );
    }
}
