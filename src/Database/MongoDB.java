package Database;

import Settings.DatabaseSettings;
import Tools.Log;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muwang on 5/6/2019.
 */
public final class MongoDB {

    public static void initialize() {
        try {
            ServerAddress serverAddress = new ServerAddress(DatabaseSettings.LOCAL_HOST, DatabaseSettings.MongoDB.PORT);
            List<ServerAddress> addresses = new ArrayList<>();
            addresses.add(serverAddress);

            MongoClient mongoClient = new MongoClient(addresses);

            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.MongoDB.DB_NAME);

            Log.log("Successfully connected to MongoDB!");

            mongoDatabase.createCollection(DatabaseSettings.MongoDB.SETTING_COLLECTION);
        } catch (Exception ex) {
            Log.log(ex.getClass().getName() + " : " + ex.getMessage());
        }
    }

}
