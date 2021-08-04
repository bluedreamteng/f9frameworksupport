package test;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tengfei.f9framework.module.F9ModuleFacade;

public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        F9ModuleFacade instance = F9ModuleFacade.getInstance(e.getProject());
        System.out.println(instance);
    }
}
