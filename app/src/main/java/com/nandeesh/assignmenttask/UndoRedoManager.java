package com.nandeesh.assignmenttask;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UndoRedoManager {
    private Stack<CharSequence> undoStack;
    private Stack<CharSequence> redoStack;

    public UndoRedoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void addState(CharSequence state) {
        undoStack.push(state);
        redoStack.clear(); // Clear redo stack when a new state is added
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(undoStack.pop());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(redoStack.pop());
        }
    }

    public Stack<CharSequence> getStates() {
        return undoStack;
    }
}