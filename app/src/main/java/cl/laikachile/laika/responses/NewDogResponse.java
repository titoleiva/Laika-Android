package cl.laikachile.laika.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cl.laikachile.laika.activities.CreateDogActivity;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.network.utils.ResponseHandler;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class NewDogResponse implements Response.ErrorListener,
        Response.Listener<JSONObject>  {

    public CreateDogActivity mActivity;
    public String mMessage;

    public NewDogResponse(CreateDogActivity mActivity, String mMessage) {
        this.mActivity = mActivity;
        this.mMessage = mMessage;
    }

    @Override
    public void onResponse(JSONObject response) {

        Context context = mActivity.getApplicationContext();
        Dog dog = Dog.saveDog(response, Tag.DOG_OWNED);
        Do.showToast(mMessage + dog.mName, context);
        mActivity.onBackPressed();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        mActivity.enableViews(true);
        ResponseHandler.error(error, mActivity);
        Do.showToast("Hubo un error", mActivity);

    }

}