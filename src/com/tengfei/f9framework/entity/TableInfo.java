package com.tengfei.f9framework.entity;


import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.util.containers.JBIterable;
import com.tengfei.f9framework.util.NameUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 表信息
 *
 * @author ZTF
 */
public class TableInfo {
    /**
     * 原始对象
     */
    private DbTable original;

    /**
     * 表名（首字母大写）
     */
    private String name;

    /**
     * 注释
     */
    private String comment;
    /**
     * 所有列
     */
    private List<ColumnInfo> fullColumn;
    /**
     * 主键列
     */
    private List<ColumnInfo> pkColumn;
    /**
     * 其他列
     */
    private List<ColumnInfo> otherColumn;

    public TableInfo(DbTable original) {
        if(original == null) {
            throw new RuntimeException("表信息为空");
        }
        this.original = original;
        // 设置原属对象
        setOriginal(original);
        // 设置类名
        setName(NameUtils.toCamelCaseWithFirstUpper(original.getName()));
        // 设置注释
        setComment(original.getComment());
        // 设置所有列
        setFullColumn(new ArrayList<>());
        // 设置主键列
        setPkColumn(new ArrayList<>());
        // 设置其他列
        setOtherColumn(new ArrayList<>());
        // 处理所有列
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(original);
        for (DasColumn column : columns) {
            ColumnInfo columnInfo = new ColumnInfo();
            // 原始列对象
            columnInfo.setObj(column);
            // 列类型
            columnInfo.setType(TypeMapper.getJavaTypeBySqlType(column.getDataType().getSpecification()));
            // 列名
           columnInfo.setName(NameUtils.toCamelCaseWithFirstLower(column.getName()));

           columnInfo.setLength(column.getDataType().size);
            // 列注释
            columnInfo.setComment(column.getComment());
            // 扩展项
            columnInfo.setExt(new LinkedHashMap<>());
            // 添加到全部列
            getFullColumn().add(columnInfo);
            // 主键列添加到主键列，否则添加到其他列
            if (DasUtil.isPrimary(column)) {
                getPkColumn().add(columnInfo);
            } else {
                getOtherColumn().add(columnInfo);
            }
        }
    }

    public String getEntityName() {
        return name;
    }

    public String getServiceInterfaceName() {
        return "I"+name+"Service";
    }

    public String getServiceImplName() {
        return name+"Service"+"Impl";
    }

    public String getServiceName() {
        return name+"Service";
    }

    public DbTable getOriginal() {
        return original;
    }

    public void setOriginal(DbTable original) {
        this.original = original;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ColumnInfo> getFullColumn() {
        return fullColumn;
    }

    public void setFullColumn(List<ColumnInfo> fullColumn) {
        this.fullColumn = fullColumn;
    }

    public List<ColumnInfo> getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(List<ColumnInfo> pkColumn) {
        this.pkColumn = pkColumn;
    }

    public List<ColumnInfo> getOtherColumn() {
        return otherColumn;
    }

    public void setOtherColumn(List<ColumnInfo> otherColumn) {
        this.otherColumn = otherColumn;
    }
}
