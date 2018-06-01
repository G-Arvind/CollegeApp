package com.example.arvind.studentinfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class MainActivity extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {

        //getSupportActionBar().hide();
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        configSplash.setBackgroundColor(R.color.logo);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);


        configSplash.setLogoSplash(R.drawable.school);

        configSplash.setAnimLogoSplashDuration(2000);
        //configSplash.setAnimLogoSplashTechnique(Techniques.BounceInDown);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInRight);
        configSplash.setOriginalHeight(10);
        configSplash.setOriginalWidth(10);

        configSplash.setTitleSplash("CollegeApp");
        configSplash.setAnimTitleDuration(2000);
        // configSplash.setTitleFont(getString(R.string.Font));
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(MainActivity.this,LoginPage.class));
        finish();
    }


}
