package com.tengfei.f9framework.webbrowser;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import com.tengfei.f9framework.setting.F9Settings;
import org.jetbrains.annotations.NotNull;

/**
 * @author ztf
 */
public class F9HtmlUrlFactory {

    private F9HtmlUrlFactory() {

    }

    public static F9HtmlUrlFactory getInstance() {
        return new F9HtmlUrlFactory();
    }

    public Url createF9HtmlUrl(@NotNull VirtualFile file) {
        String filePath = file.getPath();
        if (!isValidPath(filePath)) {
            throw new UnvalidPathException("unvalid path");
        }

        return new F9HtmlUrl("http", null, getRootPath(filePath)).resolve(getFileRelativePath(filePath));
    }

    private String getRootPath(String filePath) {
        String rootPath = "";

        if (filePath.contains(F9Settings.GL_PROJECT_NAME) || filePath.contains(F9Settings.GL_PROJECT_PAGE_PATH)) {
            rootPath = F9Settings.GL_DEPLOY_PATH;
        }

        else if (filePath.contains(F9Settings.QY_PROJECT_NAME) || filePath.contains(F9Settings.QY_PROJECT_PAGE_PATH)) {
            rootPath = F9Settings.QY_DEPLOY_PATH;
        }

        return rootPath;
    }

    private boolean isValidPath(String filePath) {
        if(!filePath.contains(F9Settings.WEB_ROOT_PATH)){
            return false;
        }
        return filePath.contains(F9Settings.GL_PROJECT_NAME) || filePath.contains(F9Settings.GL_PROJECT_PAGE_PATH) ||
                filePath.contains(F9Settings.QY_PROJECT_NAME) || filePath.contains(F9Settings.QY_PROJECT_PAGE_PATH);
    }


    private String getFileRelativePath(String filePath) {
        String result;
        if (filePath.contains(F9Settings.GL_PROJECT_PAGE_PATH)) {
            result = filePath.split(F9Settings.GL_PROJECT_PAGE_PATH)[1];
            result = result.substring(1, result.indexOf('.'));
        }
        else if (filePath.contains(F9Settings.QY_PROJECT_PAGE_PATH)) {
            result = filePath.split(F9Settings.QY_PROJECT_PAGE_PATH)[1];
            result = result.substring(1, result.indexOf('.'));
        }
        else {
            result = filePath.split("webapp")[1];
            result = result.substring(1, result.indexOf('.'));
        }
        return result;
    }


}
