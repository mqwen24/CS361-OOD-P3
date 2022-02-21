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




import javafx.fxml.FXMLLoader;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application{
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
