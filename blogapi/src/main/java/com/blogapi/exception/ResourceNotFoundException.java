package com.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException  //Status code 404
{

    public ResourceNotFoundException(long id) {
        // here super keyword print this message in the response
        super("Resource Not Found For id: " + id);
    }
}
