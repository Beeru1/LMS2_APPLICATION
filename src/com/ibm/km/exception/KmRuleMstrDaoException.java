package com.ibm.km.exception;

import com.ibm.lms.exception.DAOException;

public class KmRuleMstrDaoException extends DAOException {

    public KmRuleMstrDaoException(String message) {
        super(message);
    }

    public KmRuleMstrDaoException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
