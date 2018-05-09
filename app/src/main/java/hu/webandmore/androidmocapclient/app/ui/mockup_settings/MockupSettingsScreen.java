package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import java.util.ArrayList;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

public interface MockupSettingsScreen {

    void fillWiFiSettings(WiFiModel wiFiModel);
    void fillMqttSettings(WiFiModel wiFiModel);
    void setWiFiSettings();
    void saveWiFiSetting();
    void fillWiFiAdapter(ArrayList<String> wiFi);
    void showWiFiFeedback(String feedbackMsg, boolean failure);
    void showMqttFeedback(String feedbackMsg, boolean failure);
    boolean checkWiFiArguments();
    void showError(String errorMsg);
    void showProgressBar();
    void hideProgressBar();
    void reconnectWiFi();
    void changeWiFiMode();

}
