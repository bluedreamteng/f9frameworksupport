package com.tengfei.f9framework.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.file.F9FileFactory;
import com.tengfei.f9framework.file.F9WebappFile;
import com.tengfei.f9framework.notification.F9Notifier;

import java.awt.datatransfer.StringSelection;
import java.util.Objects;

public class CopyWebRelativePathAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if(virtualFile == null) {
            F9Notifier.notifyWarning(e.getProject(),"未获取到虚拟文件");
            return;
        }
        F9WebappFile f9WebAppFile;
        try {
            f9WebAppFile = F9FileFactory.getInstance(e.getProject()).createF9WebAppFile(virtualFile, Objects.requireNonNull(e.getProject()));
        } catch (Exception exception) {
            F9Notifier.notifyError(e.getProject(),"不支持的文件类型");
            return;
        }
        String webRelativePath = f9WebAppFile.getWebRelativePath().replaceAll("\\\\","/");
        CopyPasteManager.getInstance().setContents(new StringSelection(webRelativePath));
        F9Notifier.notifyMessage(e.getProject(),"web相对路径已复制进剪切板");
        //http://localhost:8011/smart-site/frame/basedata/attachconfiginfo/djgmainstepadd
    }
}
