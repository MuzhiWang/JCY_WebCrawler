package Settings;

/**
 * Created by muwang on 5/6/2019.
 */
public final class DatabaseSettings {

    public static final String LOCAL_HOST = "localhost";

    public static class MongoDB {
        public static final int PORT = 27017;

        public static final String DB_NAME = "JCY_WebCrawler";

        public static final String SETTING_COLLECTION = "Settings";
    }

    public static class Cassandra {

    }
}
