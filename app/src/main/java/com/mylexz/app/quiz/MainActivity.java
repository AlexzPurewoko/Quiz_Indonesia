package com.mylexz.app.quiz;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private final String TAG = getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // adding status bar colored mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }
        imageView = findViewById(R.id.main_image_splash);
        int[] images = {
                // lomba aplikasi kihajar
                R.mipmap.ic_launcher,
                // wonderful indonesia
                R.drawable.wondeful_indo
        };
        animateAndForward(imageView, images, 0, false);
    }

    private void animateAndForward(final ImageView objImage, final int[] images, final int indexStart, final boolean repeat) {
        Log.i(TAG, "inside animationForward at the top");
        int fadeInDuration = 1000; // animate duration
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        objImage.setVisibility(View.VISIBLE);
        objImage.setImageResource(images[indexStart]);

        // building an animation FADE IN
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(fadeInDuration);

        // building an FadeOut Animation
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        // building animation set
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        // don't need an fadeOut animation where pointer location array is on the end
        if(indexStart < images.length - 1)
            animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        // set up an animation into image view
        objImage.setAnimation(animation);
        // set up the animation listener to performing the basic attraction
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationEnd()");
                if (images.length - 1 > indexStart){
                    Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationEnd() in the if section");

                    animateAndForward(objImage, images, indexStart + 1, repeat);
                }
                else {
                    Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationStart() in the else section");
                    if(repeat){
                        Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationStart() in the else -> if(repeat) section");
                        animateAndForward(objImage, images, 0, repeat);
                    }
                    Toast.makeText(MainActivity.this, "Has reached the end of image to show", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAnimationStart(Animation animation) {
                Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationStart()");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(TAG, "inside animationForward at the AnimationListener.onAnimationRepeat()");
            }
        });

    }
/*
    private class LoadingSplash extends AsyncTask<Void, Integer, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // code goes here to perform an preparation in background
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
*/
}
