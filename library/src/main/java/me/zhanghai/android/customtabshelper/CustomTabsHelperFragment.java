/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.customtabshelper;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

/**
 * A Fragment that manages a {@link CustomTabsActivityHelper}.
 */
public class CustomTabsHelperFragment extends Fragment {

    private static final String FRAGMENT_TAG = CustomTabsHelperFragment.class.getName();

    private CustomTabsActivityHelper mCustomTabsActivityHelper;

    /**
     * Ensure that an instance of this fragment is attached to an activity.
     *
     * @param activity The target activity.
     * @return An instance of this fragment.
     */
    public static CustomTabsHelperFragment attachTo(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CustomTabsHelperFragment fragment = (CustomTabsHelperFragment) fragmentManager
                .findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new CustomTabsHelperFragment();
            fragmentManager.beginTransaction()
                    .add(fragment, FRAGMENT_TAG)
                    .commit();
        }
        return fragment;
    }

    /**
     * Ensure that an instance of this fragment is attached to the host activity of a fragment.
     *
     * @param fragment The target fragment, which will be used to retrieve the host activity.
     * @return An instance of this fragment.
     */
    public static CustomTabsHelperFragment attachTo(Fragment fragment) {
        return attachTo(fragment.getActivity());
    }

    /**
     * Opens the URL on a Custom Tab if possible. Otherwise fallsback to opening it on a WebView.
     *
     * <p>This is a wrapper for
     * {@link CustomTabsActivityHelper#openCustomTab(Activity, CustomTabsIntent, Uri, CustomTabsActivityHelper.CustomTabsFallback)}</p>
     *
     * @param activity The host activity.
     * @param intent a CustomTabsIntent to be used if Custom Tabs is available.
     * @param uri the Uri to be opened.
     * @param fallback a CustomTabsFallback to be used if Custom Tabs is not available.
     */
    public static void open(Activity activity,
                            CustomTabsIntent intent,
                            Uri uri,
                            CustomTabsActivityHelper.CustomTabsFallback fallback) {
        CustomTabsActivityHelper.openCustomTab(activity, intent, uri, fallback);
    }

    /**
     * Get the {@link CustomTabsActivityHelper} this fragment manages.
     *
     * @return The {@link CustomTabsActivityHelper}.
     */
    public CustomTabsActivityHelper getHelper() {
        return mCustomTabsActivityHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setUserVisibleHint(false);

        mCustomTabsActivityHelper = new CustomTabsActivityHelper();
    }

    @Override
    public void onStart() {
        super.onStart();

        mCustomTabsActivityHelper.bindCustomTabsService(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        mCustomTabsActivityHelper.unbindCustomTabsService(getActivity());
    }
}
