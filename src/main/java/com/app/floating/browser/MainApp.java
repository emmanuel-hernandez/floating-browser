package com.app.floating.browser;

import com.app.floating.browser.utils.FXUtils;
import com.app.floating.browser.constants.FXMLNames;
import com.app.floating.browser.constants.AppLabels;
import com.app.floating.browser.controller.PrincipalSceneController;
import com.app.floating.browser.utils.Utils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/**
 * Entry point of the app
 * 
 * @author Emmanuel Hz
 */
public class MainApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        Parent root = FXUtils.loadFXML(FXMLNames.PRINCIPAL_SCENE, new PrincipalSceneController(stage));
        
        Scene scene = new Scene(root);
        
        //FXUtils.addCSSToScene(scene, "/styles/Styles.css");

        stage.getIcons().add(Utils.getAppIcon());
        stage.setScene(scene);
        stage.setTitle(AppLabels.APP_TITLE);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
