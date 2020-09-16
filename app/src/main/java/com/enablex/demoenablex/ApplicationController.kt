package com.enablex.demoenablex

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle

class ApplicationController : Application(), Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    companion object {
        var context: Context? = null

        val sharedPrefs: SharedPreferences
            get() = context!!.getSharedPreferences("APP_PREF_KOTLIN", Context.MODE_PRIVATE)
    }
}
