package freeriders.mag.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ActionGenerate extends AnAction {


    public ActionGenerate() {
    }

    public ActionGenerate(String flutterCleanArchitecture, String flutterCleanArchitecture1, Icon newFolder) {
        super(flutterCleanArchitecture, flutterCleanArchitecture1, newFolder);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }
}
