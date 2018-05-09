package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import hu.webandmore.androidmocapclient.app.interactors.WiFiInteractor;
import hu.webandmore.androidmocapclient.app.interactors.events.WiFiEvent;
import hu.webandmore.androidmocapclient.app.ui.Presenter;
import hu.webandmore.androidmocapclient.app.utils.MqttCustomClient;
import hu.webandmore.androidmocapclient.app.utils.WiFiEventType;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

public class MockupSettingPresenter extends Presenter<MockupSettingsScreen> {

    private static String TAG = "MockupSettingPresenter";

    private Executor networkExecutor;
    private Context context;

    private WiFiInteractor wiFiInteractor;
    private WifiManager wifiManager;

    private final String mServerUri = "tcp://mqtt.bayi.hu:1883";
    private String clientId = "android_client";

    private BroadcastReceiver wifiReceiver;

    public MockupSettingPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        wiFiInteractor = new WiFiInteractor(context);
        wifiManager = (WifiManager)
                context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void attachScreen(MockupSettingsScreen screen) {
        Log.i(TAG, "attach Screen");
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void registerWiFiEvent() {
        EventBus.getDefault().register(this);
    }

    public void unRegisterWiFiEvent() {
        EventBus.getDefault().unregister(this);
    }

    public WiFiModel CreateWiFi(String _ssid, String _password) {
        return new WiFiModel(_ssid, _password, false);
    }

    void getWiFiTask() {
        Log.i(TAG, "getWiFiTask");
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.getWiFiStatus();
            }
        });
    }

    void setWiFiTask(final WiFiModel wiFi) {
        Log.i(TAG, "setWiFiTask");
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "setWiFiTask run");
                wiFiInteractor.setWiFiSettings(wiFi);
            }
        });
    }

    void saveWifiTask() {
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.saveWiFiSettings();
            }
        });
    }

    void reconnectWiFiTask() {
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.reconnectWiFi();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final WiFiEvent event) {
        Log.i(TAG, "onEvent");
        screen.hideProgressBar();
        if (event.getThrowable() != null) {
            Log.i(TAG, "onEvent getThrowable not null");
            event.getThrowable().printStackTrace();
            if (screen != null) {
                Log.i(TAG, "onEvent screen is not null");
                screen.showError(event.getThrowable().getMessage());
                screen.showWiFiFeedback(event.getThrowable().getMessage(), true);
                screen.showMqttFeedback(event.getThrowable().getMessage(), true);
                screen.changeWiFiModeIcon(true);
            } else {
                Log.i(TAG, "onEvent screen is null");
            }
        } else {
            Log.i(TAG, "onEvent getThrowable null");
            if (screen != null) {
                System.out.println("STATUSZKÓD: " + event.getCode());
                screen.changeWiFiModeIcon(false);
                if (event.getCode() == 200 ) {
                    if(event.getWiFiEventType() == WiFiEventType.GET) {
                        Log.i(TAG, "GET 200 status code");
                        screen.fillWiFiSettings(event.getWiFi());
                        screen.fillMqttSettings(event.getWiFi());
                        screen.showWiFiFeedback(context.getString(R.string.connected),
                                false);
                        screen.showMqttFeedback(context.getString(R.string.connected),
                                false);
                    } else if(event.getWiFiEventType() == WiFiEventType.SET) {
                        screen.showWiFiFeedback(context.getString(R.string.wifi_changed),
                                false);
                        screen.saveWiFiSetting();
                    } else if(event.getWiFiEventType() == WiFiEventType.SAVE) {
                        screen.showWiFiFeedback(context.getString(R.string.saved_successfully),
                                false);
                        screen.reconnectWiFi();
                    } else {
                        screen.showWiFiFeedback(context.getString(R.string.saved_successfully),
                                false);
                    }

                } else {
                    Log.i(TAG, "onEvent status code not 200");
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

    void connectToMqtt() {
        clientId = clientId + System.currentTimeMillis();
        MqttCustomClient mqttCustomClient =
                MqttCustomClient.getInstance(context, mServerUri, clientId);

        mqttCustomClient.setCallbacks(mqttCustomClient);
        mqttCustomClient.connectToMqttBroker(mqttCustomClient, mServerUri);
    }


    ArrayList<String> scanWiFiSSID() {
        final ArrayList<String> wifis = new ArrayList<>();
        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        wifiReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                List<ScanResult> scanResults = wifiManager.getScanResults();
                for (int i = 0; i < scanResults.size(); i++) {
                    Log.i(TAG, scanResults.get(i).SSID);
                    wifis.add(((scanResults.get(i).SSID)));
                }
            }
        };

        context.registerReceiver(wifiReceiver, theFilter);

        return wifis;
    }

    void changeDeviceAddress() {
        wiFiInteractor.changeWiFiService();
    }

}
