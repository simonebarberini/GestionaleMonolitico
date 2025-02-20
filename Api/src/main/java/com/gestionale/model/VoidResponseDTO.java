package com.gestionale.model;

public class VoidResponseDTO {
    private String message;
    private boolean success;

    public VoidResponseDTO() {}

    public VoidResponseDTO(String message, boolean success) {
        this.message = message;
        this.success = success;
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
}
