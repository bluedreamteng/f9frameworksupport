package com.tengfei.f9framework.intentionaction;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class F9ImplInterfaceIntentionAction extends PsiElementBaseIntentionAction {
    public static String INTERFACE_PREFIX = "I";
    public static String IMPL_CLASS_POSTFIX = "Impl";

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return "F9 Implement interface";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        if (!(element.getParent() instanceof PsiClass) || !(element instanceof PsiIdentifier)) {
            return false;
        }
        return ((PsiClass) element.getParent()).isInterface();

    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
        PsiElementFactory elementFactory = PsiElementFactory.getInstance(project);
        //查找特定接口的类
        PsiClass targetClass = (PsiClass)element.getParent();

        PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Implement interface", project);
        packageChooserDialog.show();
        PsiPackage choosePage = packageChooserDialog.getSelectedPackage();
        if (choosePage == null) {
            return;
        }

        WriteCommandAction.runWriteCommandAction(project, () -> {
            createClassByInterface(fileFactory, elementFactory, targetClass, choosePage);
            createImplClassByInterface(fileFactory, elementFactory, targetClass, choosePage);
        });
    }

    private void createImplClassByInterface(PsiFileFactory fileFactory, PsiElementFactory elementFactory, PsiClass targetClass, PsiPackage choosePage) {
        //创建类
        assert targetClass.getName() != null;
        PsiClass implClass = elementFactory.createClass(getImplClassNameByInterfaceName(targetClass.getName()));
        assert implClass.getImplementsList() != null;
        implClass.getImplementsList().add(elementFactory.createClassReferenceElement(targetClass));
        implClass.getModifierList().addAnnotation("org.springframework.stereotype.Component");
        //创建一个方法
        PsiMethod[] methods = targetClass.getMethods();
        for (PsiMethod method : methods) {
            PsiMethod implMethod = elementFactory.createMethod(method.getName(), method.getReturnType(), null);
            implMethod.getParameterList().replace(method.getParameterList());
            PsiCodeBlock codeBlock = elementFactory.createCodeBlock();
            PsiStatement statement = elementFactory.createStatementFromText(geImplMethodStatementTextByMethod(method, targetClass), null);
            codeBlock.add(statement);
            assert implMethod.getBody() != null;
            implMethod.getBody().replace(codeBlock);
            implClass.add(implMethod);
        }
        PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(getImplClassNameByInterfaceName(targetClass.getName()) + ".java", JavaFileType.INSTANCE, "");
        javaFile.add(implClass);
        JavaCodeStyleManager.getInstance(choosePage.getProject()).shortenClassReferences(javaFile);
        CodeStyleManager.getInstance(choosePage.getProject()).reformat(javaFile);

        choosePage.getDirectories()[0].add(javaFile);
    }


    private void createClassByInterface(PsiFileFactory fileFactory, PsiElementFactory elementFactory, PsiClass targetClass, PsiPackage choosePage) {
        assert targetClass.getName() != null;
        //创建类
        PsiClass implClass = elementFactory.createClass(getClassNameByInterfaceName(targetClass.getName()));
        //创建一个方法
        PsiMethod[] methods = targetClass.getMethods();
        for (PsiMethod method : methods) {
            PsiMethod implMethod = elementFactory.createMethod(method.getName(), method.getReturnType());
            implMethod.getParameterList().replace(method.getParameterList());
            implClass.add(implMethod);
        }
        PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(getClassNameByInterfaceName(targetClass.getName()) + ".java", JavaFileType.INSTANCE, "");
        javaFile.add(implClass);
        JavaCodeStyleManager.getInstance(choosePage.getProject()).shortenClassReferences(javaFile);
        CodeStyleManager.getInstance(choosePage.getProject()).reformat(javaFile);
        choosePage.getDirectories()[0].add(javaFile);
    }

    private static String getImplClassNameByInterfaceName(@NotNull String interfaceName) {
        String implClassName = "";
        if ("".equals(interfaceName)) {
            return implClassName;
        }
        if (interfaceName.substring(0, 1).equals(INTERFACE_PREFIX)) {
            implClassName = interfaceName.substring(1) + IMPL_CLASS_POSTFIX;
        }
        return implClassName;

    }

    private static String getClassNameByInterfaceName(@NotNull String interfaceName) {
        String className = "";
        if ("".equals(interfaceName)) {
            return className;
        }
        if (interfaceName.substring(0, 1).equals(INTERFACE_PREFIX)) {
            className = interfaceName.substring(1);
        }
        return className;
    }

    private String geImplMethodStatementTextByMethod(@NotNull PsiMethod method, PsiClass targetClass) {
        assert targetClass.getName() != null;
        String statementText;
        if (!PsiType.VOID.equals(method.getReturnType())) {
            statementText = "return new " + getClassNameByInterfaceName(targetClass.getName()) + "()." + method.getName() + getMethodParameterExpression(method) + ";";
        }
        else {
            statementText = "new " + getClassNameByInterfaceName(targetClass.getName()) + "()." + method.getName() + getMethodParameterExpression(method) + ";";
        }
        return statementText;
    }

    private String getMethodParameterExpression(@NotNull PsiMethod method) {
        StringBuilder stringBuilder = new StringBuilder("(");
        PsiParameter[] parameters = method.getParameterList().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (i < parameters.length - 1) {
                stringBuilder.append(parameters[i].getName()).append(",");
            }
            else {
                stringBuilder.append(parameters[i].getName());
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
