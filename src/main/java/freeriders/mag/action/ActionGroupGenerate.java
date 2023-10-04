package freeriders.mag.action;


import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Defines the ActionGroup to be only visible when parent is a [PSI_ELEMENT]
 */
class ActionGroupGenerate extends ActionGroup {

    @Override
    public void update(@NotNull AnActionEvent event) {
        super.update(event);
        // Enable/disable depending on whether user is editing
        @Nullable PsiElement psi = event.getData(CommonDataKeys.PSI_ELEMENT);
        //event.presentation.isEnabled = project != null
        // Always make visible.
        event.getPresentation().setVisible(psi != null);
        // Take this opportunity to set an icon for the menu entry.
        event.getPresentation().setIcon(AllIcons.Actions.NewFolder);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return super.getActionUpdateThread();
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[] {(new ActionGenerate("Flutter Clean Architecture", "Flutter Clean Architecture", AllIcons.Actions.NewFolder))};
    }
}
