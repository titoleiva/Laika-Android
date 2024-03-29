package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.CreateAlarmReminderFragment;
import social.laika.app.network.Api;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CreateCalendarReminderResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CreateAlarmReminderFragment mFragment;
    public Context mContext;

    public CreateCalendarReminderResponse(CreateAlarmReminderFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);

    }
}
