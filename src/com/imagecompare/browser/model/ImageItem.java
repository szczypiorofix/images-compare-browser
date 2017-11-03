package com.imagecompare.browser.model;

public class ImageItem {

    public static final String PARAM_ID_TITLE = "ID";
    public static final String PARAM_NAME_TITLE = "Nazwa";
    public static final String PARAM_FILENAME_TITLE = "Plik";
    public static final String PARAM_PARAM1_TITLE = "Parametr 1";
    public static final String PARAM_PARAM2_TITLE = "Parametr 2";
    public static final String PARAM_PARAM3_TITLE = "Parametr 3";
    public static final String PARAM_PARAM4_TITLE = "Parametr 4";
    public static final String PARAM_PARAM5_TITLE = "Parametr 5";

    private int id;
    private String name;
    private String filename;
    private String param1, param2, param3, param4, param5;
    private boolean changed;

    public ImageItem(int id, String name, String filename, String param1, String param2, String param3, String param4, String param5) {
        this.id = id;
        this.name = name;
        this.filename = filename;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        changed = false;
    }

    public ImageItem(String name, String filename, String param1, String param2, String param3, String param4, String param5) {
        this.name = name;
        this.filename = filename;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public String getParam3() {
        return param3;
    }

    public String getParam4() {
        return param4;
    }

    public String getParam5() {
        return param5;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
