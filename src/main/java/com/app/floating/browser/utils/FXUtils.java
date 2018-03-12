package com.app.floating.browser.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Util methods for the Java FX logic
 * 
 * @author Emmanuel Hz
 */
public final class FXUtils {
    
    /**
     * 
     */
    private FXUtils() {
    }
 
    /**
     * Loads a FXML file and returns the node parent
     * 
     * @param fxmlPath
     * @param controller
     * @return
     * @throws IOException 
     */
    public static Parent loadFXML(final String fxmlPath, final Object controller) throws IOException {
        FXMLLoader loader =  new FXMLLoader(FXUtils.class.getResource(fxmlPath));
        loader.setController(controller);

        return loader.load();
    }
    
    /**
     * Adds a CSS stylesheet to the specified scene
     * 
     * @param scene Scene to style
     * @param cssPath path to the stylesheet
     */
    public static void addCSSToScene(final Scene scene, String cssPath) {
        scene.getStylesheets().add(cssPath);
    }
}
