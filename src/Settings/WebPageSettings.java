package Settings;

/**
 * Created by muwang on 4/29/2019.
 */
public final class WebPageSettings {

    public final static int HTTP_NOT_FOUND = 404;

    public final static String JCY_ROOT = "http://www.ajxxgk.jcy.gov.cn/";

    // 法律文书
    public final static String FLWS_ROOT = JCY_ROOT + "html/zjxflws/";

    public final static String ROOT_INDEX = "index";

    public final static String HTML_SUFFIX = ".html";

    // 法律文书
    public final static String FLWS_FORMAT = FLWS_ROOT + "%s" + HTML_SUFFIX;

    // 法律文书 - 不起诉决定书
    public final static String FLWS_BQSJDS_PAGE_ID = "page_3";

    public final static String ARTICLE_CONTENT_ID = "Article";

    public final static class HTML {
        public final static String Href = "href";

        public final static String Title = "title";

        public final static String Paragraph = "p";

        public final static String Span = "span";
    }
}
