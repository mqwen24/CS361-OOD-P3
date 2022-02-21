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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.Map;
import java.util.HashMap;
import javafx.scene.input.KeyEvent;
import java.io.*;

public class Main extends Application{

    private Map<Tab, TextHistory> textHistory 
        = new HashMap<Tab, TextHistory>();

    private Map<Tab, String> filePaths
        = new HashMap<Tab, String>();

    private Clipboard systemClipboard = Clipboard.getSystemClipboard();

    private FileChooser fileChooser = new FileChooser();

    @FXML
    private Button helloButton;

    @FXML
    private TabPane editorTabs;

    /**
    * Opens a new Tab
    */
    @FXML
    void newFile(ActionEvent event) {
        Tab tab = new Tab("Untitled Tab");
        editorTabs.getTabs().add(tab);
        tab.setContent(new TextArea("Sample text"));
    }

    /**
    * prompts user to open a file from their directory
    * sets the current tab name to the new file name 
    */
    @FXML
    void openFile(ActionEvent event) {
        Stage thisStage = (Stage) textArea().getScene().getWindow();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(thisStage);
        if (file != null){
            textArea().setText(readFile(file));
            currentTab().setText(file.getName());
            this.filePaths.put(currentTab(),file.getPath());
        }
    }
    
    /**
    * If the current tab is representative of an existing tab this method
    * saves edits
    * Else calls the saveFileAs method
    */
    @FXML
    void saveFile(ActionEvent event) {
        String filepath = this.filePaths.get(currentTab());
        if(filepath != null){
            File currentFile = new File(filepath);
            System.out.println(currentFile);
            if (currentFile.exists()){
                SaveFile(textArea().getText(), currentFile);
            }
            else{
                saveFileAs(event);
            }
        }else{
            saveFileAs(event);
        }
    }

    /**
    * prompts user to save file with a specific name
    * sets the current tab name to the new file name 
    */
    @FXML
    void saveFileAs(ActionEvent event) {
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        Stage thisStage = (Stage) textArea().getScene().getWindow();
        File file = fileChooser.showSaveDialog(thisStage);
        if(file != null){
            SaveFile(textArea().getText(), file);
            //set name of tab to name of file
            currentTab().setText(file.getName());
            this.filePaths.put(currentTab(),file.getPath());
        }
    }

    /**
    * Checks if file is saved and exits
    * if file is not saves, the user is prompted to save
    */
    @FXML
    void closeFile(ActionEvent event) {
        String filepath = this.filePaths.get(currentTab());
        if(filepath != null){
            File currentFile = new File(filepath );
            if (currentFile.exists()){
                String savedText = readFile(currentFile);
                if(savedText.equals(textArea().getText())){
                    currentTab().getTabPane().getTabs().remove(currentTab());
                }else{
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Current project is modified");
                    alert.setContentText("Save?");
                    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
                    alert.showAndWait().ifPresent(type -> {
                        System.out.println(type.toString());
                        if (type.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                            System.out.println("ok pressed");
                            saveFile(event);
                            currentTab().getTabPane().getTabs().remove(currentTab());
                        } else if (type.getButtonData().equals(ButtonBar.ButtonData.NO)){
                            System.out.println("no pressed");
                            currentTab().getTabPane().getTabs().remove(currentTab());
                        } else {
                            System.out.println("cancel pressed");
                            return;
                        }
                    });
                }
            }else{
                saveFileAs(event);
                currentTab().getTabPane().getTabs().remove(currentTab());
            }
        }else{
            saveFileAs(event);
            currentTab().getTabPane().getTabs().remove(currentTab());
        }
    }

    /**
    * copy selected text to clipboard
    */
    @FXML
    void copy(ActionEvent event) {

        ClipboardContent content = new ClipboardContent();
        content.putString(textArea().getSelectedText());
        systemClipboard.setContent(content);
    }

    /**
    * cuts selected text to clipboard
    */
    @FXML
    void cut(ActionEvent event) {
        ClipboardContent content = new ClipboardContent();
        content.putString(textArea().getSelectedText());
        systemClipboard.setContent(content);
        textArea().replaceSelection("");
    }

    /**
    * pastes clipboard text into current file.
    */
    @FXML
    void paste(ActionEvent event) { 
        String clipboardText = systemClipboard.getString();
        textArea().insertText(textArea().getCaretPosition(), clipboardText);
    }

    /**
    * highlights all text in current tab 
    */
    @FXML
    void selectAll(ActionEvent event) {
        textArea().selectAll();
    }

    @FXML
    /*
    *   save current content of text area when (any) key is pressed
     */
    void saveState(KeyEvent event) {
        TextHistory currentHistory = this.textHistory.get(currentTab());
        if(currentHistory != null){
            //System.out.println("Add to existing id:" + currentTab());
            currentHistory.save(textArea().getText());

        }else{
            //System.out.println("Create history for id:" + currentTab());
            this.textHistory.put(currentTab(), new TextHistory());
            //System.out.println("First add");
            currentHistory = this.textHistory.get(currentTab());
            currentHistory.save(textArea().getText());
        }
    }

    /**
    * undoes an action if there is undo history
    */
    @FXML
    void undo(ActionEvent event) {
        TextHistory tabHistory = this.textHistory.get(currentTab());
        String lastState = tabHistory.undo();
        if(lastState != null){
            //System.out.println("undo id:" + currentTab());
            textArea().setText(lastState);
            return;
        }
        //System.out.println("no undo");
    }

    /**
    * redoes an action if there is redo history
    */
    @FXML
    void redo(ActionEvent event) {
        TextHistory tabHistory = this.textHistory.get(currentTab());
        String nextState = tabHistory.redo();
        if(nextState != null){
            //System.out.println("redo id:" + currentTab());
            textArea().setText(nextState);
            return;
        }
        //System.out.println("no redo");
    }

    @FXML
    void exitProgram(ActionEvent event) {
        //check if all saved
        for (Tab tab : editorTabs.getTabs()){
            closeFile(event);
        }
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

    //Helper method to save files
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

    //Helper method to read files
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        
        try {

            bufferedReader = new BufferedReader(new FileReader(file));
            
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        return stringBuffer.toString();
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
