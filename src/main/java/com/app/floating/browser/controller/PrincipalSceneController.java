package com.app.floating.browser.controller;

import com.app.floating.browser.utils.FXUtils;
import com.app.floating.browser.constants.FXMLNames;
import com.app.floating.browser.constants.AppLabels;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Controller for the Main window
 * 
 * @author Emmanuel Hz
 */
public class PrincipalSceneController {
    
    /**
     * Reference to the principal Stage
     */
    private final Stage STAGE;
    
    /**
     * Reference to the Tab Container in the view
     */
    @FXML
    private TabPane tabContainer;
    
    /**
     * Reference to the New Tab 
     * 
     * Actually, the new Tab button is a tab, but because the tab
     * has no text, gives the impression that the tab is a button
     */
    @FXML
    private Tab newTabButton;
    
    /**
     * Reference to the first tab in the tab container
     * 
     * This tab cannot be closed, if it be close, the whole
     * app closes as well
     */
    @FXML
    private Tab firstTab;
    
    /**
     * Constructor receiving the Stage reference
     * 
     * The stage is needed to change the app title with
     * the current viewing tab
     * 
     * @param STAGE Principal stage
     */
    public PrincipalSceneController(final Stage STAGE) {
        this.STAGE = STAGE;
    }
   
    /**
     * Initialize the components in the view
     */
    @FXML
    private void initialize() {
        
        //Set the event for when click the New tab button
        //Insert the new tab one position before the last tab
        newTabButton.setOnSelectionChanged(e -> {
            int newTabPosition = tabContainer.getTabs().size() - 1;
            
            Tab newTab = getNewTab();
            tabContainer.getTabs().add(newTabPosition, newTab);
            
            tabContainer.getSelectionModel().clearAndSelect(newTabPosition);
            STAGE.setTitle(newTab.textProperty().getValue());
        });
        
        addOnSelectionChangeEvent(firstTab);
        
        //Close the app when the first tab is closed
        firstTab.setOnCloseRequest(e -> {
            STAGE.close();
        });
        
        //Create the navigator for the first tab
        firstTab.setContent(getNavigator(firstTab));
        
        //Select the first tab and not the new tab (new tab button)
        tabContainer.getSelectionModel().selectFirst();
    }
    
    /**
     * Gets a new tab for the tab pane
     * 
     * @return 
     */
    private Tab getNewTab() {
       Tab newTab = new Tab();
       newTab.setContent(getNavigator(newTab));
       addOnSelectionChangeEvent(newTab);
       
       return newTab;
    }
    
    /**
     * Adds a listener to the text property of the specified tab 
     * The listener replace the Stage title with the webpage title
     * 
     * Also the stage title is change when navigating between tabs
     * @param tab 
     */
    private void addOnSelectionChangeEvent(Tab tab) {
       tab.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
           STAGE.setTitle(newValue);
       });
       
       tab.setOnSelectionChanged(e -> {
            STAGE.setTitle(tab.textProperty().getValue());
       });
    }
    
    
    /**
     * Returns an instance of the navigator with no default URL to open
     * 
     * @param parentTab Parent tab to add the navigator
     * 
     * @return The navigator reference
     */
    private Node getNavigator(Tab parentTab) {
        return getNavigator(parentTab, null);
    }
    
    /**
     * Returns an instance of the navigator with a URL to load 
     * 
     * @return the navigator Reference
     */
    private Node getNavigator(Tab parentTab, String urlDefault) {
        try {
            return FXUtils.loadFXML(FXMLNames.NAVIGATOR_SCENE, new NavigatorController(parentTab, urlDefault));
        }
        catch(IOException ex) {
            System.out.println(AppLabels.COULDN_LOAD_NAVIGATOR_LABEL);
        }
        
        return null;
    }
}
