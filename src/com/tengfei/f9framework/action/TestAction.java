package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class TestAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile[] data = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        for(VirtualFile file:data) {
            Module moduleForFile = ModuleUtil.findModuleForFile(file, e.getProject());
        }
    }
}
