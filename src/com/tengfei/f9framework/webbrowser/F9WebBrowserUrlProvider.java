package com.tengfei.f9framework.webbrowser;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;

public class F9WebBrowserUrlProvider extends BuiltInWebBrowserUrlProvider {
    @Nullable
    @Override
    protected Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) {
        return F9HtmlUrlFactory.getInstance().createF9HtmlUrl(file);
    }
}
