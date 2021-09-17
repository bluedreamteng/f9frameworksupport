package com.tengfei.f9framework.module;

import com.intellij.facet.FacetManager;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.tengfei.f9framework.settings.modulesetting.F9CustomizeModuleSetting;
import com.tengfei.f9framework.settings.modulesetting.F9ProjectSetting;
import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import com.tengfei.f9framework.notification.F9Notifier;
import org.jetbrains.annotations.Nullable;
import org.jsoup.internal.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 */
public class F9ModuleFacade {
    private static F9ModuleFacade f9ModuleFacade;

    private final Project project;

    private final List<F9StandardModule> standardModules = new ArrayList<>();
    private final List<F9CustomizeModule> customizeModules = new ArrayList<>();


    public static F9ModuleFacade getInstance(Project project) {
        if (f9ModuleFacade == null) {
            f9ModuleFacade = new F9ModuleFacade(project);
        }
        return f9ModuleFacade;
    }

    private F9ModuleFacade(Project project) {
        this.project = project;
        init();
    }

    public void init() {
        //clear
        clearModules();
        List<F9StandardModuleSetting> standardModuleSettings = F9ProjectSetting.getInstance(project).standardModules;
        if(standardModuleSettings.isEmpty()) {
            F9Notifier.notifyWarning(project,"未检测到配置文件，请及时配置");
            return;
        }
        for (F9StandardModuleSetting standardModuleSetting : standardModuleSettings) {
            F9StandardModule standardModule = new F9StandardModule();
            standardModule.setName(standardModuleSetting.name);
            standardModule.setDeployHost(standardModuleSetting.deployHost);
            standardModule.setProductCustomizeName(standardModuleSetting.getProductCustomizeName());
            String webRootPath = findWebRootPathByModuleName(standardModuleSetting.name);
            if(StringUtil.isBlank(webRootPath)) {
                F9Notifier.notifyWarning(project,standardModuleSetting.getName() + "未找到webrootpath,请检查配置");
                clearModules();
                return;
            }
            standardModule.setWebRootPath(webRootPath);
            for (F9CustomizeModuleSetting customizeModuleSetting : standardModuleSetting.customizeModuleList) {
                F9CustomizeModule customizeModule = new F9CustomizeModule();
                customizeModule.setName(customizeModuleSetting.name);
                customizeModule.setStandardModule(standardModule);
                customizeModule.setCustomizeProjectPath(customizeModuleSetting.customizeProjectPath);
                String customizeModuleSoftLinkWebRootPath = findCustomizeModuleSoftLinkWebRootPath(standardModule, customizeModuleSetting.customizeProjectPath);
                if(StringUtil.isBlank(customizeModuleSoftLinkWebRootPath)) {
                    F9Notifier.notifyWarning(project,customizeModuleSetting.getName() + "未找到对应软链接真实路径webrootpath,请检查配置");
                    clearModules();
                    return;
                }
                customizeModule.setWebRoot(customizeModuleSoftLinkWebRootPath);
                standardModule.getCustomizeModuleList().add(customizeModule);
                customizeModules.add(customizeModule);
            }
            standardModules.add(standardModule);
        }
        F9Notifier.notifyMessage(project,"项目模块初始化成功!");
    }

    private void clearModules() {
        standardModules.clear();
        customizeModules.clear();
    }

    public List<F9StandardModule> findAllStandardModules() {
        return standardModules;
    }

    public List<F9CustomizeModule> findAllCustomizeModules() {
        return customizeModules;
    }


    @Nullable
    public F9StandardModule findStandardModuleByName(String moduleName) {
        for (F9StandardModule standardModule : standardModules) {
            if (standardModule.getName().equals(moduleName)) {
                return standardModule;
            }
        }
        return null;
    }

    @Nullable
    public F9CustomizeModule findCustomizeModuleByName(String moduleName) {
        for (F9CustomizeModule customizeModule : customizeModules) {
            if (customizeModule.getName().equals(moduleName)) {
                return customizeModule;
            }
        }
        return null;
    }

    @Nullable
    public F9CustomizeModule findContainingCustomizeModule(VirtualFile virtualFile) {
        for (F9CustomizeModule customizeModule : customizeModules) {
            if(!(virtualFile.getPresentableUrl().equals(customizeModule.getWebRoot())) && virtualFile.getPresentableUrl().startsWith(customizeModule.getWebRoot())) {
                return customizeModule;
            }
        }
        return null;
    }

    @Nullable
    public F9StandardModule findContainingStandardModule(VirtualFile virtualFile) {
        for (F9StandardModule standardModule : standardModules) {
            if(!(virtualFile.getPresentableUrl().equals(standardModule.getWebRootPath())) && virtualFile.getPresentableUrl().startsWith(standardModule.getWebRootPath())) {
                return standardModule;
            }
        }
        return null;
    }


    @Nullable
    private String findWebRootPathByModuleName(String moduleName) {
        ModuleManager moduleManager = ModuleManager.getInstance(project);
        Module module = moduleManager.findModuleByName(moduleName);
        if (module == null) {
            return null;
        }
        WebFacet webFacet = FacetManager.getInstance(module).getFacetByType(WebFacet.ID);
        if (webFacet != null && webFacet.getWebRoots() != null && !webFacet.getWebRoots().isEmpty()) {
            return webFacet.getWebRoots().get(0).getPresentableUrl();
        }
        return null;
    }

    @Nullable
    private String findCustomizeModuleSoftLinkWebRootPath(F9StandardModule standardModule, String customizeProjectPath) {
        //定位到个性化目录软链接文件夹
        String result = null;
        String customizeDir = standardModule.getWebRootPath() + "/" + customizeProjectPath;
        File file = new File(customizeDir);
        if (!file.exists()) {
            return null;
        }
        try {
            String softLinkRealPath = file.toPath().toRealPath().toString();
            if(file.toPath().toString().equals(softLinkRealPath)) {
                return null;
            }
            result = softLinkRealPath;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
