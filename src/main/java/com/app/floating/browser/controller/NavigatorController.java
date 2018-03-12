package com.app.floating.browser.controller;

import com.app.floating.browser.constants.AppLabels;
import com.app.floating.browser.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Controller for the Navigator component
 * 
 * @author Emmanuel Hz
 */
public class NavigatorController {
    
    /**
     * Reference to the Parent tab where the navigator is showing
     */
    private final Tab PARENT_TAB;
    
    /**
     * Default URL to load when the new instance is created
     */
    private final String URL_DEFAULT;
    
    /**
     * Reference to the text field component in the view
     */
    @FXML
    private TextField addressBar;
    
    /**
     * Reference to the web view component in the view
     */
    @FXML
    private WebView webView;
    
    /**
     * Constructor receiving the Parent tab where the navigator is loaded
     * 
     * @param PARENT_TAB Reference to the parent tab
     */
    public NavigatorController(final Tab PARENT_TAB) {
        this(PARENT_TAB, null);
    }
    
    /**
     * Constructor receiving the Parent tab where the navigator is loaded
     * and a URL to load
     * 
     * @param PARENT_TAB Reference to the parent tab
     * @param URL_DEFAULT Default URL to load in the navigator
     */
    public NavigatorController(final Tab PARENT_TAB, final String URL_DEFAULT) {
        this.PARENT_TAB = PARENT_TAB;
        this.URL_DEFAULT = URL_DEFAULT;
    }
    
    /**
     * Initialize the components in the layout view
     */
    @FXML
    private void initialize() {
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue ov, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    setParentTabTitle(getPageTitle());
                }
            }
        });
        
        addressBar.setText(URL_DEFAULT);
        
        if(addressBar.getText() == null || addressBar.getText().trim().length() == 0) {
            setParentTabTitle(AppLabels.NEW_TAB_LABEL);
        }
        else {
            goToURLOnAddressBar();
        }
    }

    /**
     * Event fired when the key Enter is pressed over the URL Bar
     * 
     * @param event Action Event that encapsulate the event
     */
    @FXML
    private void onEnterAddressBar(ActionEvent event) {
        goToURLOnAddressBar();
    }
    
    /**
     * Gets the URL sanitize and well formed from the URL bar
     * 
     * @return URL with HTTP and WWW parts
     */
    private String getURLFromURLBar() {
        return Utils.sanitizeUrl(addressBar.getText().trim());
    }
    
    /**
     * Go to the URL specified in the URL Bar
     */
    private void goToURLOnAddressBar() {
        goToURL(getURLFromURLBar());
    }
    
    /**
     * Go to the URL specified 
     * 
     * @param URL well formed to load in the navigator
     */
    private void goToURL(final String URL) {
        System.out.println(AppLabels.LOADING_LABEL + " " + URL);
        
        addressBar.setText(URL);
        addressBar.positionCaret(addressBar.getText().length());
        PARENT_TAB.setText(AppLabels.LOADING_LABEL);
        webView.getEngine().load(URL);
    }
    
    /**
     * Set the specified title in the Text Property of the Parent TAB
     * 
     * @param TAB_TITLE a title for the parent tab
     */
    private void setParentTabTitle(final String TAB_TITLE) {
        PARENT_TAB.textProperty().setValue(TAB_TITLE);
    }
    
    /**
     * Gets the webpage title if exists in the HTML document
     * 
     * @return a title if it is found
     */
    private String getPageTitle() {
        String title = webView.getEngine().getLocation();
        
        Document document = webView.getEngine().getDocument();
 
        NodeList heads = document.getElementsByTagName("head");
        if (heads.getLength() > 0) {
            Element head = (Element)heads.item(0);
            
            NodeList titles = head.getElementsByTagName("title");
            if (titles.getLength() > 0) {
                Node titleElement = titles.item(0);
                title = titleElement.getTextContent();
            }
        }

        return title;
    }
}
