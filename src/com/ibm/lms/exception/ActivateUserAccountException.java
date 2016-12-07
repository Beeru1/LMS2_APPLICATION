package com.ibm.lms.exception;


public class ActivateUserAccountException extends DAOException {

    public ActivateUserAccountException(String message) {
        super(message);
    }

    public ActivateUserAccountException(String message, Throwable throwable) {
        super(message,throwable);
    }

}