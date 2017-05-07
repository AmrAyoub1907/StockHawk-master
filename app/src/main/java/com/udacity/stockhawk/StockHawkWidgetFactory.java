package com.udacity.stockhawk;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;


/**
 * Created by Amr Ayoub on 5/6/2017.
 */

public class StockHawkWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor mCursor;
    private Context mContext;
    //List<String>Symbols,Prices,Changes;
    int mWidgetId;

    public StockHawkWidgetFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                new String[]{Contract.Quote.COLUMN_SYMBOL,Contract.Quote.COLUMN_PRICE,Contract.Quote.COLUMN_ABSOLUTE_CHANGE}
                ,null
                ,null
                ,null);
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        if (mCursor.moveToPosition(position)) {
            rv.setTextViewText(R.id.stock_symbol, mCursor.getString(Contract.Quote.POSITION_SYMBOL));
            rv.setTextViewText(R.id.price, mCursor.getString(Contract.Quote.POSITION_PRICE));
            rv.setTextViewText(R.id.stock_change, mCursor.getString(Contract.Quote.POSITION_ABSOLUTE_CHANGE));
        }
        return rv;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                new String[]{Contract.Quote.COLUMN_SYMBOL,Contract.Quote.COLUMN_PRICE,Contract.Quote.COLUMN_ABSOLUTE_CHANGE}
                ,null
                ,null
                ,null);
    }

    @Override
    public void onDestroy() {
    }
}
