package Core;

import Database.MongoDB;
import LocalException.PageNotExistException;
import Settings.WebPageSettings;
import Tools.MongoDBUtils;
import Tools.FileUtils;
import Tools.HTMLUtils;
import Tools.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonReader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.List;

/**
 * Created by muwang on 5/3/2019.
 */
public final class JCYWebCrawler extends WebCrawlerBase {

    private static String ROOT_FOLDER;

    private MongoDB mongoDB;

    public JCYWebCrawler(String rootFolder) {
        this.ROOT_FOLDER = rootFolder;
        this.mongoDB = new MongoDB();
        this.initialExistFilesMap();
    }

    @Override
    protected void onStart() throws Exception {
        this.initialExistFilesMap();

        ObjectMapper mapper = new ObjectMapper();

        List<org.bson.Document> docs = MongoDBUtils.queryDocumentByContent(this.mongoDB.getJcyCollection(), "安徽省宿松", 10);
        for (org.bson.Document doc : docs) {
            String json = doc.toJson();
            Log.log(json);
            JCYDocument jcyDoc = mapper.readValue(json, JCYDocument.class);
            Log.log(jcyDoc);
        }
    }

    @Override
    protected void inProcess() throws Exception {
        for (int i = 350; i < 350; ++i) {
            if (i == 1) {
                if (!this.querySummaryPage("")) {
                    break;
                }
            } else {
                if (!this.querySummaryPage(Integer.toString(i))) {
                    break;
                }
            }
        }
    }

    @Override
    protected void beforeEnd() {

    }

    // Query summary page with list of documents.
    // Return if exist new files. True exist, false not exist.
    private boolean querySummaryPage(String pageIndex) throws Exception {
        String url = pageIndex == "" ? WebPageSettings.FLWS_ROOT + pageIndex : String.format(WebPageSettings.FLWS_FORMAT, pageIndex);
        Document rootPage;
        try {
            rootPage = HTMLUtils.parseHtmlAsDocument(url);
        } catch (PageNotExistException ex) {
            Log.log(String.format("Page not exist with url: %s", url));
            throw ex;
        }

        // 不起诉决定书
        Element tab_3_bqsjds = rootPage.selectFirst("div#" + WebPageSettings.FLWS_BQSJDS_PAGE_ID);

        Element content_ul = tab_3_bqsjds.selectFirst("ul");

        boolean hasNewFile = false;
        for (Element li : content_ul.select("li")) {
            // Composite Title
            Element ctitle = li.selectFirst("div.ctitle");

            // link & title
            Element linkEle = ctitle.select("a[href]").get(1);
            String link = linkEle.attr(WebPageSettings.HTML.Href);
            String title = linkEle.attr(WebPageSettings.HTML.Title);
            Log.log(link);
            Log.log(title);

            // sj - 时间
            Element time = li.selectFirst("div.crow > div.sj > span");
            String timeStr = time.text();
            Log.log(timeStr);

            JCYDocument jcyDocument = this.getAndGenerateQiSuShu(WebPageSettings.JCY_ROOT + link, timeStr, title);

            if (jcyDocument != null) {
                if (MongoDBUtils.upsertDocumentById(this.mongoDB.getJcyCollection(), jcyDocument)) {
                    hasNewFile = true;
                }
            }

            Thread.sleep(2000);
        }

        return hasNewFile;
    }

    // 起诉书
    private JCYDocument getAndGenerateQiSuShu(String url, String date, String title) throws Exception {
        if (this.checkFileExist(title, date)) {
            return null;
        }

        Document qiSuShuPage;
        try {
            qiSuShuPage = HTMLUtils.parseHtmlAsDocument(url);
        } catch (PageNotExistException ex) {
            Log.log(String.format("Document with title %s, url %s, data %s not exist", title, url, date));
            return null;
        }

        Element article = qiSuShuPage.selectFirst("div#" + WebPageSettings.ARTICLE_CONTENT_ID);

        StringBuilder sb = new StringBuilder();

        for (Element p : article.select(WebPageSettings.HTML.Paragraph)) {
            Element span = p.selectFirst(WebPageSettings.HTML.Span);
            if (span == null) {
                sb.append(p.text() + "\n");
            } else {
                sb.append(span.text() + "\n");
            }
        }

        Log.log(sb.toString());

        JCYDocument document = new JCYDocument(date, sb.toString(), url, title);

        return document;
    }

    private void initialExistFilesMap() {
        List<File> allFiles = FileUtils.getAllFiles(this.ROOT_FOLDER);
        for (File file : allFiles) {

        }
    }

    private boolean checkFileExist(String title, String date) {
        return false;
    }
}
