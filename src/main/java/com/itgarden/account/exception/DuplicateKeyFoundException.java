package com.itgarden.account.exception;

/*
 * Created by Suresh Stalin on 20 / Oct / 2020.
 */

public class DuplicateKeyFoundException extends Exception {

    public DuplicateKeyFoundException(String errorMessage) {
        super(errorMessage);
    }
}
