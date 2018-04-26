package hu.webandmore.androidmocapclient.app.utils;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCustomClient extends MqttAndroidClient {

    private static final String TAG = "MqttClient";

    private static volatile MqttCustomClient mMqttCustomClient = null;

    private final String mSubscriptionTopic = "sensor/status";
    private final String mPublishTopic = "sensor/status";
    private final String mPublishMessage = "Android client";

    public MqttCustomClient(Context context, String serverURI, String clientId) {
        super(context, serverURI, clientId);
    }

    public static MqttCustomClient getInstance(Context context, String serverURI, String clientId) {
        if (mMqttCustomClient == null) {
            synchronized(MqttCustomClient.class) {
                if (mMqttCustomClient == null) {
                    mMqttCustomClient = new MqttCustomClient(context, serverURI, clientId);
                }
            }
        }
        return mMqttCustomClient;
    }

    public void setCallbacks(final MqttCustomClient mqttCustomClient){
        mqttCustomClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                    mqttLog("Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic(mqttCustomClient);
                } else {
                    mqttLog("Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                mqttLog("The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                mqttLog("Incoming message: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void connectToMqttBroker(final MqttCustomClient mqttCustomClient, final String serverUri) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            mqttLog("Connecting to " + serverUri);
            mqttCustomClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttCustomClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic(mqttCustomClient);
                    publishMessage(mqttCustomClient);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    mqttLog("Failed to connect to: " + serverUri);
                    mqttLog("Failed to connect to: " + exception.getLocalizedMessage());
                }
            });


        } catch (MqttException ex){
            mqttLog(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void subscribeToTopic(MqttCustomClient mqttCustomClient){
        try {
            mqttCustomClient.subscribe(mSubscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttLog("Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    mqttLog("Failed to subscribe");
                }
            });

            mqttCustomClient.subscribe(mSubscriptionTopic, 0, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    mqttLog("Message: " + topic + " : " + new String(message.getPayload()));
                }
            });

        } catch (MqttException ex){
            mqttLog("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publishMessage(MqttCustomClient mqttCustomClient){

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(mPublishMessage.getBytes());
            mqttCustomClient.publish(mPublishTopic, message);
            mqttLog("Message Published");
            if(!mqttCustomClient.isConnected()){
                mqttLog(mqttCustomClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            mqttLog("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mqttLog(String mainText){
        Log.i(TAG, mainText);
    }

}
