package social.laika.app.network.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import social.laika.app.network.account.AccountService;
import social.laika.app.utils.PrefsManager;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class SyncUtils {

    private static final long SYNC_FREQUENCY = 60 * 60 * 12;  // 1 day (in seconds)
    private static final String CONTENT_AUTHORITY = SyncService.CONTENT_AUTHORITY;

    public static final String CODE = "code";
    public static final int CODE_GENERAL= 100;
    public static final int CODE_LOCATIONS= 110;
    public static final int CODE_BREEDS= 120;
    public static final int CODE_POSTULATIONS= 200;
    public static final int CODE_MY_DOG= 300;
    public static final int CODE_ALARM_REFRESH = 310;
    public static final int CODE_ALARM_CREATE = 311;
    public static final int CODE_ALARM_READ = 312;
    public static final int CODE_ALARM_SYNC = 313;
    public static final int CODE_ALARM_DELETE = 314;
    public static final int CODE_CALENDAR_REFRESH = 320;
    public static final int CODE_CALENDAR_CREATE = 321;
    public static final int CODE_CALENDAR_READ = 322;
    public static final int CODE_CALENDAR_SYNC = 323;
    public static final int CODE_CALENDAR_DELETE = 324;
    public static final int CODE_VET_VISIT_REFRESH = 330;
    public static final int CODE_VET_VISIT_CREATE = 331;
    public static final int CODE_VET_VISIT_READ = 332;
    public static final int CODE_VET_VISIT_SYNC = 333;
    public static final int CODE_VET_VISIT_DELETE = 334;
    public static final int CODE_OWNER = 340;
    public static final int CODE_OWNER_CREATE = 341;
    public static final int CODE_OWNER_READ = 342;
    public static final int CODE_OWNER_UPDATE = 343;
    public static final int CODE_OWNER_DELETE = 344;
    public static final int CODE_PHOTO_REFRESH = 350;
    public static final int CODE_PHOTO_CREATE = 351;
    public static final int CODE_PHOTO_READ = 352;
    public static final int CODE_PHOTO_SYNC = 353;
    public static final int CODE_PHOTO_DELETE = 354;


    // Value below must match the account type specified in res/xml/syncadapter.xml
    public static final String ACCOUNT_TYPE = SyncService.ACCOUNT_TYPE;

    /**
     * Create an entry for this application in the system account list, if it isn't already there.
     *
     * @param context Context
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static Account createSyncAccount(Context context) {

        boolean newAccount = false;
        boolean setupComplete = PrefsManager.getSyncSetupComplete(context);

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        Account account = AccountService.GetAccount(ACCOUNT_TYPE);
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    account, CONTENT_AUTHORITY, getFrequentData(), SYNC_FREQUENCY);
            newAccount = true;
        }

        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete) {
            // FIXME idk what to do :c
            /* triggerRefresh(getFrequentData()); // FIXME remove this line
            PrefsManager.setSyncSetupComplete(context, true); */
        }

        return account;
    }

    /**
     * Helper method to trigger an immediate sync ("refresh").
     *
     * <p>This should only be used when we need to preempt the normal sync schedule. Typically, this
     * means the user has pressed the "refresh" button.
     *
     * Note that SYNC_EXTRAS_MANUAL will cause an immediate sync, without any optimization to
     * preserve battery life. If you know new data is available (perhaps via a GCM notification),
     * but the user is not actively waiting for that data, you should omit this flag; this will give
     * the OS additional freedom in scheduling your sync request.
     */
    public static void triggerRefresh(Bundle data) {

        // Disable sync backoff and ignore sync preferences. In other words...perform sync NOW!
        data.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        data.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        Account account = AccountService.GetAccount(ACCOUNT_TYPE);

        ContentResolver.requestSync(
                account,                                // Sync account
                CONTENT_AUTHORITY,                       // Content authority
                data);                                   // Extras
    }

    public static void requestSync(Bundle data) {

        Account account = AccountService.GetAccount(ACCOUNT_TYPE);
        data.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(
                account,                                // Sync account
                CONTENT_AUTHORITY,                       // Content authority
                data);                                   // Extras
    }

    public static Bundle getFrequentData() {

        Bundle bundle = new Bundle();

        return bundle;
    }

    /**
     * Frequent data bundle data for a code
     * @param code The code
     * @return A new bundle data, with the code included
     */
    public static Bundle getFrequentData(int code) {
        Bundle bundle = new Bundle();
        bundle.putInt(CODE, code);

        return bundle;
    }
}

