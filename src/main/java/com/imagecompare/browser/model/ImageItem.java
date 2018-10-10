package com.imagecompare.browser.model;

/**
 * ############################################################################
 * ImageItem
 * ############################################################################
 *
 * Model class for database.
 *
 * @author Piotr Wróblewski
 */
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

    /**
     * Basic constructor for the ImageItem class.
     * @param id Integer - id of element.
     * @param name String - name of element.
     * @param filename String - filename
     * @param param1 String - parameter 1
     * @param param2 String - parameter 2
     * @param param3 String - parameter 3
     * @param param4 String - parameter 4
     * @param param5 String - parameter 5
     */
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

    /**
     *  Basic constructor for the ImageItem class.
     * @param name String - name of element.
     * @param filename String - filename
     * @param param1 String - parameter 1
     * @param param2 String - parameter 2
     * @param param3 String - parameter 3
     * @param param4 String - parameter 4
     * @param param5 String - parameter 5
     */
    public ImageItem(String name, String filename, String param1, String param2, String param3, String param4, String param5) {
        this.name = name;
        this.filename = filename;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    /**
     * Returns ID of the element.
     * @return Integer - id of the element.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns name of the element.
     * @return String - name of the element.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns filename (with path) of the element.
     * @return String - filename with path.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Returns parameter 1.
     * @return String - parameter 1.
     */
    public String getParam1() {
        return param1;
    }

    /**
     * Returns parameter 2.
     * @return String - parameter 2.
     */
    public String getParam2() {
        return param2;
    }

    /**
     * Returns parameter 3.
     * @return String - parameter 3.
     */
    public String getParam3() {
        return param3;
    }

    /**
     * Returns parameter 4.
     * @return String - parameter 4.
     */
    public String getParam4() {
        return param4;
    }

    /**
     * Returns parameter 5.
     * @return String - parameter 5.
     */
    public String getParam5() {
        return param5;
    }

    /**
     * Checks if values was changed.
     * @return Boolean - true if values was changed.
     */
    public boolean isChanged() {
        return changed;
    }

}
