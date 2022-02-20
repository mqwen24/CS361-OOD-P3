/**
 * CS361 OOP project 3 GUI iteration
 * 
 * @author Erik Cohen
 * @author Dylan Tymkiw
 * @author Muqing Wen
 * @version 1.3
 * @since 1.0
*/
package proj3CohenTymkiwWen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Dictionary;


public class Main extends Application{

    private Dictionary<String, TextHistory> textHistory;

    @FXML
    private Button helloButton;

    @FXML
    private TabPane editorTabs;


    @FXML
    void newFile(ActionEvent event) {
        Tab tab = new Tab("Tab");
        editorTabs.getTabs().add(tab);
        tab.setContent(new TextArea("Sample text"));
        this.textHistory.put(tab.getId(), new TextHistory());
    }

    /**
    * prompts user to open a file from their directory
    * sets the current tab name to the new file name 
    */
    @FXML
    void openFile(ActionEvent event) {

    }
    
    /**
    * If the current tab is representative of an existing tab this method
    * saves edits
    * Else calls the saveFileAs method
    */
    @FXML
    void saveFile(ActionEvent event) {

    }

    /**
    * prompts user to save file with a specific name
    * sets the current tab name to the new file name 
    */
    @FXML
    void saveFileAs(ActionEvent event) {

    }

    /**
    * Checks if file is saved and exits
    */
    @FXML
    void closeFile(ActionEvent event) {

    }

    /**
    * copy selected text to clipboard
    */
    @FXML
    void copy(ActionEvent event) {

    }

    /**
    * cuts selected text to clipboard
    */
    @FXML
    void cut(ActionEvent event) {

    }

    /**
    * pastes clipboard text into current file.
    */
    @FXML
    void paste(ActionEvent event) {

    }

    /**
    * highlights all text in current tab 
    */
    @FXML
    void selectAll(ActionEvent event) {

    }

    /**
    * undoes an action if there is undo history
    */
    @FXML
    void undo(ActionEvent event) {
        //this.textHistory.get(currentTab().getId()).backtrack(); -- can be null if no undo 
    }

    /**
    * redoes an action if there is redo history
    */
    @FXML
    void redo(ActionEvent event) {
        //this.textHistory.get(currentTab().getId()).redo();-- can be null if no redo 
    }

    @FXML
    void exitProgram(ActionEvent event) {
        //check if all saved
        System.exit(0);
    }

    @FXML
    void getAbout(ActionEvent event) {
        // Sets up a Dialogue in form of an Alert
        Alert aboutDialogue = new Alert(AlertType.INFORMATION); 

        aboutDialogue.setTitle("About");
        //Set the content of the dialogue box
        aboutDialogue.setContentText("This actually took some time to figure out.\n Authors:\n Muqin Wen, Erik Cohen, Dylan Tymkiw");
        aboutDialogue.show();
    }

    /**
    * An action method, activated when fx:id="goodbyeButton" recieves an interaction.
    * This method appends "Goodbye" to the central text area.
    */
    @FXML
    void goodbyePressed(ActionEvent event) {
        textArea().appendText("Goodbye");
    }

    /**
    * An action method, activated when fx:id="helloButton" recieves an interaction.
    * This method brings up a TextInputDialog box which recieves an entry,
    * and sets the text of fx:id="helloButton" to the result.
    */
    @FXML
    void helloPressed(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        
        /* Sets up the scene and shows the entry until it is submitted. */
        dialog.setTitle("Give me a number");
        dialog.setHeaderText("Give me an integer from 0 to 255:");
        Optional<String> userInput = dialog.showAndWait();
        /* If the user has an input, sets the text of fx:id="helloButton" to it */
        if(userInput.isPresent()){ 
            helloButton.setText(userInput.get());
        }
    }

    
    /**
     * The main entry point for all JavaFX applications.
     * Called on the JavaFX Application Thread.
     * Creates GUI using FXML loader
     *
     * @param  primaryStage  Main window used by Application
     * @see        Application
    */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            this.textHistory.put(currentTab().getId(), new TextHistory());
            scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
            primaryStage.setTitle("EC, DT, MW et al.'s Project 2");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * helper method to get the selected tab
     * 
     * @return TextArea the textarea of the selected tab
    */
    private Tab currentTab(){
        return this.editorTabs.getSelectionModel().getSelectedItem();
    }

    /**
     * helper method to get the textArea from the opened tab
     * 
     * @return TextArea the textarea of the selected tab
    */
    private TextArea textArea(){
        TextArea textArea = (TextArea) currentTab().getContent();
        return textArea;
    }

    /**
     * Creates a window with interactable buttons
     * 
     * @param  args Command line arguments used in Application "launch" method
     * @return     an interactable window
     * @see        start
    */
    public static void main(String[] args) {
        launch(args);
    }
}
