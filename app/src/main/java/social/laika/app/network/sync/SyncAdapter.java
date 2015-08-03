package social.laika.app.network.sync;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;


import com.android.volley.Request;

import social.laika.app.network.RequestManager;
import social.laika.app.network.VolleyManager;
import social.laika.app.network.requests.PostulationRequest;
import social.laika.app.responses.FirstInformationResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;

/**
 * Define a sync adapter for the app.
 *
 * <p>This class is instantiated in {@link SyncService}, which also binds SyncAdapter to the system.
 * SyncAdapter should only be initialized in SyncService, never anywhere else.
 *
 * <p>The system calls onPerformSync() via an RPC call through the IBinder object supplied by
 * SyncService.
 */
class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SyncAdapter";

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    /**
     * Called by the Android system in response to a request to run the sync adapter. The work
     * required to read data from the network, parse it, and store it in the content provider is
     * done here. Extending AbstractThreadedSyncAdapter ensures that all methods within SyncAdapter
     * run on a background thread. For this reason, blocking I/O and other long-running tasks can be
     * run <em>in situ</em>, and you don't have to set up a separate thread for them.
     .
     *
     * <p>This is where we actually perform any work required to perform a sync.
     * {@link android.content.AbstractThreadedSyncAdapter} guarantees that this will be called on a non-UI thread,
     * so it is safe to peform blocking I/O here.
     *
     * <p>The syncResult argument allows you to pass information back to the method that triggered
     * the sync.
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        Context context = this.getContext();
        Log.i(TAG, "Beginning network synchronization");

        String lastSync = Do.dateToString(PrefsManager.getLastSync(context), Do.DAY_FIRST);

        if (!Do.isNullOrEmpty(lastSync)) {



        }



        Log.i(TAG, "Network synchronization complete");
    }


    public void syncEverything(Context context) {




    }


}

