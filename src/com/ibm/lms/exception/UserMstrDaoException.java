package com.ibm.lms.exception;


public class UserMstrDaoException extends DAOException {

    public UserMstrDaoException(String message) {
        super(message);
    }

    public UserMstrDaoException(String message, Throwable throwable) {
        super(message,throwable);
    }

}
