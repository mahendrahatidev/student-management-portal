package com.questglobal.student.response;
public class PortalResponse<T> {
    private T response;
    private ErrorResponse error;

    public PortalResponse(T response) {
        this.response = response;
    }

    public PortalResponse(ErrorResponse error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}
