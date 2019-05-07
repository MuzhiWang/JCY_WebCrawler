package Database;

import Settings.DatabaseSettings;
import Tools.Log;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muwang on 5/6/2019.
 */
public final class MongoDB {

    private MongoDatabase database;

    private MongoCollection<Document> settingsCollection;

    private MongoCollection<Document> jcyCollection;

    public MongoDB() {
        this.initialize();
    }

    public MongoCollection<Document> getSettingsCollection() { return this.settingsCollection; }

    public MongoCollection<Document> getJcyCollection() { return this.jcyCollection; }

    public void initialize() {
        try {
            ServerAddress serverAddress = new ServerAddress(DatabaseSettings.LOCAL_HOST, DatabaseSettings.MongoDB.PORT);
            List<ServerAddress> addresses = new ArrayList<>();
            addresses.add(serverAddress);

            MongoClient mongoClient = new MongoClient(addresses);

            this.database = mongoClient.getDatabase(DatabaseSettings.MongoDB.DB_NAME);

            Log.log("Successfully connected to MongoDB!");

            this.createCollectionIfNotExist(DatabaseSettings.MongoDB.SETTING_COLLECTION);
            this.createCollectionIfNotExist(DatabaseSettings.MongoDB.JCY_COLLECTION);

            this.settingsCollection = this.database.getCollection(DatabaseSettings.MongoDB.SETTING_COLLECTION);
            this.jcyCollection = this.database.getCollection(DatabaseSettings.MongoDB.JCY_COLLECTION);
        } catch (Exception ex) {
            Log.log(ex);
        }
    }

    private void createCollectionIfNotExist(String collectionName) {
        try {
            this.database.createCollection(collectionName);
        } catch (MongoCommandException ex) {
            if (ex.getErrorMessage().contains("exist"))
                Log.log(ex);
            else
                throw ex;
        }
    }
}
