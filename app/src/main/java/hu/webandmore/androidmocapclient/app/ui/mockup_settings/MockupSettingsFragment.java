package hu.webandmore.androidmocapclient.app.ui.mockup_settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.api.model.WiFiModel;

public class MockupSettingsFragment extends Fragment implements MockupSettingsScreen {

    private static final String TAG = "MockupSettingsFragment";


    @BindView(R.id.wifi_ssid)
    EditText mWiFiSSID;

    @BindView(R.id.wifi_password)
    EditText mWiFiPassword;

    @BindView(R.id.wifi_feedback)
    RelativeLayout mWiFiFeedbackLayout;

    @BindView(R.id.wifi_feedback_text)
    TextView mWiFiFeedbackText;

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

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");
        mockupSettingPresenter.getWiFiTask();
    }

    @Override
    public void fillWiFiSettings(WiFiModel wiFiModel) {
        mWiFiSSID.setText(wiFiModel.getSsid());
        mWiFiPassword.setText(wiFiModel.getPassword());
    }

    @Override
    public void setWiFiSettings() {

    }

    @Override
    public void saveWiFiSetting() {

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
    public void checkWiFiArguments() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.save_wifi)
    public void saveWiFi(){

    }

}
