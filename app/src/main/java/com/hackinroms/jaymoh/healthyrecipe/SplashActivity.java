package com.hackinroms.jaymoh.healthyrecipe;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ViewSwitcher;

/**
 * Created by Jaymoh on 1/10/2016.
 */
public class SplashActivity extends Activity implements ViewSwitcher.ViewFactory {
//spash screen timer
    private static int SPLASH_TIME_OUT=2000;
    private ImageSwitcher imageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        int currentOrientation=getResources().getConfiguration().orientation;
        if(currentOrientation== Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        imageSwitcher=(ImageSwitcher)findViewById(R.id.image_switcher);
        imageSwitcher.setFactory(this);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        Runnable r=new Runnable() {
            @Override
            public void run() {
                final int images[]={};
            }
        };
    }
    @Override
    public View makeView() {
        return null;
    }
}
