package hu.webandmore.androidmocapclient.app.ui.settings;

import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.ui.mockup_settings.MockupSettingsFragment;
import hu.webandmore.androidmocapclient.app.ui.robot_settings.RobotSettingsFragment;
import hu.webandmore.androidmocapclient.app.utils.MainMenu;

public class SettingsActivity extends MainMenu
        implements MockupSettingsFragment.OnFragmentInteractionListener,
        RobotSettingsFragment.OnFragmentInteractionListener {

    private static String TAG = "SettingsActivity";

    private SettingsPagerAdapter mSettingsPagerAdapter;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMainMenu(toolbar);

        mSettingsPagerAdapter = new SettingsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mSettingsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActiveItem(R.id.nav_settings);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
