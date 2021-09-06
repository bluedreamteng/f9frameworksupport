import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        JPanel panel = ToolbarDecorator.createDecorator(new JTree())
                .setAddActionUpdater(e -> true)
                .setAddAction(button -> System.out.println("hello wrold"))
                .setEditActionUpdater(e -> true)
                .setEditAction(button -> System.out.println("hello wrold"))
                .setRemoveActionUpdater(e -> true)
                .setRemoveAction(button -> System.out.println("hello wrold"))
                .addExtraAction(duplicateAction())
                .createPanel();
        JPanel mainPanel = FormBuilder.createFormBuilder().addComponent(panel).getPanel();
        mainPanel.setVisible(true);
    }


    private  static AnActionButton duplicateAction() {
        AnActionButton button = new AnActionButton(CodeInsightBundle.messagePointer("action.AnActionButton.text.duplicate"), PlatformIcons.COPY_ICON) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                System.out.println("hello wrold");
            }

            @Override
            public void updateButton(@NotNull AnActionEvent e) {
                e.getPresentation().setEnabled(true);
            }
        };
        return button;
    }
}
