/*
* File: FileIO.java
* Names: Erik Cohen, Dylan Tymkiw, Muqing Wen
* Class: CS 361
* Project 3
* Date: February 16, 2022
*/

package proj3CohenTymkiwWen;


import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;

/**
 * Handles File based functions for CS361 GUI
 * 
 * @author Erik Cohen
 * @author Dylan Tymkiw
 * @author Muqing Wen
 * @version 1.3
 * @since 1.3
*/
public class FileIO
{
    private TabPane editorTabs;


    /**
     * FileIO Class Constructor
     * 
     * @param  Tab The Tab Pane of our GUI //may change to a "Tabs" class
     * @return     an interface for File based operations
    */
    public FileIO(TabPane editorTabs) {
        this.editorTabs = editorTabs;
    }

    // should move to tabs class
    private TextArea textArea(){
        Tab currentTab = this.editorTabs.getSelectionModel().getSelectedItem();
        TextArea textArea = (TextArea) currentTab.getContent();
        return textArea;
    }


}
