package Tools;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by muwang on 5/3/2019.
 */
public final class FileUtils {

    public static void createFile(String filePath) {

    }

    public static boolean folderExistBasedOnDateTime(Date date, String path) {
        return false;
    }

    public static List<File> getAllFiles(String path) {
        File curDir = new File(path);
        return FileUtils.getFiles(curDir);
    }

    private static List<File> getFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        List<File> res = new ArrayList<>();

        for (File file : filesList) {
            if (file.isDirectory()) {
                res.addAll(FileUtils.getFiles(file));
            } else {
                res.add(file);
            }
        }

        return res;
    }

    public static boolean pathExist(String pathStr) {
        Path path = FileSystems.getDefault().getPath(pathStr);
        return Files.notExists(path);
    }

    public static void deleteFolder(String folderPath) throws Exception {
        File folder = new File(folderPath);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    FileUtils.deleteFolder(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
}
