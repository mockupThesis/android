package hu.webandmore.androidmocapclient.app.interactors.events;


import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

public class WiFiEvent {

    private int code;
    private WiFiModel wiFi;
    private Throwable throwable;
    private String errorMessage;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WiFiModel getWiFi() {
        return wiFi;
    }

    public void setWiFi(WiFiModel wiFi) {
        this.wiFi = wiFi;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
