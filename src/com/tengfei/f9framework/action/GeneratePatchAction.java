package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.file.F9File;
import com.tengfei.f9framework.file.F9FileFactory;
import com.tengfei.f9framework.notification.F9Notifier;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author ztf
 */
public class GeneratePatchAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            throw new RuntimeException("project is null");
        }
        VirtualFile[] data = CommonDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if (data == null || data.length == 0) {
            F9Notifier.notifyWarning(project, "请选择文件");
            return;
        }
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        VirtualFile directory = FileChooser.chooseFile(fileChooserDescriptor, project, null);
        if (directory == null) {
            F9Notifier.notifyWarning(project, "请选择目录");
            return;
        }

        WriteCommandAction.runWriteCommandAction(project, () -> {
            F9FileFactory fileFactory = F9FileFactory.getInstance(project);
            for (VirtualFile file : data) {
                F9File f9file;
                try {
                    f9file = fileFactory.createF9File(file, project);
                }
                catch (Exception exception) {
                    F9Notifier.notifyWarning(project,String.format("文件：%s 不支持此操作！",file.getPresentableUrl()));
                    continue;
                }
                try {
                    f9file.copyToPatch(directory);
                } catch (IOException ioException) {
                    F9Notifier.notifyWarning(project,String.format("文件：%s 未能成功复制！" + ioException.getMessage(),file.getPresentableUrl()));
                }
            }
            F9Notifier.notifyMessage(project, "补丁包制作完成");
        });


    }


}

