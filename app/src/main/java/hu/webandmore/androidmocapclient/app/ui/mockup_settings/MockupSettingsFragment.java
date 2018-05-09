package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.api.ServiceGenerator;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

public class MockupSettingsFragment extends Fragment implements MockupSettingsScreen {

    private static final String TAG = "MockupSettingsFragment";


    @BindView(R.id.wifi_ssid)
    AutoCompleteTextView mWiFiSSID;

    @BindView(R.id.wifi_password)
    EditText mWiFiPassword;

    @BindView(R.id.wifi_feedback)
    RelativeLayout mWiFiFeedbackLayout;

    @BindView(R.id.wifi_feedback_text)
    TextView mWiFiFeedbackText;

    @BindView(R.id.mqtt_host)
    EditText mMqttHost;

    @BindView(R.id.mqtt_port)
    EditText mMqttPort;

    @BindView(R.id.mqtt_feedback)
    RelativeLayout mMqttFeedbackLayout;

    @BindView(R.id.mqtt_feedback_text)
    TextView mMqttFeedbackText;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    @BindView(R.id.apMode)
    ImageView mApMode;

    @BindView(R.id.wifiMode)
    ImageView mWiFiMode;

    private MockupSettingPresenter mockupSettingPresenter;

    public MockupSettingsFragment() {
        // Required empty public constructor
    }

    public static MockupSettingsFragment newInstance() {
        MockupSettingsFragment fragment = new MockupSettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mockup_settings, container,
                false);

        Log.i(TAG, "onCreateView");
        ButterKnife.bind(this, view);

        mockupSettingPresenter = new MockupSettingPresenter(getContext());
        mockupSettingPresenter.attachScreen(this);
        mockupSettingPresenter.registerWiFiEvent();


        mWiFiSSID.setThreshold(1);

        mockupSettingPresenter.connectToMqtt();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.select_dialog_singlechoice, mockupSettingPresenter.scanWiFiSSID());
        mWiFiSSID.setAdapter(adapter);
        mockupSettingPresenter.getWiFiTask();
    }

    @Override
    public void fillWiFiSettings(WiFiModel wiFiModel) {
        mWiFiSSID.setText(wiFiModel.getSsid());
        mWiFiPassword.setText(wiFiModel.getPassword());
    }

    @Override
    public void fillMqttSettings(WiFiModel wiFiModel) {
        mMqttHost.setText(wiFiModel.getMqtt_host());
        mMqttPort.setText(String.valueOf(wiFiModel.getMqtt_port()));
    }

    @Override
    public void setWiFiSettings() {

    }

    @Override
    public void saveWiFiSetting() {
        mockupSettingPresenter.saveWifiTask();
    }

    @Override
    public void fillWiFiAdapter(ArrayList<String> wiFi) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, wiFi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //mWiFiSSID.setAdapter(adapter);
    }

    @Override
    public void showWiFiFeedback(String feedbackMsg, boolean failure) {
        if(failure) {
            mWiFiFeedbackLayout.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.disconnected_card_feedback));
        } else {
            mWiFiFeedbackLayout.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.connected_card_feedback));
        }
        mWiFiFeedbackText.setText(feedbackMsg);
    }

    @Override
    public void showMqttFeedback(String feedbackMsg, boolean failure) {
        if(failure) {
            mMqttFeedbackLayout.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.disconnected_card_feedback));
        } else {
            mMqttFeedbackLayout.setBackground(ContextCompat.getDrawable(getContext(),
                    R.drawable.connected_card_feedback));
        }
        mMqttFeedbackText.setText(feedbackMsg);
    }

    @Override
    public boolean checkWiFiArguments() {
        if(mWiFiSSID.getText().toString().isEmpty()) {
            mWiFiSSID.setError(getString(R.string.required_field));
            return false;
        }
        if(mWiFiPassword.getText().toString().isEmpty()) {
            mWiFiPassword.setError(getString(R.string.required_field));
            return false;
        }
        return true;
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void reconnectWiFi() {
        mockupSettingPresenter.reconnectWiFiTask();
    }

    @Override
    public void changeWiFiMode() {
        if(mApMode.getVisibility() == View.VISIBLE) {
            mApMode.setVisibility(View.GONE);
            mWiFiMode.setVisibility(View.VISIBLE);
            ServiceGenerator.changeApiBaseUrl(false);
        } else {
            mWiFiMode.setVisibility(View.GONE);
            mApMode.setVisibility(View.VISIBLE);
            ServiceGenerator.changeApiBaseUrl(true);
        }
    }

    @Override
    public void changeWiFiModeIcon(boolean isFailure) {
        if(isFailure) {
            mWiFiMode.setVisibility(View.GONE);
            mApMode.setVisibility(View.VISIBLE);
        } else {
            mApMode.setVisibility(View.GONE);
            mWiFiMode.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.save_wifi)
    public void setWiFi(){
        if(checkWiFiArguments()) {
            Log.i(TAG, "WiFi arguments checked!");
            WiFiModel wiFi = new WiFiModel();
            wiFi.setSsid(mWiFiSSID.getText().toString());
            wiFi.setPassword(mWiFiPassword.getText().toString());
            wiFi.setAp(false);
            mockupSettingPresenter.setWiFiTask(wiFi);
        }
    }

    @OnClick({R.id.wifiMode, R.id.apMode})
    public void changeWiFi(){
        changeWiFiMode();
        mockupSettingPresenter.changeDeviceAddress();
    }

}
