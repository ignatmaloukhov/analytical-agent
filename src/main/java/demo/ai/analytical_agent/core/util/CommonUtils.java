package demo.ai.analytical_agent.core.util;


public class CommonUtils {

    public static String cutFileSize(String rawFileName) {

        if (rawFileName == null || rawFileName.isEmpty()) {
            return rawFileName;
        }
        int index = rawFileName.lastIndexOf('(');
        if (index == -1) {
            return rawFileName;
        }

        return rawFileName.substring(0, index - 1);
    }

    public static String getFileName(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int lastSpaceIndex = fileName.lastIndexOf('.');
        if (lastSpaceIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastSpaceIndex);
    }

    public static String getFileExtension(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int index = fileName.lastIndexOf('.');

        if (index == -1) {
            return fileName;
        }

        return fileName.substring(index + 1);
    }

}