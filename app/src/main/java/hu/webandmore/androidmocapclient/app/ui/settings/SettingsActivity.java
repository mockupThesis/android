package hu.webandmore.androidmocapclient.app.ui.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hu.webandmore.androidmocapclient.R;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
