package com.example.models;

public class TimkiemlvItem {
    private String title;
    private int leftIcon;
    private Integer rightIcon;

    public TimkiemlvItem(String title, int leftIcon, Integer rightIcon) {
        this.title = title;
        this.leftIcon = leftIcon;
        this.rightIcon = rightIcon;
    }

    public String getTitle() {
        return title;
    }

    public int getLeftIcon() {
        return leftIcon;
    }

    public Integer getRightIcon() {
        return rightIcon;
    }

}
