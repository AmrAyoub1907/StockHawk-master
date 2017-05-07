package com.udacity.stockhawk;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class StockHawkWidgetProvider extends AppWidgetProvider {

    /*static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_hawk_widget_provider);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }*/

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //context.startService(new Intent(context, QuoteIntentService.class));
        appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,StockHawkWidgetProvider.class));
        for (int i : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stock_hawk_widget_provider);
            Intent adapter = new Intent(context, StockHawkWidgetService.class);
            adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, i);
            rv.setRemoteAdapter(R.id.stock_list, adapter);
            appWidgetManager.updateAppWidget(i, rv);
            //appWidgetManager.notifyAppWidgetViewDataChanged(i, R.id.stock_list);
        /*for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_hawk_widget_provider);
            Intent launchIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent =  PendingIntent.getActivity(context,0,launchIntent,0);
            views.setTextViewText(R.id.appwidget_text, "Hello Widget");
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }*/

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

