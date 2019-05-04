package UX;

import Core.JCYWebCrawler;
import Settings.WebPageSettings;
import Tools.HTMLUtils;
import Tools.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        HTMLUtils.openFiddlerDebug();


//        Document doc = HTMLUtils.parseHtmlAsDocument(String.format(WebPageSettings.FLWS_FORMAT, WebPageSettings.ROOT_INDEX));
//        Log.log(doc);

        JCYWebCrawler webCrawler = new JCYWebCrawler(".");
        webCrawler.run();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
