package com.muxin.asus.arg.common;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class AppManager {
	private static final String TAG = AppManager.class.getSimpleName();

	private static AppManager instance = null;
	private static List<Activity> mActivities = new LinkedList<Activity>();

	private AppManager() {

	}

	public static AppManager getInstance() {
		if (null == instance) {
			synchronized (AppManager.class) {
				if (null == instance) {
					instance = new AppManager();
				}
			}
		}
		return instance;
	}

	public int size() {
		return mActivities.size();
	}

	public synchronized void addActivity(Activity activity) {
		mActivities.add(activity);
	}

	public synchronized void removeActivity(Activity activity) {
		if (mActivities.contains(activity)) {
			mActivities.remove(activity);
		}
	}

	public synchronized void clear() {
		for (int i = mActivities.size() - 1; i > -1; i--) {
			Activity activity = mActivities.get(i);
			removeActivity(activity);
			activity.finish();
			i = mActivities.size();
		}
	}

	public synchronized void clearToTop() {
		for (int i = mActivities.size() - 2; i > -1; i--) {
			Activity activity = mActivities.get(i);
			removeActivity(activity);
			activity.finish();
			i = mActivities.size() - 1;
		}
	}
}
