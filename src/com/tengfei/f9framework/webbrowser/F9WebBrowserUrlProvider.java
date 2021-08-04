package com.tengfei.f9framework.webbrowser;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import com.tengfei.f9framework.module.setting.F9SettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;

/**
 * @author ztf
 */
public class F9WebBrowserUrlProvider extends BuiltInWebBrowserUrlProvider {


    @Nullable
    @Override
    protected Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) {
        F9SettingsState f9SettingsState = F9SettingsState.getInstance(request.getProject());
        if (f9SettingsState.enableChromeSupport) {
            return F9HtmlUrlFactory.getInstance().createF9HtmlUrl(request, file);
        }
        else {
            return super.getUrl(request, file);
        }
    }
}
