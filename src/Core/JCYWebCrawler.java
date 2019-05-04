package Core;

import Settings.WebPageSettings;
import Tools.HTMLUtils;
import Tools.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by muwang on 5/3/2019.
 */
public final class JCYWebCrawler extends WebCrawlerBase {

    private static final String ROOT_PAGE = String.format(WebPageSettings.FLWS_FORMAT, WebPageSettings.FLWS_ROOT);


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
            // link
            Element ajh = li.selectFirst("div.crow > div.ajh");
            String link = ajh.selectFirst("a[href]").attr(WebPageSettings.HTML.Href);
            Log.log(link);

            // sj - 时间
            Element time = li.selectFirst("div.crow > div.sj > span");
            String timeStr = time.text();
            Log.log(timeStr);
        }
    }

    @Override
    protected void beforeEnd() {

    }
}
