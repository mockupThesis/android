package hu.webandmore.androidmocapclient.app.ui.mockup_debug;

public interface MockupDebugScreen {

    void drawGraph();
    void getGraphFilters();
    void setGraphFilters();
    void recordData();
    void backupData();
    void checkMockupConnection();
    void showError();

}
