package com.tengfei.f9framework.service.impl.htmlcodeservice;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.entity.ColumnInfo;
import com.tengfei.f9framework.entity.TableInfo;
import com.tengfei.f9framework.miniuicomponent.F9FormComponentStrategy;
import com.tengfei.f9framework.miniuicomponent.F9MiniUiComponentFactory;
import com.tengfei.f9framework.util.CollectionUtil;

import java.util.List;

public class ListHtmlCodeService extends HtmlCodeServiceBase {

    protected ListHtmlCodeService(Project project, TableInfo tableInfo, VirtualFile directory) {
        super(project, tableInfo, directory);
    }

    @Override
    protected String getFileName() {
        return tableInfo.getListHtmlName();
    }

    @Override
    protected String getHtmlToolbar() {
        return "<div class=\"fui-toolbar\">\n" +
                "    <div class=\"btn-group mr10\">\n" +
                "        <a class=\"mini-button\" onclick=\"openAdd\" id=\"btnAddRecord\">\n" +
                "            新增记录\n" +
                "        </a>\n" +
                "        <a id=\"btnDel\" class=\"mini-button\" onclick=\"deleteData\">\n" +
                "            删除选定\n" +
                "        </a>\n" +
                "    </div>\n" +
                "    <a class=\"mini-dataexport\" id=\"dataexport\" gridId=\"datagrid\" fileName=\"查询列表\"\n" +
                "       action=\"getExportModel\" extraId=\"fui-form\"></a>\n" +
                "</div>";
    }

    @Override
    protected String getHtmlFuiCondition() {
        List<List<ColumnInfo>> otherColumnInfos = CollectionUtil.splitList(tableInfo.getOtherColumn(), 2);
        F9MiniUiComponentFactory miniUiComponentFactory = F9MiniUiComponentFactory.getInstance(F9FormComponentStrategy.SEARCH_FORM);
        return "<div class=\"fui-condition\">\n" +
                "    <div class=\"fui-form\" id=\"fui-form\">\n" +
                "        <div id=\"cform\" role=\"form\">\n" +
                miniUiComponentFactory.createDivRows(otherColumnInfos) +
                "        </div>\n" +
                "    </div>\n" +
                "    <a role=\"searcher\" callback=\"searchData\"></a></div>";
    }

    @Override
    protected String getHtmlFuiContent() {
        String htmlFuiContentTemplate= "<div id=\"fuiContent\" class=\"fui-content\">\n" +
                "    <div id=\"datagrid\" class=\"mini-datagrid\" idField=\"%s\" action=\"getDataGridData\" sortOrder=\"desc\"\n" +
                "         showPager=\"true\" style=\"height: 100%%;\" allowResize=\"true\" multiSelect=\"true\" allowCellEdit=\"true\"\n" +
                "         allowCellSelect=\"true\" editNextOnEnterKey=\"true\" editNextRowCell=\"true\">\n" +
                "        <div property=\"columns\">\n" +
                "            <div type=\"checkcolumn\" width=\"40\"></div>\n" +
                "            <div type=\"indexcolumn\" width=\"40\" headerAlign=\"center\">序</div>\n" +
                F9MiniUiComponentFactory.getInstance().createDivFields(tableInfo.getOtherColumn())+
                "            <div width=\"50\" align=\"center\" headerAlign=\"center\" renderer=\"onEditRenderer\">\n" +
                "                修改\n" +
                "            </div>\n" +
                "            <div width=\"50\" align=\"center\" headerAlign=\"center\" renderer=\"onDetailRenderer\">\n" +
                "                查看\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        String primaryKeyName = "";
        //默认第一个主键
        if(!tableInfo.getPkColumn().isEmpty()){
            primaryKeyName = tableInfo.getPkColumn().get(0).getNameWithLowerCase();
        }
        return String.format(htmlFuiContentTemplate,primaryKeyName);
    }

    @Override
    protected String getHtmlJavaScript() {
        String htmlJavaScriptTemplate = "<script>\n" +
                "    // 初始化页面\n" +
                "    epoint.initPage('%s');\n" +
                "\n" +
                "    // 绘制行编辑按钮\n" +
                "    var onEditRenderer = function (e) {\n" +
                "        return epoint.renderCell(e, \"action-icon icon-edit\", \"openEdit\");\n" +
                "    };\n" +
                "\n" +
                "    // 绘制行查看按钮\n" +
                "    var onDetailRenderer = function (e) {\n" +
                "        return epoint.renderCell(e, \"action-icon icon-search\", \"openDetail\");\n" +
                "    };\n" +
                "\n" +
                "    // 弹出新增窗口\n" +
                "    function openAdd() {\n" +
                "        epoint.openDialog('新增记录', \"./%s\", searchData, {\n" +
                "            'width': 1000,\n" +
                "            'height': 550\n" +
                "        });\n" +
                "    }\n" +
                "\n" +
                "    // 弹出修改窗口\n" +
                "    function openEdit(id) {\n" +
                "        epoint.openDialog('修改记录', \"./%s?guid=\" + id, searchData, {\n" +
                "            'width': 1000,\n" +
                "            'height': 550\n" +
                "        });\n" +
                "    }\n" +
                "\n" +
                "    // 弹出明细窗口\n" +
                "    function openDetail(id) {\n" +
                "        epoint.openDialog('详细信息', \"./%s?guid=\" + id, {\n" +
                "            'width': 1000,\n" +
                "            'height': 550\n" +
                "        });\n" +
                "    }\n" +
                "\n" +
                "    // 删除数据\n" +
                "    function deleteSelect() {\n" +
                "        epoint.execute(\"deleteSelect\", '', callback);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    function callback(data) {\n" +
                "        if (data.msg) {\n" +
                "            epoint.alert(data.msg, '', searchData, 'info');\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    // 表格的搜索\n" +
                "    function searchData() {\n" +
                "        epoint.refresh(['datagrid', 'fui-form'], '', true);\n" +
                "    }\n" +
                "\n" +
                "    function deleteData() {\n" +
                "        if (mini.get('datagrid').getSelecteds().length == 0) {\n" +
                "            epoint.alert('请选择要删除的记录!', '', null, 'warning');\n" +
                "        } else {\n" +
                "            epoint.confirm('确认要删除吗?', '', deleteSelect);\n" +
                "        }\n" +
                "    }\n" +
                "</script>";
        return String.format(htmlJavaScriptTemplate,
                StringUtil.toLowerCase(tableInfo.getListActionName()),
                tableInfo.getAddHtmlName(),
                tableInfo.getEditHtmlName(),
                tableInfo.getDetailHtmlName()
        );
    }
}
