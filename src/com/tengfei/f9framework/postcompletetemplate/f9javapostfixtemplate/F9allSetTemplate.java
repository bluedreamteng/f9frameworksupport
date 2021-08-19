package com.tengfei.f9framework.postcompletetemplate.f9javapostfixtemplate;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.tengfei.f9framework.util.EditorManage;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列举出所有的setter方法
 * @author ztf
 */
public class F9allSetTemplate extends PostfixTemplateWithExpressionSelector {

    public static final Pattern SETTERMETHODPATTERN = Pattern.compile("^set[A-Z]\\w*");

    public F9allSetTemplate() {
        super(
                "allset",
                "list all setter",
                JavaPostfixTemplatesUtils.selectorTopmost(element ->
                        element instanceof PsiReferenceExpression && ((PsiReferenceExpression) element).getType() != null)
        );
    }


    @Override
    protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor) {
        EditorManage.getInstance(editor).removeExpression(expression);
        final Project project = expression.getProject();
        final TemplateManager manager = TemplateManager.getInstance(project);
        String className = ((PsiReferenceExpression) expression).getType().getCanonicalText();
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
        List<String> setters = getSetter(psiClass);
        final String stringTemplate = getSetsText(expression.getText(), setters) + "$END$";
        final Template template = manager.createTemplate("", "", stringTemplate);
        template.setToReformat(true);
        manager.startTemplate(editor, template);

    }

    private String getSetsText(String referenceName, List<String> methodNames) {
        StringBuilder builder = new StringBuilder();
        for (String methodName : methodNames) {
            builder.append(referenceName).append(".").append(methodName).append("();\n");
        }
        return builder.toString();
    }

    private static List<String> getSetter(PsiClass psiClass) {
        List<String> result = new ArrayList<>();
        if (psiClass == null) {
            return result;
        }
        PsiMethod[] methods = psiClass.getMethods();
        for (PsiMethod method : methods) {
            String methodName = method.getName();
            Matcher matcher = SETTERMETHODPATTERN.matcher(methodName);
            if (matcher.matches()) {
                result.add(methodName);
            }
        }
        return result;
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     * A subclass overrides the {@code finalize} method to dispose of
     * system resources or to perform other cleanup.
     * <p>
     * The general contract of {@code finalize} is that it is invoked
     * if and when the Java&trade; virtual
     * machine has determined that there is no longer any
     * means by which this object can be accessed by any thread that has
     * not yet died, except as a result of an action taken by the
     * finalization of some other object or class which is ready to be
     * finalized. The {@code finalize} method may take any action, including
     * making this object available again to other threads; the usual purpose
     * of {@code finalize}, however, is to perform cleanup actions before
     * the object is irrevocably discarded. For example, the finalize method
     * for an object that represents an input/output connection might perform
     * explicit I/O transactions to break the connection before the object is
     * permanently discarded.
     * <p>
     * The {@code finalize} method of class {@code Object} performs no
     * special action; it simply returns normally. Subclasses of
     * {@code Object} may override this definition.
     * <p>
     * The Java programming language does not guarantee which thread will
     * invoke the {@code finalize} method for any given object. It is
     * guaranteed, however, that the thread that invokes finalize will not
     * be holding any user-visible synchronization locks when finalize is
     * invoked. If an uncaught exception is thrown by the finalize method,
     * the exception is ignored and finalization of that object terminates.
     * <p>
     * After the {@code finalize} method has been invoked for an object, no
     * further action is taken until the Java virtual machine has again
     * determined that there is no longer any means by which this object can
     * be accessed by any thread that has not yet died, including possible
     * actions by other objects or classes which are ready to be finalized,
     * at which point the object may be discarded.
     * <p>
     * The {@code finalize} method is never invoked more than once by a Java
     * virtual machine for any given object.
     * <p>
     * Any exception thrown by the {@code finalize} method causes
     * the finalization of this object to be halted, but is otherwise
     * ignored.
     *
     * @throws Throwable the {@code Exception} raised by this method
     * @jls 12.6 Finalization of Class Instances
     * @see WeakReference
     * @see PhantomReference
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}