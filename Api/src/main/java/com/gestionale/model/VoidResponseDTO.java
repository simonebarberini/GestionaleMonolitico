package com.gestionale.model;

public class VoidResponseDTO {
    private String message;
    private boolean success;
    private Exception exception;

    public VoidResponseDTO() {}

    public VoidResponseDTO(String message, boolean success, Exception exception) {
        this.message = message;
        this.success = success;
        this.exception = exception;
    }

    // Getter e Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
