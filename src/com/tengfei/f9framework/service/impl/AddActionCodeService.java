package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class AddActionCodeService extends ActionCodeServiceBase {


    public AddActionCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
    }

    @Override
    protected String getClassName() {
        return tableInfo.getAddActionName();
    }

    @Override
    protected String buildActionTemplate() {
        String addActionTemplate = "/**\n" +
                " * 数据表新增页面对应的后台\n" +
                " *\n" +
                " * @author idea\n" +
                " */\n" +
                "@com.epoint.basic.controller.RightRelation(%s.class)\n" +
                "@org.springframework.web.bind.annotation.RestController(\"%s\")\n" +
                "@org.springframework.context.annotation.Scope(\"request\")\n" +
                "public class %s extends com.epoint.basic.controller.BaseController {\n" +
                "    @org.springframework.beans.factory.annotation.Autowired\n" +
                "    private %s service;\n" +
                "    /**\n" +
                "     * 数据表实体对象\n" +
                "     */\n" +
                "    private %s dataBean = null;\n" +
                "\n" +
                "    @Override\n" +
                "    public void pageLoad() {\n" +
                "        dataBean = new %s();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 保存并关闭\n" +
                "     */\n" +
                "    public void add() {\n" +
                "        dataBean.setRowguid(java.util.UUID.randomUUID().toString());\n" +
                "        dataBean.setOperatedate(new java.util.Date());\n" +
                "        dataBean.setOperateusername(userSession.getDisplayName());\n" +
                "        service.insert(dataBean);\n" +
                "        addCallbackParam(\"msg\", \"保存成功！\");\n" +
                "        dataBean = null;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 保存并新建\n" +
                "     */\n" +
                "    public void addNew() {\n" +
                "        add();\n" +
                "        dataBean = new %s();\n" +
                "    }\n" +
                "\n" +
                "    public %s getDataBean() {\n" +
                "        if (dataBean == null) {\n" +
                "            dataBean = new %s();\n" +
                "        }\n" +
                "        return dataBean;\n" +
                "    }\n" +
                "\n" +
                "    public void setDataBean(%s dataBean) {\n" +
                "        this.dataBean = dataBean;\n" +
                "    }\n" +
                "\n" +
                "}";
        return String.format(addActionTemplate,
                tableInfo.getListActionName(),
                StringUtil.toLowerCase(tableInfo.getAddActionName()),
                tableInfo.getAddActionName(),
                tableInfo.getServiceInterfaceName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName()
        );
    }


}
