package com.tengfei.f9framework.webbrowser;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;

import com.tengfei.f9framework.setting.F9SettingsState;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9HtmlUrlFactory {
    F9SettingsState settingsState = null;

    private F9HtmlUrlFactory() {

    }

    public static F9HtmlUrlFactory getInstance() {
        return new F9HtmlUrlFactory();
    }

    public Url createF9HtmlUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) {
        settingsState = F9SettingsState.getInstance(request.getProject());
        String filePath = file.getPath();
        if (!isValidPath(filePath)) {
            throw new UnvalidPathException("unvalid path");
        }
        return new F9HtmlUrl("http", null, getRootPath(filePath)).resolve(getFileRelativePath(filePath));
    }

    private String getRootPath(String filePath) {
        String rootPath = "";

        if (filePath.contains(settingsState.glProjectName) || filePath.contains(settingsState.glProjectPagePath)) {
            rootPath = settingsState.glDeployHost + "/" + settingsState.glProjectName;
        }

        else if (filePath.contains(settingsState.qyProjectName) || filePath.contains(settingsState.qyProjectPagePath)) {
            rootPath = settingsState.qyDeployHost + "/" + settingsState.qyProjectName;
        }

        return rootPath;
    }

    private boolean isValidPath(String filePath) {
        if(!filePath.contains(settingsState.webRootPath)){
            return false;
        }
        return filePath.contains(settingsState.glProjectName) || filePath.contains(settingsState.glProjectPagePath) ||
                filePath.contains(settingsState.qyProjectName) || filePath.contains(settingsState.qyProjectPagePath);
    }


    private String getFileRelativePath(String filePath) {
        String result;
        if (filePath.contains(settingsState.glProjectPagePath)) {
            result = filePath.split(settingsState.glProjectPagePath)[1];
            result = result.substring(1, result.indexOf('.'));
        }
        else if (filePath.contains(settingsState.qyProjectPagePath)) {
            result = filePath.split(settingsState.qyProjectPagePath)[1];
            result = result.substring(1, result.indexOf('.'));
        }
        else {
            result = filePath.split(settingsState.webRootPath)[1];
            result = result.substring(1, result.indexOf('.'));
        }
        return result;
    }


}
