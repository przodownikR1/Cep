package pl.java.scalatech.web.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    @Getter
    private final String clazz;
    @Getter
    private final String id;

    public ResourceNotFoundException(String clazz, String id) {
        super();
        this.clazz = clazz;
        this.id = id;
    }

    public ResourceNotFoundException(String message, Throwable cause, String clazz, String id) {
        super(message, cause);
        this.clazz = clazz;
        this.id = id;
    }

    public ResourceNotFoundException(String message, String clazz, String id) {
        super(message);
        this.clazz = clazz;
        this.id = id;
    }

    public ResourceNotFoundException(Throwable cause, String clazz, String id) {
        super(cause);
        this.clazz = clazz;
        this.id = id;
    }

}
