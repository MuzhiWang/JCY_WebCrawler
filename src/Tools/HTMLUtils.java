package Tools;

import LocalException.PageNotExistException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muwang on 4/29/2019.
 */
public final class HTMLUtils {

    private static Map<String, String> headers = new HashMap<String, String>() {
        {
            this.put("User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
            this.put("Referer", "http://www.ajxxgk.jcy.gov.cn/html/zjxflws/");
            this.put("Host", "www.ajxxgk.jcy.gov.cn");
            this.put("Connection", "keep-alive");
            this.put("Cache-Control", "max-ageo=0");
            this.put("Accept-Language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7,zh-TW;q=0.6");
            this.put("Accept-Encoding", "gzip, deflate");
            this.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            this.put("Upgrade-Insecure-Requests", "1");
        }
    };

    public static Document parseHtmlAsDocument(String url) throws Exception {
        StringUtils.checkStringAndThrow(url);

        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .headers(HTMLUtils.headers)
                    .timeout(15000)
                    .method(Connection.Method.GET)
                    .execute()
                    .parse();
        } catch (Exception ex) {
            Log.log(ex);

            if (ex instanceof HttpStatusException
                    && ((HttpStatusException)ex).getStatusCode() == 404) {
                Log.log("Page not exist");
                throw new PageNotExistException();
            } else throw ex;
        }

        return doc;
    }

    public static void openFiddlerDebug() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
    }

}
