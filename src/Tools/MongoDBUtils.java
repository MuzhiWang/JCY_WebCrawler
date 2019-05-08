package Tools;

import Settings.DatabaseSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

/**
 * Created by muwang on 5/7/2019.
 */
public final class MongoDBUtils {

    public static String generateId(String... strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    // Return boolean, true if insert, false if update(file exists)
    public static boolean upsertDocumentById(MongoCollection<Document> mongoCollection, Document document) {
        Bson filter = Filters.eq(DatabaseSettings.MongoDB.DOC_ID, document.get(DatabaseSettings.MongoDB.DOC_ID));
        if (!mongoCollection.find(filter).iterator().hasNext()) {
            mongoCollection.insertOne(document);
            return true;
        }

        UpdateOptions options = new UpdateOptions().upsert(true);
        mongoCollection.updateOne(filter, new Document().append(DatabaseSettings.MongoDB.DOC_UPDATE_SET_STR, document), options);
        return false;
    }

    public static List<Document> queryDocumentByContent(MongoCollection mongoCollection, String contentQueryStr, int queryCount) {
        if (queryCount <= 0) queryCount = 10;

        Iterator<Document> iterator = null;
        Bson search = new Document(
                "$text",
                new Document("$search", contentQueryStr));
//                    .append("$caseSensitive", true));
//                .append("$diacriticSensitive", false);
        iterator = mongoCollection.find(search).limit(queryCount).iterator();

        List<Document> res = new ArrayList<>();
        while (iterator.hasNext() && queryCount > 0) {
            res.add(iterator.next());
        }

        return res;
    }
}
