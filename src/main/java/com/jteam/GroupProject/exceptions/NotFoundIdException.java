package com.jteam.GroupProject.exceptions;

public class NotFoundIdException extends RuntimeException {
    public NotFoundIdException() {
        super();
    }

    public NotFoundIdException(String message) {
        super(message);
    }
}
