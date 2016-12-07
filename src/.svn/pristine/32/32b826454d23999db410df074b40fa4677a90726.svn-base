package com.ibm.lms.engine.helper;

import java.util.HashMap;
import java.util.Map;

import com.ibm.lms.engine.exception.LmsException;

/**
 * 
 * Cached Application Instance Helper provides the chached value of Application
 * Instance of that country
 * 
 * @verion 0.1
 */
public class CachedApplicationInstanceHelper {

    private static Map cachedValues = null;

    private static CachedApplicationInstanceHelper instance = null;

    private CachedApplicationInstanceHelper() {
        cachedValues = new HashMap();
    }

    /**
     * returns singleton object of CachedApplicationInstanceHelper
     * 
     * @throws LmsException
     * @return
     */
    public static CachedApplicationInstanceHelper getInstance() {
        if (instance == null) {
            instance = new CachedApplicationInstanceHelper();
        }
        return instance;
    }

    
}