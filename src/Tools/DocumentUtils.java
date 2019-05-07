package Tools;

import Settings.DatabaseSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Base64;

/**
 * Created by muwang on 5/7/2019.
 */
public final class DocumentUtils {

    public static String generateId(String... strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    public static void upsertDocumentById(MongoCollection<Document> mongoCollection, Document document) {
        Bson filter = Filters.eq(DatabaseSettings.MongoDB.DOC_ID, document.get(DatabaseSettings.MongoDB.DOC_ID));
        if (!mongoCollection.find(filter).iterator().hasNext()) {
            mongoCollection.insertOne(document);
        } else {
            UpdateOptions options = new UpdateOptions().upsert(true);
            mongoCollection.updateOne(filter, new Document().append(DatabaseSettings.MongoDB.DOC_UPDATE_SET_STR, document), options);
        }
    }
}
