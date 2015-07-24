package social.laika.app.responses;

import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.RegisterActivity;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class RegisterResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public RegisterActivity mActivity;

    public RegisterResponse(RegisterActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        mActivity.mRegisterProgressBar.setVisibility(View.GONE);
        ResponseHandler.successLogin(mActivity, response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mActivity);
        mActivity.mRegisterProgressBar.setVisibility(View.GONE);
        mActivity.enableViews(true);
    }

}