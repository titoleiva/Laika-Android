package social.laika.app.network.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Dog;
import social.laika.app.network.RequestManager;
import social.laika.app.responses.SimpleResponse;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 12-06-15.
 */
public class AlarmRemindersRequest extends SyncRequest {


    private static final String TAG = AlarmRemindersRequest.class.getSimpleName();

    AlarmReminder mAlarmReminder;
    int mDogId;

    public AlarmRemindersRequest(Context context, AlarmReminder alarmReminder) {
        super(context);
        mAlarmReminder = alarmReminder;

    }

    public AlarmRemindersRequest(Context mContext, int mDogId) {
        super(mContext);
        this.mDogId = mDogId;
    }

    @Override
    public void sync() throws InterruptedException, ExecutionException, TimeoutException, JSONException {

        JSONObject jsonObject;

        switch (mAlarmReminder.mNeedsSync) {

            case Tag.FLAG_CREATED:

                Log.d(TAG, "FLAG_CREATED Uploading the new alarm to the server");

                jsonObject = create();
                mAlarmReminder.mAlarmReminderId = jsonObject.getInt(AlarmReminder.COLUMN_ALARM_REMINDER_ID);
                mAlarmReminder.refresh(mContext);

                break;

            case Tag.FLAG_UPDATED:

                Log.d(TAG, "FLAG_UPDATED Uploading the updated alarm to the server");

                jsonObject = update();
                AlarmReminder.saveReminder(jsonObject, mContext);

                break;

            case Tag.FLAG_DELETED:

                Log.d(TAG, "FLAG_DELETED Deleting the alarm with the server");

                jsonObject = delete();

                if (jsonObject.getBoolean("success")) {

                    mAlarmReminder.delete();
                }

                break;
        }
    }

    @Override
    public JSONObject refresh() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        Map<String, String> params = new HashMap<>();
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDogId));

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.getRequest(params, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(20, TimeUnit.SECONDS);

    }

    @Override
    protected JSONObject create() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mAlarmReminder.getJsonObject();

        String address = RequestManager.ADDRESS_ALERT_REMINDERS;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.postRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);
    }

    @Override
    protected JSONObject update() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        JSONObject jsonObject = mAlarmReminder.getJsonObject();

        String address = RequestManager.ADDRESS_ALERT_REMINDERS + mAlarmReminder.mAlarmReminderId;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.putRequest(jsonObject, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);
    }


    @Override
    protected JSONObject delete() throws JSONException, InterruptedException, ExecutionException,
            TimeoutException {

        String address = RequestManager.ADDRESS_ALERT_REMINDERS + mAlarmReminder.mAlarmReminderId;
        String token = PrefsManager.getUserToken(mContext);

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        SimpleResponse errorListener = new SimpleResponse();
        Request request = RequestManager.deleteRequest(null, address, future, errorListener, token);

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

        return future.get(10, TimeUnit.SECONDS);

    }
}
