package proj3CohenTymkiwWen;

import java.util.ArrayList;

public class TextHistory
{
    private ArrayList<String> history;
    private Integer historyPointer;

    public void editorHistory(){
        this.history = new ArrayList<String>();
        this.historyPointer = -1;
    }

    public void save(String save) {
        if(canRedo()){
            //clear redos so new save is at end of tracked changes
            this.history = 
                        new ArrayList<String>(this.history.subList(0, historyPointer));
        }
        this.history.add(save);
        historyPointer++;
    }

    public String backtrack() {
        if(this.historyPointer < 0){
            return null;
        }
        return this.history.get(--historyPointer);
    }

    public String redo(){
        if(!canRedo()){
            return null;
        }
        return this.history.get(++historyPointer);
    }

    private Boolean canRedo(){
        if(this.history.size() > this.historyPointer + 1){
            return true;
        }
        return false;
    }
}


