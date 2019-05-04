package Core;

import Settings.WebPageSettings;
import Tools.FileUtils;
import Tools.HTMLUtils;
import Tools.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.List;

/**
 * Created by muwang on 5/3/2019.
 */
public final class JCYWebCrawler extends WebCrawlerBase {

    private static final String ROOT_PAGE = String.format(WebPageSettings.FLWS_FORMAT, WebPageSettings.FLWS_ROOT);

    private static String ROOT_FOLDER;

    public JCYWebCrawler(String rootFolder) {
        this.ROOT_FOLDER = rootFolder;
        this.initialExistFilesMap();
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void inProcess() throws Exception {
        Document rootPage = HTMLUtils.parseHtmlAsDocument(WebPageSettings.FLWS_ROOT);

        // 不起诉决定书
        Element tab_3_bqsjds = rootPage.selectFirst("div#" + WebPageSettings.FLWS_BQSJDS_PAGE_ID);

        Element content_ul = tab_3_bqsjds.selectFirst("ul");

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

            this.getAndGenerateQiSuShu(WebPageSettings.JCY_ROOT + link, timeStr, title);
            Thread.sleep(2000);
        }
    }

    @Override
    protected void beforeEnd() {

    }

    // 起诉书
    private void getAndGenerateQiSuShu(String url, String date, String title) throws Exception {
        if (this.checkFileExist(title, date)) {
            return;
        }

        Document qiSuShuPage = HTMLUtils.parseHtmlAsDocument(url);
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
