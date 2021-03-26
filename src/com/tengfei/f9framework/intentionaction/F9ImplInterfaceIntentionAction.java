package com.tengfei.f9framework.intentionaction;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.util.IncorrectOperationException;
import com.tengfei.f9framework.notification.F9Notifier;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class F9ImplInterfaceIntentionAction extends PsiElementBaseIntentionAction {
    public static final String SPRINGFRAMEWORK_COMPONENT = "org.springframework.stereotype.Component";
    public static String INTERFACE_PREFIX = "I";
    public static String IMPL_CLASS_POSTFIX = "Impl";
    public static Pattern F9INTERFACE_PATTERN = Pattern.compile("^I[A-Za-z]*");


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
        return ((PsiClass) element.getParent()).isInterface() &&
                F9INTERFACE_PATTERN.matcher(((PsiClass) element.getParent()).getName()).matches();
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);
        PsiElementFactory elementFactory = PsiElementFactory.getInstance(project);
        //查找特定接口的类
        PsiClass targetClass = (PsiClass) element.getParent();
        PsiDirectory containingDirectory = targetClass.getContainingFile().getContainingDirectory();
        PsiDirectory implDirectory = containingDirectory.getParent().findSubdirectory("impl");
        if (implDirectory == null) {
            implDirectory = containingDirectory.getParent().createSubdirectory("impl");
        }
        PsiDirectory finalImplDirectory = implDirectory;
        WriteCommandAction.runWriteCommandAction(project, () -> {
            createImplClassByInterface(fileFactory, elementFactory, targetClass, finalImplDirectory);
            PsiFile classByInterface = createClassByInterface(fileFactory, elementFactory, targetClass, finalImplDirectory);
            if(classByInterface != null) {
                //这里生成的psifile只存在于内存中 所以不能用psifile.getVirtualFile()获取
                VirtualFile virtualFile = VfsUtil.refreshAndFindChild(finalImplDirectory.getVirtualFile(), classByInterface.getName());
                FileEditorManager.getInstance(project).openFile(virtualFile,true);
            }
        });
    }

    private PsiFile createImplClassByInterface(PsiFileFactory fileFactory, PsiElementFactory elementFactory, PsiClass targetClass, PsiDirectory implDirectory) {
        //创建类
        assert targetClass.getName() != null;
        PsiClass implClass = elementFactory.createClass(getImplClassNameByInterfaceName(targetClass.getName()));
        assert implClass.getImplementsList() != null;
        implClass.getImplementsList().add(elementFactory.createClassReferenceElement(targetClass));
        implClass.getModifierList().addAnnotation(SPRINGFRAMEWORK_COMPONENT);
        //创建一个方法
        PsiMethod[] methods = targetClass.getMethods();
        for (PsiMethod method : methods) {
            PsiMethod implMethod = createMethodByTargetMethod(method, geImplMethodStatementTextByMethod(method));
            implClass.add(implMethod);
        }
        PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(getImplClassNameByInterfaceName(targetClass.getName()) + ".java", JavaFileType.INSTANCE, "");
        javaFile.add(implClass);
        reformatJavaFile(javaFile);
        if (implDirectory.findFile(javaFile.getName()) == null) {
            implDirectory.add(javaFile);
            return javaFile;
        }
        else {
            F9Notifier.notifyError(javaFile.getProject(),"file is adready exits");
        }
        return null;
    }


    private PsiFile createClassByInterface(PsiFileFactory fileFactory, PsiElementFactory elementFactory, PsiClass targetClass, PsiDirectory implDirectory) {
        assert targetClass.getName() != null;
        //创建类
        PsiClass implClass = elementFactory.createClass(getClassNameByInterfaceName(targetClass.getName()));
        //创建一个方法
        PsiMethod[] methods = targetClass.getMethods();
        for (PsiMethod method : methods) {
            PsiMethod implMethod = createMethodByTargetMethod(method, getClassMethodStatementTextByMethod(method));
            implClass.add(implMethod);
        }
        PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(getClassNameByInterfaceName(targetClass.getName()) + ".java", JavaFileType.INSTANCE, "");
        javaFile.add(implClass);
        reformatJavaFile(javaFile);
        if (implDirectory.findFile(javaFile.getName()) == null) {
            implDirectory.add(javaFile);
            return javaFile;
        }
        else {
            F9Notifier.notifyError(javaFile.getProject(),"file is adready exits");
        }
        return null;
    }

    private void reformatJavaFile(PsiJavaFile javaFile) {
        JavaCodeStyleManager.getInstance(javaFile.getProject()).shortenClassReferences(javaFile);
        CodeStyleManager.getInstance(javaFile.getProject()).reformat(javaFile);
    }

    @NotNull
    private PsiMethod createMethodByTargetMethod(PsiMethod method, String statementText) {
        PsiElementFactory elementFactory = PsiElementFactory.getInstance(method.getProject());
        PsiMethod result = elementFactory.createMethod(method.getName(), method.getReturnType());
        result.getParameterList().replace(method.getParameterList());
        if (method.getDocComment() != null) {
            result.addBefore(method.getDocComment(), result.getFirstChild());
        }
        PsiCodeBlock codeBlock = elementFactory.createCodeBlock();
        PsiStatement statement = elementFactory.createStatementFromText(statementText, null);
        codeBlock.add(statement);
        assert result.getBody() != null;
        result.getBody().replace(codeBlock);
        return result;
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

    private String geImplMethodStatementTextByMethod(@NotNull PsiMethod method) {
        PsiClass targetClass = method.getContainingClass();
        assert targetClass.getName() != null;
        String statementText;
        if (PsiType.VOID.equals(method.getReturnType())) {
            statementText = "new " + getClassNameByInterfaceName(targetClass.getName()) + "()." + method.getName() + getMethodParameterExpression(method) + ";";
        }
        else {
            statementText = "return new " + getClassNameByInterfaceName(targetClass.getName()) + "()." + method.getName() + getMethodParameterExpression(method) + ";";
        }
        return statementText;
    }

    private String getClassMethodStatementTextByMethod(@NotNull PsiMethod method) {
        String statementText;
        if (PsiType.VOID.equals(method.getReturnType())) {
            statementText = "return;";
        }
        else {
            statementText = "return null;";
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
