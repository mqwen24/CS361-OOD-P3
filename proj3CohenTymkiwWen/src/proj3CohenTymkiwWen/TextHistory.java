package proj3CohenTymkiwWen;

import java.util.Stack;

public class TextHistory
{
    private Stack<String> undo ;
    private Stack<String> redo;

    public TextHistory(){
        this.undo = new Stack<String>();
        this.redo = new Stack<String>();
    }

    public void save(String save) {
        this.redo.clear();
        this.undo.add(save);
    }

    public String undo() {
        if(! this.undo.isEmpty()){
            String lastState = this.undo.pop();
            this.redo.add(lastState);
            return this.undo.peek();
        }
        return null;
    }

    public String redo(){
        if(! this.redo.isEmpty()){
            return this.redo.pop();
        }
        return null;
    }
}


