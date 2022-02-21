/**
 * Handles all fille based operations for GUI
 * 
 * @author Erik Cohen
 * @author Dylan Tymkiw
 * @author Muqing Wen
 * @version 1.3
 * @since 1.0
*/

package proj3CohenTymkiwWen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.stage.FileChooser;

public class FileIO {

    FileChooser fileChooser = new FileChooser();
 
    //Set extension filter for text files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    fileChooser.getExtensionFilters().add(extFilter);

    //Show save file dialog
    File file = fileChooser.showSaveDialog(primaryStage);

    if (file != null) {
        saveTextToFile(sampleText, file);
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
 
    
}
