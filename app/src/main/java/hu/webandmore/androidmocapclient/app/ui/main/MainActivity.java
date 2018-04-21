package hu.webandmore.androidmocapclient.app.ui.main;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;
import hu.webandmore.androidmocapclient.app.utils.MainMenu;

public class MainActivity extends MainMenu implements MainScreen {

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.mockup_host)
    EditText mMockupHost;

    @BindView(R.id.mockup_port)
    EditText mMockupPort;

    @BindView(R.id.mockup_feedback)
    RelativeLayout mMockupFeedbackLayout;

    @BindView(R.id.mockup_feedback_text)
    TextView mMockupFeedbackText;

    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(MainActivity.this);
        mainPresenter.attachScreen(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMainMenu(toolbar);
        setActiveItem(R.id.nav_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ComponentName cn =
                    new ComponentName("com.android.settings",
                            "com.android.settings.wifi.WifiSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setActiveItem(R.id.nav_home);

        mainPresenter.getWiFiTask();
    }

    @OnClick(R.id.connect_mockup)
    public void connectMockupSuit() {
        checkSensorConnection();
    }

    @Override
    public void checkSensorConnection() {
        // TODO - setIP if HOST changed
        mainPresenter.getWiFiTask();
    }

    @Override
    public void showMockupFeedback(String feedbackMsg, boolean failure) {
        if(failure) {
            mMockupFeedbackLayout.setBackground(ContextCompat.getDrawable(MainActivity.this,
                    R.drawable.disconnected_card_feedback));
        } else {
            mMockupFeedbackLayout.setBackground(ContextCompat.getDrawable(MainActivity.this,
                    R.drawable.connected_card_feedback));
        }
        mMockupFeedbackText.setText(feedbackMsg);
    }

    @Override
    public void fillMockupData(WiFiModel wiFiModel) {
        mMockupHost.setText(wiFiModel.getIp());
        mMockupPort.setText("80");
    }

    @Override
    public void clearMockupData() {
        mMockupHost.clearComposingText();
        mMockupPort.clearComposingText();
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        mainLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
