package Core;

import Tools.DocumentUtils;
import org.bson.Document;

import java.util.Date;

/**
 * Created by muwang on 5/7/2019.
 */
public final class JCYDocument extends Document {
    private String dateStr;

    private String content;

    private String originalUrl;

    private String _id;

    private long createdDateTime;

    public String getDateStr() { return this.dateStr; }

    public String getContent() { return this.content; }

    public String getOriginalUrl() { return this.originalUrl; }

    public String get_id() { return this._id; }

    public long getCreatedDateTime() { return this.createdDateTime; }

    public JCYDocument(String dateStr, String content, String url) {
        this.setDateStr(dateStr);
        this.setContent(content);
        this.setOriginalUrl(url);
        this.set_id();
        this.setCreatedDateTime();
    }

    private void setDateStr(String dateStr) {
        this.dateStr = dateStr;
        this.update("Date", dateStr);
    }

    private void setContent(String content) {
        this.content = content;
        this.update("Content", content);
    }

    private void setOriginalUrl(String url) {
        this.originalUrl = url;
        this.update("Url", url);
    }

    private void set_id() {
        this.update("_id", this.get_id());
        this._id = DocumentUtils.generateId(this.getDateStr(), this.getOriginalUrl());
    }

    private void setCreatedDateTime() {
        this.createdDateTime = new Date().getTime();
        this.update("CreatedDateTime", this.createdDateTime);
    }

    private void update(String key, Object val) {
        if (this.containsKey(key)) {
            this.remove(key);
        }
        this.append(key, val);
    }
}

