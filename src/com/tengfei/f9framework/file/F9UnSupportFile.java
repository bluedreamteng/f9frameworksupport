package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.notification.F9Notifier;

class F9UnSupportFile extends F9File{
    public F9UnSupportFile(VirtualFile virtualFile, Project project) {
        super(virtualFile, project);
    }

    @Override
    public void copyToPatch(VirtualFile directory) {
        F9Notifier.notifyError(project,virtualFile.getPath() + ": 不支持此操作");
    }

    @Override
    public String getPatchDirRelativePath() {
        throw new UnsupportedOperationException();
    }
}
