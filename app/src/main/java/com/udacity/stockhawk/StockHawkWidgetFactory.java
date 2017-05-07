package com.udacity.stockhawk;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by Amr Ayoub on 5/6/2017.
 */

public class StockHawkWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor mCursor;
    private Context mContext;

    //List<String>Symbols,Prices,Changes;
    int mWidgetId;
    public final String LOG_TAG = StockHawkWidgetService.class.getSimpleName();
    private static final String[] Quotes_COLUMNS = {
            Contract.Quote.TABLE_NAME + "." + Contract.Quote._ID,
            Contract.Quote.COLUMN_SYMBOL,
            Contract.Quote.COLUMN_PRICE,
            Contract.Quote.COLUMN_ABSOLUTE_CHANGE,
            Contract.Quote.COLUMN_PERCENTAGE_CHANGE,
            Contract.Quote.COLUMN_HISTORY
    };
    // these indices must match the projection
    static final int INDEX_QUOTE_ID = 0;
    static final int INDEX_QUOTE_SYMBOL = 1;
    static final int INDEX_QUOTE_PRICE = 2;
    static final int INDEX_QUOTE_ABS_CHANGE = 3;
    static final int INDEX_QUOTE_PREC_CHANGE = 4;
    static final int INDEX_QUOTE_HISTORY = 5;

    public StockHawkWidgetFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();

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
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        String symbol = mCursor.getString(INDEX_QUOTE_SYMBOL);
        String price = Float.toString(mCursor.getFloat(INDEX_QUOTE_PRICE)),
                change = Float.toString(mCursor.getFloat(INDEX_QUOTE_PREC_CHANGE));
        Float f = mCursor.getFloat(INDEX_QUOTE_ABS_CHANGE);
        if (f<0){
            rv.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }
            rv.setTextViewText(R.id.stock_symbol, symbol);
            rv.setTextViewText(R.id.price,"$"+price );
            rv.setTextViewText(R.id.stock_change, change+"%");

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
        if (mCursor != null) {
            mCursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Quotes_COLUMNS,
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL + " ASC");
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }
}
