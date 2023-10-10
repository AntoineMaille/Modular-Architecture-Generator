package freeriders.mag.settings.project.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import freeriders.mag.settings.ide.state.models.Preset;
import freeriders.mag.settings.project.state.ProjectPresetsState;
import freeriders.mag.settings.project.utils.ProjectPresetsUtils;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectManagerOpenedListener implements ProjectActivity {


    private List<Preset> loadProjectPresetsFromJsonFile(Project project) {
        /// Find the mag.json file within the project
        try{
            VirtualFile magJsonFile = Objects.requireNonNull(ProjectUtil.guessProjectDir(project)).findFileByRelativePath("mag.json");
            return ProjectPresetsUtils.loadPreset(magJsonFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        // Load project-specific presets from a JSON file
        List<Preset> projectPresets = loadProjectPresetsFromJsonFile(project);
        VirtualFileManager.getInstance().addAsyncFileListener(new ProjectPresetsFileListener(project), project);
        // Set projectPresets in ProjectPresetsComponent
        ProjectPresetsState.getInstance().getState().setProjectPresetsState(projectPresets);
        return null;
    }
}

