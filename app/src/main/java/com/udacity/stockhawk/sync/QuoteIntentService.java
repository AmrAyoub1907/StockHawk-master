package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.StockHawkWidgetProvider;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.StockAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    private static final int STOCK_LOADER = 0;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recycler_view)
    RecyclerView stockRecyclerView;

    private StockAdapter adapter;

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                StockHawkWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(this.getPackageName(),
                    R.layout.stock_hawk_widget_provider);
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,launchIntent,0);
            views.setTextViewText(R.id.appwidget_text, "Hello Widget");
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
        QuoteSyncJob.getQuotes(getApplicationContext());

    }
}
