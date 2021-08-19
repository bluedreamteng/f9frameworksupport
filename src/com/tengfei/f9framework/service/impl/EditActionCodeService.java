package com.tengfei.f9framework.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class EditActionCodeService extends ActionCodeServiceBase {
    public EditActionCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
    }

    @Override
    protected String getClassName() {
        return tableInfo.getEditActionName();
    }

    @Override
    protected String buildActionTemplate() {
        String editActionTemplate = "/**\n" +
                " * 数据表修改页面对应的后台\n" +
                " *\n" +
                " * @author idea\n" +
                " */\n" +
                "@com.epoint.basic.controller.RightRelation(%s.class)\n" +
                "@org.springframework.web.bind.annotation.RestController(\"%s\")\n" +
                "@org.springframework.context.annotation.Scope(\"request\")\n" +
                "public class %s extends com.epoint.basic.controller.BaseController {\n" +
                "\n" +
                "    @org.springframework.beans.factory.annotation.Autowired\n" +
                "    private %s service;\n" +
                "\n" +
                "    /**\n" +
                "     * 数据表实体对象\n" +
                "     */\n" +
                "    private %s dataBean = null;\n" +
                "\n" +
                "\n" +
                "    public void pageLoad() {\n" +
                "        String guid = getRequestParameter(\"guid\");\n" +
                "        dataBean = service.find(guid);\n" +
                "        if (dataBean == null) {\n" +
                "            dataBean = new %s();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 保存修改\n" +
                "     */\n" +
                "    public void save() {\n" +
                "        dataBean.setOperatedate(new java.util.Date());\n" +
                "        service.update(dataBean);\n" +
                "        addCallbackParam(\"msg\", \"修改成功！\");\n" +
                "    }\n" +
                "\n" +
                "    public %s getDataBean() {\n" +
                "        return dataBean;\n" +
                "    }\n" +
                "\n" +
                "    public void setDataBean(%s dataBean) {\n" +
                "        this.dataBean = dataBean;\n" +
                "    }\n" +
                "\n" +
                "}";
        return String.format(editActionTemplate,
                tableInfo.getListActionName(),
                StringUtil.toLowerCase(tableInfo.getEditActionName()),
                tableInfo.getEditActionName(),
                tableInfo.getServiceInterfaceName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName()
        );
    }
}
