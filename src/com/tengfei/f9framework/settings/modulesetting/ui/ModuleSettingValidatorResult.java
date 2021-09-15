package com.tengfei.f9framework.settings.modulesetting.ui;

/**
 * @author ztf
 */
public class ModuleSettingValidatorResult {

    private final boolean isSuccess;
    private final String description;

    public static ModuleSettingValidatorResult buildLegalResult() {
        return buildLegalResult("validate legal");
    }

    public static ModuleSettingValidatorResult buildLegalResult(String description) {
        return new ModuleSettingValidatorResult(true, description);
    }


    public static ModuleSettingValidatorResult buildIllegalResult(String description) {
        return new ModuleSettingValidatorResult(false, description);
    }


    private ModuleSettingValidatorResult(boolean isSuccess, String description) {
        this.isSuccess = isSuccess;
        this.description = description;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getDescription() {
        return description;
    }
}
