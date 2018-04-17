package hu.webandmore.androidmocapclient.app.ui.settings;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.webandmore.androidmocapclient.R;
import hu.webandmore.androidmocapclient.app.ui.mockup_settings.MockupSettingsFragment;
import hu.webandmore.androidmocapclient.app.ui.robot_settings.RobotSettingsFragment;

public class SettingsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SettingsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    private MockupSettingsFragment mockupSettingsFragment =
            MockupSettingsFragment.newInstance("asd", "ASD");
    private RobotSettingsFragment robotSettingsFragment =
            RobotSettingsFragment.newInstance("Asd", "ad");

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return mockupSettingsFragment;
        if (position == 1)
            return robotSettingsFragment;
        return mockupSettingsFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.mockup);
            case 1:
                return mContext.getString(R.string.humanoid_robot);
        }
        return null;
    }

}
