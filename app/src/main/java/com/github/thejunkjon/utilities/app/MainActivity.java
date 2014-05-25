package com.github.thejunkjon.utilities.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.github.thejunkjon.utilities.app.filebrowser.FileBrowserActivity;

public class MainActivity extends TitlelessActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(3000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.imageViewLogo).setVisibility(View.INVISIBLE);

                Intent fileBrowserIntent = new Intent(
                        getApplicationContext(), FileBrowserActivity.class);
                startActivity(fileBrowserIntent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ImageView iv = (ImageView) findViewById(R.id.imageViewLogo);
        iv.setAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
