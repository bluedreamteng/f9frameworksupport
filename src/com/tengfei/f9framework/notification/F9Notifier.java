package com.tengfei.f9framework.notification;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @author ztf
 */
public class F9Notifier {

    private static final NotificationGroup NOTIFICATION_GROUP =
            new NotificationGroup("Custom Notification Group", NotificationDisplayType.BALLOON, true);

    public static void notifyError(@Nullable Project project, String content) {
        NOTIFICATION_GROUP.createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

    public static void notifyMessage(@Nullable Project project, String content) {
        NOTIFICATION_GROUP.createNotification(content, NotificationType.INFORMATION)
                .notify(project);
    }

    public static void notifyWarning(@Nullable Project project, String content) {
        NOTIFICATION_GROUP.createNotification(content, NotificationType.WARNING)
                .notify(project);
    }

}
