package Core;

import Settings.WebPageSettings;
import Tools.HTMLUtils;
import Tools.StringUtils;
import org.jsoup.nodes.Document;

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
        Document rootPage = HTMLUtils.parseHtmlAsDocument(ROOT_PAGE);


    }

    @Override
    protected void beforeEnd() {

    }
}
