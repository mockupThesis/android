package hu.webandmore.androidmocapclient.app.api.model;


public class WiFiModel {

    private String ssid;
    private String password;
    private boolean ap = false;

    public WiFiModel(){}

    public WiFiModel(String _ssid, String _password, boolean _ap) {
        ssid = _ssid;
        password = _password;
        ap = _ap;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAp() {
        return ap;
    }

    public void setAp(boolean ap) {
        this.ap = ap;
    }
}
