package freeriders.mag.settings.project.listener;

import com.intellij.openapi.project.Project;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerOpenedListener implements ProjectActivity {


    private List<Preset> loadProjectPresetsFromJsonFile(Project project) {
        /// Find the mag.json file within the project
        try{
            return ProjectPresetsUtils.loadPreset(project);
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
        VirtualFileManager.getInstance().addVirtualFileListener(new ProjectPresetsFileListener(project));
        // Set projectPresets in ProjectPresetsComponent
        ProjectPresetsState.getInstance().getState().setProjectPresetsState(projectPresets);
        return null;
    }
}

