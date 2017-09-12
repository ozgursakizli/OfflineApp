package com.ozgursakizli.offlineapp.components;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ActivityManager {

    public static ActivityManager instance;
    private Activity currentActivity;
    private ArrayList<Activity> runningActivities = new ArrayList<>();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = activity;
        addToRunningActivities(activity);
    }

    private void addToRunningActivities(Activity runningActivity) {
        if (!runningActivities.contains(runningActivity)) {
            runningActivities.add(runningActivity);
        }
    }

    public void removeFromActivities(Activity runningActivity) {
        while (runningActivities.contains(runningActivity)) {
            runningActivities.remove(runningActivity);
        }
    }

}
