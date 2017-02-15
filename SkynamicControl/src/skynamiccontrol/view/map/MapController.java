package skynamiccontrol.view.map;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class MapController
 *
 * @author Aurelien Brugalieres
 */
public class MapController implements Initializable {

    @FXML
    private WebView webView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine webEngine = webView.getEngine();

        webEngine.load("file:/home/aurelien/IdeaProjects/ChefDOeuvre/SkynamicControl/src/skynamiccontrol/view/map/map_api/index.html");


    }
}