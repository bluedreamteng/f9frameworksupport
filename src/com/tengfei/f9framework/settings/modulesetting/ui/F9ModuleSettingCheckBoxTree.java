package com.tengfei.f9framework.settings.modulesetting.ui;

import com.intellij.codeInsight.template.postfix.settings.PostfixTemplateCheckedTreeNode;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.ui.tree.TreeUtil;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.List;

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
        myModel = (DefaultTreeModel)getModel();
        myRoot = (CheckedTreeNode)myModel.getRoot();
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
    }

    public void initTree(){
        List<F9StandardModuleSetting> standardModules = F9ProjectSetting.getInstance(project).standardModules;
        if(standardModules.isEmpty()) {
            return;
        }
        for (F9StandardModuleSetting f9StandardModuleSetting : standardModules) {
            F9StdModuleSettingNode stdModuleSettingNode = new F9StdModuleSettingNode(f9StandardModuleSetting);
            myRoot.add(stdModuleSettingNode);
            if(!f9StandardModuleSetting.getCustomizeModuleList().isEmpty()) {
                for (F9CustomizeModuleSetting customizeModuleSetting : f9StandardModuleSetting.getCustomizeModuleList()) {
                    stdModuleSettingNode.add(new F9CusModuleSettingNode(customizeModuleSetting));
                }
            }
        }
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
                CheckedTreeNode node = (CheckedTreeNode)value;

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

    }

    /*public void initTree(@NotNull MultiMap<PostfixTemplateProvider, PostfixTemplate> providerToTemplates) {
        myRoot.removeAllChildren();
        Map<String, Set<PostfixTemplateCheckedTreeNode>> languageToNodes = new HashMap<>();
        for (Map.Entry<PostfixTemplateProvider, Collection<PostfixTemplate>> entry : providerToTemplates.entrySet()) {
            PostfixTemplateProvider provider = entry.getKey();
            String languageId = getProvidersToLanguages().get(provider);
            Set<PostfixTemplateCheckedTreeNode> nodes = ContainerUtil.getOrCreate(languageToNodes, languageId, myNodesComparator);
            for (PostfixTemplate template : entry.getValue()) {


            }
        }
        for (Map.Entry<String, Set<PostfixTemplateCheckedTreeNode>> entry : languageToNodes.entrySet()) {
            DefaultMutableTreeNode languageNode = findOrCreateLanguageNode(entry.getKey());
            for (PostfixTemplateCheckedTreeNode node : entry.getValue()) {
                languageNode.add(new PostfixTemplateCheckedTreeNode(node.getTemplate(), node.getTemplateProvider(), false));
            }
        }

        myModel.nodeStructureChanged(myRoot);
        TreeUtil.expandAll(this);
    }*/
}
