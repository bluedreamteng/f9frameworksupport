package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.codeInsight.template.postfix.settings.PostfixTemplateCheckedTreeNode;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.*;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.ui.tree.TreeUtil;
import com.tengfei.f9framework.notification.F9Notifier;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Set;

/**
 * @author ztf
 */
public class F9ModuleSettingCheckBoxTree extends CheckboxTree implements Disposable {

    @NotNull
    private final CheckedTreeNode myRoot;
    @NotNull
    private final DefaultTreeModel myModel;

    private final Project project;

    public F9ModuleSettingCheckBoxTree(@NotNull Project project) {
        super(getRenderer(), new CheckedTreeNode());
        this.project = project;
        myModel = (DefaultTreeModel) getModel();
        myRoot = (CheckedTreeNode) myModel.getRoot();
        setRootVisible(false);
        setShowsRootHandles(true);
        initTree();
        TreeSelectionListener selectionListener = new TreeSelectionListener() {
            @Override
            public void valueChanged(@NotNull TreeSelectionEvent event) {
                selectionChanged();
            }
        };
        getSelectionModel().addTreeSelectionListener(selectionListener);
        Disposer.register(this, () -> getSelectionModel().removeTreeSelectionListener(selectionListener));
        DoubleClickListener doubleClickListener = new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(MouseEvent event) {
                clearSelection();
                return true;
            }
        };
        doubleClickListener.installOn(this);
        Disposer.register(this, () -> doubleClickListener.uninstall(this));
    }

    public void initTree() {
        myRoot.removeAllChildren();
        Set<F9StandardModuleSetting> standardModules = F9ProjectSetting.getInstance(project).standardModules;
        if (!standardModules.isEmpty()) {
            for (F9StandardModuleSetting f9StandardModuleSetting : standardModules) {
                F9StdModuleSettingNode stdModuleSettingNode = new F9StdModuleSettingNode(f9StandardModuleSetting);
                myRoot.add(stdModuleSettingNode);
                if (!f9StandardModuleSetting.getCustomizeModuleList().isEmpty()) {
                    for (F9CustomizeModuleSetting customizeModuleSetting : f9StandardModuleSetting.getCustomizeModuleList()) {
                        stdModuleSettingNode.add(new F9CusModuleSettingNode(customizeModuleSetting));
                    }
                }
            }
        }
        myModel.reload();
        TreeUtil.expandAll(this);
    }


    @Override
    public void dispose() {
        UIUtil.dispose(this);
    }

    @NotNull
    private static CheckboxTreeCellRenderer getRenderer() {
        return new CheckboxTreeCellRenderer() {
            @Override
            public void customizeRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (!(value instanceof CheckedTreeNode)) {
                    return;
                }
                CheckedTreeNode node = (CheckedTreeNode) value;

                Color background = UIUtil.getTreeBackground(selected, true);
                PostfixTemplateCheckedTreeNode templateNode = ObjectUtils.tryCast(node, PostfixTemplateCheckedTreeNode.class);
                SimpleTextAttributes attributes;
                if (templateNode != null) {
                    Color fgColor = templateNode.isChanged() || templateNode.isNew() ? JBColor.BLUE : null;
                    attributes = new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, fgColor);
                }
                else {
                    attributes = SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES;
                }
                getTextRenderer().append(StringUtil.notNullize(value.toString()),
                        new SimpleTextAttributes(background, attributes.getFgColor(), JBColor.RED, attributes.getStyle()));

                if (templateNode != null) {
                    String example = templateNode.getTemplate().getExample();
                    if (StringUtil.isNotEmpty(example)) {
                        getTextRenderer().append("  " + example, new SimpleTextAttributes(SimpleTextAttributes.STYLE_SMALLER, JBColor.GRAY), false);
                    }
                }
            }
        };
    }

    protected void selectionChanged() {
        //determine by subclass
    }


    @Nullable
    public F9ModuleSetting getSelectedModuleSetting() {
        TreePath path = getSelectionModel().getSelectionPath();
        if (path == null) {
            return null;
        }
        Object lastPathComponent = path.getLastPathComponent();
        if (lastPathComponent instanceof F9StdModuleSettingNode) {
            return ((F9StdModuleSettingNode) lastPathComponent).getStandardModuleSetting();
        }
        else if (lastPathComponent instanceof F9CusModuleSettingNode) {
            return ((F9CusModuleSettingNode) lastPathComponent).getCustomizeModuleSetting();
        }
        return null;
    }

    @Nullable
    public F9ModuleSettingNode getSelectedModuleSettingNode() {
        TreePath path = getSelectionModel().getSelectionPath();
        if (path == null) {
            return null;
        }
        return (F9ModuleSettingNode) path.getLastPathComponent();
    }


    @Nullable
    public Object getParentSelectedNode() {
        TreePath path = getSelectionModel().getSelectionPath().getParentPath();
        if (path == null) {
            return null;
        }
        return path.getLastPathComponent();
    }

    public void addModuleSetting() {
        //获取选中节点
        F9ModuleSettingNode selectedModuleSettingNode = getSelectedModuleSettingNode();

        if (selectedModuleSettingNode == null) {
            //增加标版模块
            F9StdModuleFormDialog addStandardModuleSettingDialog = new F9StdModuleFormDialog(project, "Add Standard Module Setting");
            addStandardModuleSettingDialog.open();
            initTree();
        }
        else if (selectedModuleSettingNode instanceof F9StdModuleSettingNode) {
            //增加该标版的个性化模块

        }
        else {
            //do nothing
        }
    }

    public void removeModuleSetting() {
        F9ModuleSetting selectedModuleSetting = getSelectedModuleSetting();
        if (selectedModuleSetting == null) {
            return;
        }
        if (selectedModuleSetting instanceof F9StandardModuleSetting) {
            F9StandardModuleSetting stdModuleSetting = (F9StandardModuleSetting) selectedModuleSetting;
            boolean isSuccess = F9ProjectSetting.getInstance(project).removeStdModuleSetting(stdModuleSetting);
            if (isSuccess) {
                F9Notifier.notifyMessage(project, "删除成功");
                initTree();
            }
            else {
                F9Notifier.notifyMessage(project, "删除失败");
            }
        }
        else if (selectedModuleSetting instanceof F9CustomizeModuleSetting) {
            F9CustomizeModuleSetting cusModuleSetting = (F9CustomizeModuleSetting) selectedModuleSetting;
            Object parentSelectedNode = getParentSelectedNode();
            if (!(parentSelectedNode instanceof F9StdModuleSettingNode)) {
                return;
            }
            F9StdModuleSettingNode parent = (F9StdModuleSettingNode) parentSelectedNode;
            boolean isSuccess = F9ProjectSetting.getInstance(project).removeCusModuleSetting(parent.getStandardModuleSetting(), cusModuleSetting);
            if (isSuccess) {
                F9Notifier.notifyMessage(project, "删除成功");
                initTree();
            }
            else {
                F9Notifier.notifyMessage(project, "删除失败");
            }
        }
    }
}
