package com.martdbj.backend.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id " + id + " already exists in our records");
    }
}
