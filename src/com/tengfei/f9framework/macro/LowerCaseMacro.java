package com.tengfei.f9framework.macro;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ztf
 */
public class LowerCaseMacro extends MacroBase {

    public LowerCaseMacro() {
        super("lowerCase", "lowerCase(String)");
    }

    @Nullable
    @Override
    protected Result calculateResult(@NotNull Expression[] params, ExpressionContext context, boolean quick) {
        String text = getTextResult(params, context, true);
        if (text == null) {
            return null;
        }
        if (text.length() > 0) {
            text = StringUtil.toLowerCase(text);
        }
        return new TextResult(text);
    }
}
