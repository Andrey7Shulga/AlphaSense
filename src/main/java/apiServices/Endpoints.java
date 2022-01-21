package apiServices;

import config.ConfigReader;

public class Endpoints {

    private static final String KEYWORD_SEARCH = "/keyword-search/";
    private static final String PUBLIC_DOCUMENT_DATA = "/services/i/public-document-data/document";
    private final static String DOC_LABEL = ConfigReader.getInstance().getProperty("document");


    public static String getCurrentDocInfo() {
        return PUBLIC_DOCUMENT_DATA + DOC_LABEL + KEYWORD_SEARCH;
    }

}
