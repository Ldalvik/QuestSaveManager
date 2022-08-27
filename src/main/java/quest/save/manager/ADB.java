package quest.save.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ADB {

    static String ADB_PATH = "C:\\FFA\\platform-tools\\adb.exe";

    public static String run(String... commands) throws IOException, InterruptedException {
        Process p = new ProcessBuilder(commands).start();
        p.waitFor();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String result = "";
        String line;
        while ((line = in.readLine()) != null) {
            result = result.concat(line);
        }
        return result;
    }

    public static String getConnectedDevices() throws IOException, InterruptedException {
        return run(ADB_PATH, "devices", "-l");
    }

    public static boolean isQuestConnected() throws IOException, InterruptedException {
        return getConnectedDevices().toLowerCase().contains("quest_2");
    }

    public static String[] getInstalledApps() throws IOException, InterruptedException {
        return run(ADB_PATH,  "shell", "pm", "list", "packages", "-3").split("package:");
    }

    public static String getAppDirectory(int index) throws IOException, InterruptedException {
        return run(ADB_PATH, "shell", "pm", "path", getInstalledApps()[index]);
    }

}
