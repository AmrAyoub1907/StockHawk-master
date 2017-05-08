package com.udacity.stockhawk;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.ui.MainActivity;


public class StockHawkWidgetProvider extends AppWidgetProvider {
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //context.startService(new Intent(context, QuoteIntentService.class));
        appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,StockHawkWidgetProvider.class));
        for (int i : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stock_hawk_widget_provider);
            Intent adapter = new Intent(context, StockHawkWidgetService.class);
            adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, i);
            rv.setRemoteAdapter(R.id.stock_list, adapter);
            Intent startActivityIntent = new Intent(context, MainActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.stock_list, startActivityPendingIntent);
            appWidgetManager.updateAppWidget(i, rv);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

