package hu.webandmore.androidmocapclient.app.api.model;


public class WiFiModel {

    private String ssid;
    private String wifi;
    private String password;
    private String ip;
    private boolean connected;
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

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
