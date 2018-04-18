package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

public interface MockupSettingsScreen {

    void fillWiFiSettings(WiFiModel wiFiModel);
    void setWiFiSettings();
    void saveWiFiSetting();
    void showWiFiFeedback(String feedbackMsg, boolean failure);
    boolean checkWiFiArguments();
    void showError(String errorMsg);
    void showProgressBar();
    void hideProgressBar();

}
