package hu.webandmore.androidmocapclient.app.ui.main;

interface MainScreen {

    void checkMockupConnection();
    void showMockupConnectionError();
    void checkMqttConnection();
    void showMqttConnectionError();

}
