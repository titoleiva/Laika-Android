package social.laika.app.network.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import social.laika.app.R;
import social.laika.app.activities.MainActivity;
import social.laika.app.network.sync.SyncUtils;

/**
 * Created by Tito_Leiva on 30-07-15.
 */
public class LaikaGcmListenerService extends GcmListenerService {

    public static final String TAG = LaikaGcmListenerService.class.getSimpleName();


    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("title");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        sendNotification(message);
        SyncUtils.triggerRefresh(data);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.camera_icon_camera)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(getWofUri())
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private Uri getWofUri() {

        return Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ladrido);

    }


}