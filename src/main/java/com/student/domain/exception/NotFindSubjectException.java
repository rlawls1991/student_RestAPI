package com.student.domain.exception;

public class NotFindSubjectException extends RuntimeException{
    public NotFindSubjectException(){
        super();
    }

    public NotFindSubjectException(String message) {
        super(message);
    }

    public NotFindSubjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFindSubjectException(Throwable cause) {
        super(cause);
    }
}
