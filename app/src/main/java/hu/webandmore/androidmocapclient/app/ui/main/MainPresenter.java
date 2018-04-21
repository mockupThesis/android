package hu.webandmore.androidmocapclient.app.ui.main;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.interactors.WiFiInteractor;
import hu.webandmore.androidmocapclient.app.interactors.events.WiFiEvent;
import hu.webandmore.androidmocapclient.app.ui.Presenter;
import hu.webandmore.androidmocapclient.app.utils.WiFiEventType;

public class MainPresenter extends Presenter<MainScreen> {

    private static String TAG = "MainPresenter";

    private Context context;
    private Executor networkExecutor;
    private WiFiInteractor wiFiInteractor;

    public MainPresenter(Context _context){
        context = _context;
        networkExecutor = Executors.newFixedThreadPool(1);
        wiFiInteractor = new WiFiInteractor(context);
    }

    @Override
    public void attachScreen(MainScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    public void getWiFiTask() {
        Log.i(TAG, "getWiFiTask");
        screen.showProgressBar();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                wiFiInteractor.getWiFiStatus();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final WiFiEvent event) {
        screen.hideProgressBar();
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                screen.showMockupFeedback(event.getThrowable().getMessage(), true);
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200 ) {
                    if(event.getWiFiEventType() == WiFiEventType.GET) {
                        screen.fillMockupDatas(event.getWiFi());
                        screen.showMockupFeedback(context.getString(R.string.connected),
                                false);
                    }
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

}
