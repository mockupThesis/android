package hu.webandmore.androidmocapclient.app.api.model;

public class MqttModel {

    private String host;
    private int port;

    public MqttModel(){}

    public MqttModel(String _host, int _port) {
        host = _host;
        port = _port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
