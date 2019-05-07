package Tools;

/**
 * Created by muwang on 4/29/2019.
 */
public final class Log {

    public static void log(Object obj) {
        System.out.println(obj.toString());
    }

    public static void log(Exception ex) {
        Log.log(ex.getClass().getName() + " : " + ex.getMessage());
    }
}
