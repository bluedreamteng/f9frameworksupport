package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.file.F9File;
import com.tengfei.f9framework.file.F9FileFactory;
import com.tengfei.f9framework.notification.F9Notifier;
import org.jetbrains.annotations.NotNull;

public class TestAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            throw new RuntimeException("project is null");
        }
        VirtualFile[] data = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if(data == null || data.length == 0) {
            F9Notifier.notifyWarning(project,"请选择文件");
            return;
        }
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(false,true,false,false,false,false);
        VirtualFile directory = FileChooser.chooseFile(fileChooserDescriptor, project, null);
        for (VirtualFile file : data) {
            if (moduleForFile != null) {
                F9File file1 = F9FileFactory.getInstance().createFile(file, project);
                file1.copyToPatch(directory);
            }
            else {
                F9Notifier.notifyError(project, file.getPath() + "不属于任何模块");
            }
        }


    }

    private void copyFileToTargetDirectory(@NotNull VirtualFile file,@NotNull VirtualFile directory) {
        Module moduleForFile = ModuleUtil.findModuleForFile(file, file.getProject());

        if (moduleForFile != null) {

        }
        else {
            F9Notifier.notifyError(project, file.getPath() + "不属于任何模块");
        }
    }


}
