package Tools;

/**
 * Created by muwang on 4/29/2019.
 */
public final class StringUtils {

    public static boolean checkStringNotNullOrEmpty(String str) {
        if (str == null || str.isEmpty() || str == "" || str.trim() == "") {
            return false;
        }

        return true;
    }

    public static void checkStringAndThrow(String str) throws Exception {
        if (!StringUtils.checkStringNotNullOrEmpty(str)) {
            throw new Exception("Input string is null or empty");
        }
    }
}
