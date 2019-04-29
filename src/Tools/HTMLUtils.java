package Tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by muwang on 4/29/2019.
 */
public final class HTMLUtils {

    public static Document parseHtmlAsDocument(String url) throws Exception {
        StringUtils.checkStringAndThrow(url);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Log.log(ex);
            throw ex;
        }

        return doc;
    }
}
