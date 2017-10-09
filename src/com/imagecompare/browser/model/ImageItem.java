package com.imagecompare.browser.model;

public class ImageItem {

    private String name;
    private String filename;
    private String[] params;

    public ImageItem(String name, String filename, String[] params) {
        this.name = name;
        this.filename = filename;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}