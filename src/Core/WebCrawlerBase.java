package Core;

import Tools.Log;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by muwang on 4/29/2019.
 */
public abstract class WebCrawlerBase implements Runnable {

    protected Set<String> visitedPages;

    public WebCrawlerBase() {
        this.visitedPages = new HashSet<>();
    }

    protected abstract void onStart();

    protected abstract void inProcess() throws Exception;

    protected abstract void beforeEnd();

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
