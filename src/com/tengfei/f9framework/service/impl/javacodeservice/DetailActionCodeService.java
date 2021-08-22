package com.tengfei.f9framework.service.impl.javacodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.tengfei.f9framework.entity.PathConfig;
import com.tengfei.f9framework.entity.TableInfo;

/**
 * @author ztf
 */
public class DetailActionCodeService extends ActionCodeServiceBase {
    public DetailActionCodeService(Project project, TableInfo tableInfo, PathConfig pathConfig) {
        super(project, tableInfo, pathConfig);
    }

    @Override
    protected String getClassName() {
        return tableInfo.getDetailActionName();
    }

    @Override
    protected String buildActionTemplate() {
        String detailActionTemplate = "/**\n" +
                " * 数据表详情页面对应的后台\n" +
                " * \n" +
                " * @author idea\n" +
                " */\n" +
                "@com.epoint.basic.controller.RightRelation(%s.class)\n" +
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
                "  \tprivate %s dataBean=null;\n" +
                "  \n" +
                "    @Override\n" +
                "\tpublic void pageLoad() {\n" +
                "\t   \tString\tguid = getRequestParameter(\"guid\");\n" +
                "\t   \tdataBean = service.find(guid);\n" +
                "\t\t  if(dataBean==null){\n" +
                "\t\t      dataBean=new %s();\n" +
                "\t\t  }\n" +
                "    }\n" +
                "   \n" +
                "\tpublic %s getDataBean() {\n" +
                "\t\treturn dataBean;\n" +
                "\t}\n" +
                "}";
        return String.format(detailActionTemplate,
                tableInfo.getListActionName(),
                StringUtil.toLowerCase(tableInfo.getDetailActionName()),
                tableInfo.getDetailActionName(),
                tableInfo.getServiceInterfaceName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName(),
                tableInfo.getEntityName()
        );
    }
}
