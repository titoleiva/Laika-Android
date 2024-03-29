package social.laika.app.listeners;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import social.laika.app.activities.BasePublicationsActivity;
import social.laika.app.activities.PublicationsActivity;
import social.laika.app.adapters.PublicationsAdapter;
import social.laika.app.models.publications.BasePublication;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 24-04-15.
 */
public class PublicationsRefreshListener implements AbsListView.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = PublicationsRefreshListener.class.getSimpleName();

    private BasePublicationsActivity mActivity;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ArrayAdapter mAdapter;
    private int mLastVisibleItem;
    private int mCurrentVisibleItem;
    private int mCurrentScrollState;
    private int mLastTotalItemCount;
    private boolean mRefreshing;

    public PublicationsRefreshListener(BasePublicationsActivity mActivity) {

        this.mActivity = mActivity;
        this.mSwipeLayout = mActivity.mSwipeLayout;
        this.mListView = mActivity.mPublicationsListView;
        this.mAdapter = mActivity.mPublicationsAdapter;
        mRefreshing = false;
        mLastTotalItemCount = -1;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (!mRefreshing && mLastTotalItemCount != totalItemCount &&
                view.getLastVisiblePosition() == view.getAdapter().getCount() - 1 &&
                view.getChildAt(view.getChildCount() - 1) != null &&
                view.getChildAt(view.getChildCount() - 1).getBottom() <= view.getHeight()) {

            // refresh
            mSwipeLayout.setRefreshing(true);
            int size = mAdapter.getCount();
            int lastPublicationId = ((BasePublication) mAdapter.getItem(size - 1)).getServerId();
            mActivity.requestPublications(lastPublicationId, Tag.LIMIT, mActivity);

            mRefreshing = true;
            mLastTotalItemCount = totalItemCount;
        }
    }

    private void isScrollCompleted(Context context) {
        if (this.mCurrentVisibleItem >= mLastVisibleItem &&
                this.mCurrentScrollState == SCROLL_STATE_IDLE
                && !mSwipeLayout.isRefreshing() && !mActivity.mIsFavorite) {

            mSwipeLayout.setRefreshing(true);

            int size = mAdapter.getCount();
            int lastPublicationId = ((BasePublication) mAdapter.getItem(size - 1)).getServerId();
            mActivity.requestPublications(lastPublicationId, Tag.LIMIT, mActivity);

        }
    }

    public void shouldLoadMore(boolean loadMore) {
        mRefreshing = loadMore;
    }

    @Override
    public void onRefresh() {
        mActivity.requestPublications(Tag.NONE, Tag.LIMIT, mActivity);

    }

}
