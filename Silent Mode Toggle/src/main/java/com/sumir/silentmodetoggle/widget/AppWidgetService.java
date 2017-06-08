package com.sumir.silentmodetoggle.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

import com.sumir.silentmodetoggle.R;
import com.sumir.silentmodetoggle.RingerHelper;

/**
 * Created by Sumir on 18-05-2017.
 */

public class AppWidgetService extends IntentService {

    private static String ACTION_DO_TOGGLE = "actionDoToggle";

    AudioManager audioManager;

    public AppWidgetService()
    {
        super("AppWidgetService");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null && intent.getBooleanExtra(ACTION_DO_TOGGLE,false))
        {
            RingerHelper.performToggle(audioManager);
        }

        AppWidgetManager mgr = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(this,AppWidget.class);
        mgr.updateAppWidget(name,updateUI());

    }

    private RemoteViews updateUI()
    {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.app_widget);

        int phoneImage = RingerHelper.isPhoneSilent(audioManager)
                ?R.drawable.ringer_on
                :R.drawable.ringer_off;

        remoteViews.setImageViewResource(R.id.phone_state,phoneImage);

        Intent intent = new Intent(this,AppWidgetService.class).putExtra(ACTION_DO_TOGGLE,true);

        PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        remoteViews.setOnClickPendingIntent(R.id.phone_state,pendingIntent);

        return remoteViews;
    }
}
