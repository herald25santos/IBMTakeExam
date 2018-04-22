package com.santos.herald.ibmpracticalexam.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.ui.main.MainActivity;

public class ActivityUtils {

    public static void replaceFragment(Fragment newFragment, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .commit();
    }

    public static void replaceFragmentButton(Fragment newFragment, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .replace(R.id.containerButton, newFragment)
                .commit();
    }

    public static void startMainActivity(Activity activity, Class<?> next, Boolean isClearTop) {
        Intent i = new Intent(activity, next);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(isClearTop) i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(i);
        activity.finish();
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

}
