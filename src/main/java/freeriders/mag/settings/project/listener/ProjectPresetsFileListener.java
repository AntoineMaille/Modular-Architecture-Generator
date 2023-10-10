package freeriders.mag.settings.project.listener;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.AsyncFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import freeriders.mag.settings.project.state.ProjectPresetsState;
import freeriders.mag.settings.project.utils.ProjectPresetsUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProjectPresetsFileListener implements AsyncFileListener {
    private final Project project;

    public ProjectPresetsFileListener(Project project) {
        this.project = project;
    }

    @Override
    public @Nullable ChangeApplier prepareChange(@NotNull List<? extends @NotNull VFileEvent> events) {
        return new ChangeApplier() {
            @Override
            public void afterVfsChange() {
                // Handle changes after VFS events are applied
                ApplicationManager.getApplication().invokeLater(() -> {
                    for (VFileEvent event : events) {
                        event.getPath();
                        if (event instanceof VFileContentChangeEvent) {
                            // Handle content change event
                            handleContentChangeEvent((VFileContentChangeEvent) event);
                        }
                    }
                });
            }
        };
    }

    // Define methods to handle specific event types here

    private void handleContentChangeEvent(VFileContentChangeEvent event) {
        if (event.getPath().endsWith("mag.json")) {
            ApplicationManager.getApplication().invokeLater(() -> {
                try {
                    ProjectPresetsState.getInstance().getState().setProjectPresetsState(ProjectPresetsUtils.loadPreset(event.getFile()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
