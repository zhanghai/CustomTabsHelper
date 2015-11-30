/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.customtabshelper.sample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

public class MainActivity extends AppCompatActivity
        implements CustomTabsActivityHelper.CustomTabsFallback {

    private static final Uri PROJECT_URI = Uri.parse(
            "https://github.com/DreaminginCodeZH/CustomTabsHelper");

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;

    @Bind(R.id.view_on_github)
    Button mViewOnGitHubButton;

    private CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        CustomTabsHelperFragment.attachTo(this);

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        mViewOnGitHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsHelperFragment.open(MainActivity.this, mCustomTabsIntent, PROJECT_URI,
                        MainActivity.this);
            }
        });
    }

    @Override
    public void openUri(Activity activity, Uri uri) {
        Toast.makeText(this, R.string.custom_tabs_failed, Toast.LENGTH_SHORT).show();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}
