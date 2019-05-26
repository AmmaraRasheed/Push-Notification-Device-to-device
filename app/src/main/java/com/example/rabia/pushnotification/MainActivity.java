package com.example.rabia.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView user_profile,all_user,user_notification;
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_profile=findViewById(R.id.profile);
        all_user=findViewById(R.id.allUser);
        user_notification=findViewById(R.id.notification);
        pager=findViewById(R.id.viewpage);
        pager.setOffscreenPageLimit(2);
        firebaseAuth=FirebaseAuth.getInstance();
        pagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        all_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        user_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int i) {
                changeTabs(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent intent=new Intent(this,LoginUser.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int i) {
        if(i==0){
            user_profile.setTextColor(getColor(R.color.selectedText));
            user_profile.setTextSize(22);

            all_user.setTextColor(getColor(R.color.notSelectedText));
            all_user.setTextSize(16);

            user_notification.setTextColor(getColor(R.color.notSelectedText));
            user_notification.setTextSize(16);
        }
        if(i==1){
            all_user.setTextColor(getColor(R.color.selectedText));
            all_user.setTextSize(22);

            user_profile.setTextColor(getColor(R.color.notSelectedText));
            user_profile.setTextSize(16);

            user_notification.setTextColor(getColor(R.color.notSelectedText));
            user_notification.setTextSize(16);
        }
        if(i==2){
            user_notification.setTextColor(getColor(R.color.selectedText));
            user_notification.setTextSize(22);

            all_user.setTextColor(getColor(R.color.notSelectedText));
            all_user.setTextSize(16);

            user_profile.setTextColor(getColor(R.color.notSelectedText));
            user_profile.setTextSize(16);
        }
    }
}
