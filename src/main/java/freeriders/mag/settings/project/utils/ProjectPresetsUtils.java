package freeriders.mag.settings.project.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import freeriders.mag.settings.ide.state.models.Preset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProjectPresetsUtils {


    public static List<Preset> loadPreset(Project project) throws IOException {

        VirtualFile magJsonFile = project.getBaseDir().findFileByRelativePath("mag.json");
        if (magJsonFile != null && magJsonFile.exists()) {

            // Read and parse the JSON content from the file
            InputStream inputStream = magJsonFile.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonContent.append(line);
            }
            bufferedReader.close();

            // Deserialize the JSON content into a list of Preset objects
            return Preset.fromListJson(jsonContent.toString());
        }
        else throw new IOException("file mag.json does not exists");
    }

}
