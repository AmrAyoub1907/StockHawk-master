package com.udacity.stockhawk;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Amr Ayoub on 5/6/2017.
 */

public class StockHawkWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockHawkWidgetFactory(getApplicationContext(), intent);
    }
}
