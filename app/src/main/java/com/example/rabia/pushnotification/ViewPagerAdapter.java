package com.example.rabia.pushnotification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapter extends FragmentPagerAdapter{
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                ProfileUser profileUser=new ProfileUser();
                return profileUser;
            case 1:
                AllUser allUser=new AllUser();
                return allUser;
            case 2:
                NotificationUser notificationUser=new NotificationUser();
                return notificationUser;
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
