package com.tengfei.f9framework.settings.modulesetting.ui;

import com.tengfei.f9framework.settings.modulesetting.F9StandardModuleSetting;
import org.jsoup.internal.StringUtil;

/**
 * 模块配置校验器
 * @author ztf
 */
public interface ModuleSettingValidator {

    public static ModuleSettingValidatorResult validateStdModuleSetting(F9StandardModuleSetting standardModuleSetting) {
        String name = standardModuleSetting.getName();
        if(StringUtil.isBlank(name)) {
            return ModuleSettingValidatorResult.buildIllegalResult("模块名为空");
        }
        return null;
    }
}
