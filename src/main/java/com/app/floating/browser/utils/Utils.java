package com.app.floating.browser.utils;

import com.app.floating.browser.constants.AppPaths;
import javafx.scene.image.Image;

/**
 * Utilities methods for the app
 * 
 * @author Emmanuel Hz
 */
public final class Utils {
    
    /**
     * Contructs a well formed URL
     * 
     * Adds the HTTP protocol or the WWW if needed
     * 
     * @param possibleUrl
     * 
     * @return a well formed URL
     */
    public static String sanitizeUrl(final String possibleUrl) {
       String url = possibleUrl;
       
       final String httpPrefix = "http://";
       final String wwwPrefix = "www.";
       final String fullPrefix = httpPrefix + wwwPrefix;
       
       if(possibleUrl != null && !possibleUrl.isEmpty()) {
           if(possibleUrl.startsWith(httpPrefix) && !possibleUrl.substring(httpPrefix.length()).startsWith(wwwPrefix)) {
               url = fullPrefix + possibleUrl.substring(httpPrefix.length());
           }
           else if(possibleUrl.startsWith(wwwPrefix)) {
               url = httpPrefix + possibleUrl;
           }
           else {
               url = fullPrefix + possibleUrl;
           }
       }
       
       return url;
    }
    
    /**
     * Returns the specified image from the resource images directory
     * 
     * @param IMAGE_NAME name of the image in the resource directory to load
     * @return an image object
     */
    public static Image getImageFromResources(final String IMAGE_NAME) {
        return new Image(Utils.class.getResourceAsStream(AppPaths.IMAGES + IMAGE_NAME));
    }
    
    /**
     * Gets the app icon
     * 
     * @return an image object
     */
    public static Image getAppIcon() {
        return getImageFromResources("icon.png");
    }
    
}
