package social.laika.app.utils.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Tito_Leiva on 29-04-15.
 */
public class GeneralSwipeRefreshLayout extends SwipeRefreshLayout {
    private OnChildScrollUpListener mScrollListenerNeeded;

    public static interface OnChildScrollUpListener {
        public boolean canChildScrollUp();
    }

    public GeneralSwipeRefreshLayout(Context context) {
        super(context);
    }
    public GeneralSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Listener that controls if scrolling up is allowed to child views or not
     */
    public void setOnChildScrollUpListener(OnChildScrollUpListener listener) {
        mScrollListenerNeeded = listener;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollListenerNeeded == null) {
            Log.e(GeneralSwipeRefreshLayout.class.getSimpleName(), "listener is not defined!");
        }
        return mScrollListenerNeeded == null ? false : mScrollListenerNeeded.canChildScrollUp();
    }
}
