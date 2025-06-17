package com.example.models;

public class TinnhanItem {
    private String message;
    private boolean isSender;
    private boolean isPinned;

    public TinnhanItem(String message, boolean isSender) {
        this.message = message;
        this.isSender = isSender;
        this.isPinned = false;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSender() {
        return isSender;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

}
