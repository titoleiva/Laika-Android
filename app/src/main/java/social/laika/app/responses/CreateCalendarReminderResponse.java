package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.fragments.AlarmReminderMyDogFragment;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class CreateCalendarReminderResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public AlarmReminderMyDogFragment mFragment;
    public Context mContext;

    public CreateCalendarReminderResponse(AlarmReminderMyDogFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity().getApplicationContext();
    }

    @Override
    public void onResponse(JSONObject response) {


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);

    }
}