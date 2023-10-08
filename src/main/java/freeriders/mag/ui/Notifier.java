package freeriders.mag.ui;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class Notifier {

    public static void notifyError(Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("MAG Notification Group")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

    public static void notifyWarning(Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("MAG Notification Group")
                .createNotification(content, NotificationType.WARNING)
                .notify(project);
    }

    public static void notifySucess(Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("MAG Notification Group")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

}
