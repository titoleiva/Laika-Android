package social.laika.app.responses;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.interfaces.Photographable;
import social.laika.app.models.Dog;
import social.laika.app.models.Photo;
import social.laika.app.network.Api;

/**
 * Created by Tito_Leiva on 13-04-15.
 */
public class DogProfileResponse implements Response.ErrorListener,
        Response.Listener<JSONObject> {

    public static final String API_URL = "url";
    public static final String API_IS_PROFILE = "is_profile";
    public static final String API_TIME = "time";
    public static final String API_DATE = "date";

    public Dog mDog;
    public Context mContext;
    public Photographable mActivity;

    public DogProfileResponse(Dog dog, Photographable mActivity, Context mContext) {

        this.mDog = dog;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    @Override
    public void onResponse(JSONObject response) {

        Photo photo = Photo.saveDogPhoto(response, mContext, mDog);
        mDog.mUrlLocal = "";
        mDog.mUrlImage = photo.mUrlLarge;
        mDog.save();
        mActivity.succeedUpload();

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);
        mActivity.failedUpload();

    }
}