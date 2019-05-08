package Core;

import Tools.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by muwang on 4/29/2019.
 */
public abstract class WebCrawlerBase implements Runnable {

    protected Map<String, String> visitedPages;

    public WebCrawlerBase() {
        this.visitedPages = new HashMap<>();
    }

    protected abstract void onStart() throws Exception;

    protected abstract void inProcess() throws Exception;

    protected abstract void beforeEnd() throws Exception;

    @Override
    public void run() {
        try {
            this.onStart();

            this.inProcess();

            this.beforeEnd();
        } catch (Exception ex) {
            Log.log(ex.toString());
        }
    }
}
