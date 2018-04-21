package hu.webandmore.androidmocapclient.app.ui.main;

import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

interface MainScreen {

    void checkSensorConnection();
    void showMockupFeedback(String feedbackMsg, boolean failure);
    void fillMockupDatas(WiFiModel wiFiModel);
    void showError(String errorMsg);
    void showProgressBar();
    void hideProgressBar();

}
