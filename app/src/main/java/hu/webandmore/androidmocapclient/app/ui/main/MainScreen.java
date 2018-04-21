package hu.webandmore.androidmocapclient.app.ui.main;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

interface MainScreen {

    void checkSensorConnection();
    void showMockupFeedback(String feedbackMsg, boolean failure);
    void fillMockupData(WiFiModel wiFiModel);
    void clearMockupData();
    void showError(String errorMsg);
    void showProgressBar();
    void hideProgressBar();

}
