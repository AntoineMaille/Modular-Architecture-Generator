package freeriders.mag.settings.project.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import freeriders.mag.settings.project.state.ProjectPresetsState;
import freeriders.mag.settings.project.utils.ProjectPresetsUtils;
import org.jetbrains.annotations.NotNull;

public class ProjectPresetsFileListener implements VirtualFileListener {
    private final Project project;

    public ProjectPresetsFileListener(Project project) {
        this.project = project;
    }

    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        // Check if the changed file is 'mag.json'
        if ("mag.json".equals(event.getFileName())) {
            // Reload project presets using ProjectPresetsHandler
            try{
              ProjectPresetsState.getInstance().getState().setProjectPresetsState(ProjectPresetsUtils.loadPreset(project));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
