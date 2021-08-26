package com.tengfei.f9framework.webbrowser;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import com.tengfei.f9framework.file.F9FileFactory;
import com.tengfei.f9framework.file.F9WebappFile;
import com.tengfei.f9framework.projectsetting.F9SettingsState;
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
        F9WebappFile f9WebAppFile = F9FileFactory.getInstance(request.getProject()).createF9WebAppFile(file, request.getProject());
        String deployWebPath = f9WebAppFile.getDeployWebPath();
        if (deployWebPath.contains(".")) {
            deployWebPath = deployWebPath.substring(0, deployWebPath.lastIndexOf("."));
        }
        return new F9HtmlUrl("http", null, deployWebPath);
    }

}
