package com.product.api.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

public class DBAccessException extends DataAccessException {

    public DBAccessException(DataAccessException e) {
        super(e.getMessage());
    }
}