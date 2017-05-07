package com.udacity.stockhawk;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViewsService;

/**
 * Created by Amr Ayoub on 5/6/2017.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StockHawkWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockHawkWidgetFactory(getApplicationContext(), intent);
    }
}
