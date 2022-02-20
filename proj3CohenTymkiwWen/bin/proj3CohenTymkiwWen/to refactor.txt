package proj3CohenTymkiwWen;
/*
* File: Main.java
* Names: Jasper Loverude, Muqing Wen, Matthew Cerrato
* Class: CS 361
* Project 2
* Date: February 14, 2022
*/




import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
*
* A simple GUI display, with which the user can interact through a menu,
* TextArea, as well as two buttons.
*
* @author Jasper Loverude, Muqing Wen, Matthew Cerrato 
*/
public class Main extends Application{

    /* FXML object fields */
    @FXML
    private TextArea textArea;

    @FXML
    private Button helloButton;

    @FXML
    private Button goodbyeButton;

    // @FXML
    // private MenuItem menuClearButton; //We have to delete this I think

    @FXML
    private MenuItem menuAboutButton;

    @FXML
    private MenuItem menuNewButton;

    @FXML
    private MenuItem menuOpenButton;

    @FXML
    private MenuItem menuSaveButton;

    @FXML
    private MenuItem menuSaveAsButton;

    @FXML
    private MenuItem menuCloseButton;

    @FXML
    private MenuItem menuExitButton;

    @FXML
    private TabPane tabPane;



    /**
    *
    * An action method, activated when fx:id="helloButton" recieves an interaction.
    * This method brings up a TextInputDialog box which recieves an entry,
    * and sets the text of fx:id="helloButton" to the result.
    *
    * @author  Jasper Loverude, Muqing Wen, Matthew Cerrato
    * @return  void
    * @see     A TextInputDialog box into with a user entry box.
    */
    public void helloButtonPressed()
    {

        TextInputDialog dialog = new TextInputDialog();
        
        /* Sets up the scene and shows the entry until it is submitted. */
        dialog.setTitle("Give me a number");
        dialog.setHeaderText("Give me an integer from 0 to 255:");

        Optional<String> userInput = dialog.showAndWait();

        /* If the user has an input, sets the text of fx:id="helloButton" to it */
        if(userInput.isPresent())
        { 
            
            helloButton.setText(userInput.get());

        }
        

    }


    /**
    *
    * An action method, activated when fx:id="goodbyeButton" recieves an interaction.
    * This method appends "Goodbye" to the central text area.
    *
    * @author Jasper Loverude, Muqing Wen, Matthew Cerrato
    * @return void
    * @see    An addition to textArea.
    */
    public void goodbyeButtonPressed()
    {

        this.textArea.appendText("Goodbye");

    }

    /**
    *
    * An action method, activated when fx:id="menuClearButton" recieves an interaction.
    * This method brings sets the text of the textArea back to "Sample Text"
    * Additionally, if fx:id="helloButton" had it's text changed, resets it to "Hello"
    *
    * @author  Jasper Loverude, Muqing Wen, Matthew Cerrato
    * @return  void
    * @see     Resets the GUI application to default state.
    */
    public void menuClearButtonPressed()
    {

        this.textArea.setText("Sample Text");

        this.helloButton.setText("Hello");

    }

     /**
    *
    * An action method, activated when fx:id="menuExitButton" recieves an interaction.
    * This method exits the application
    *
    * @author  Jasper Loverude, Muqing Wen, Matthew Cerrato
    * @return  void
    * @see     Window closes
    */
    public void menuExitButtonPressed()
    {

        System.exit(0);

    }

    /**
    *
    * An action method, activated when fx:id="menuExitButton" recieves an interaction.
    * This method exits the application
    *
    * @author  Dylan Tymkiw, Muqing Wen, Erik Cohen
    * @return  void
    * @see     About window pops up
    */
    
    public void menuAboutButtonPressed(){
        // Sets up a Dialogue in form of an Alert
        Alert aboutDialogue = new Alert(AlertType.INFORMATION); 
        
        aboutDialogue.setTitle("About");
        //Set the content of the dialogue box
        aboutDialogue.setContentText("This actually took some time to figure out.\n Authors:\n Muqin Wen, Erik Cohen, Dylan Tymkiw");

        aboutDialogue.show();
    }


    // This the correct form of doing it I think, but We have to move it to the start method
    // public void menuSaveAsButtonPressed(){
    //     FileChooser fileChooser = new FileChooser();
    //     fileChooser.setTitle("Save as");
    //     fileChooser.showSaveDialog(stage);
    // }

    //This creates a new tab but we need to make it the topmost one
    @FXML
    public void menuNewButtonPressed(){
        Tab tab = new Tab("Tab");
        tabPane.getTabs().add(tab);
        tab.setContent(new TextArea("Sample text"));
    }


    /**
    *
    * The main driver method for the application.
    * Loads FXML file "Main.fxml" containing layout information.
    * Creates scene from fxmlLoader, set's title and displays
    * Loads Stylesheet "Main.css" containing syling information.
    * Override's start method from Application interface
    *
    * @author  Jasper Loverude, Muqing Wen, Matthew Cerrato
    * @param   appStage, which is the application's stage window
    * @return  void
    * @see     GUI display 
    */
    @Override
    public void start(Stage appStage)
    {

        /* Uses tryCatch to catch IOExceptions*/
        try{

            /* Creates fxmlloader object which loads in fxml settings, from "Main.fxml". */
            FXMLLoader fxmlLoaderObject = new FXMLLoader(getClass().getResource("Main.fxml"));

            Parent root = (Parent) fxmlLoaderObject.load();
            Scene appScene = new Scene(root);

            /* Loads in stylesheets from "Main.css" */
            appScene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
            appStage.setTitle("Project 2: J. Loverude, M. Cerato, M.");
            appStage.setScene(appScene);

            appStage.show();
        }
        /*  There are no input/output exceptions that can occur with this file. */
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        
    }


    /**
     * Launches the application from commandline
     * 
     * @param  args from the Command line.
     * @return void
     * @see    GUI display
    */
    public static void main(String[] args) {
        launch(args);
    }


}