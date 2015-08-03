package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import social.laika.app.activities.LoginActivity;
import social.laika.app.activities.MainActivity;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Breed;
import social.laika.app.models.Country;
import social.laika.app.models.Dog;
import social.laika.app.models.City;
import social.laika.app.models.Region;
import social.laika.app.utils.Do;
import social.laika.app.utils.PrefsManager;
import social.laika.app.utils.Tag;

/**
 * Created by Tito_Leiva on 07-05-15.
 */
public class FirstInformationResponse implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Activity mActivity;
    public Context mContext;
    public ProgressBar mProgressBar;
    public Requestable mRequestable;

    public FirstInformationResponse(Activity mActivity, ProgressBar progressBar) {

        this.mActivity = mActivity;
        this.mContext = mActivity.getApplicationContext();
        this.mProgressBar = progressBar;
    }

    public FirstInformationResponse(Requestable mRequestable) {
        this.mRequestable = mRequestable;
    }

    @Override
    public void onResponse(JSONObject response) {

        Dog.saveDogs(response, Tag.DOG_OWNED);
        Breed.saveBreeds(response);
        Country.saveCountries(response);
        Region.saveRegions(response);
        City.saveCities(response);


        if (mRequestable != null) { //FIXME

            mRequestable.onSuccess();
            return;
        }

        if (mProgressBar != null) {

            mProgressBar.setVisibility(View.GONE);
        }

        Do.changeActivity(mContext, MainActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);
        Do.showLongToast("Bienvenido " + PrefsManager.getUserName(mContext) +
                ", ¿Cómo están tus perritos?", mContext);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseHandler.error(error, mContext);
        //Do.changeActivity(mContext, MainActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);

        if (mProgressBar != null) {

            mProgressBar.setVisibility(View.GONE);
        }

        if (mActivity instanceof LoginActivity) {
            ((LoginActivity) mActivity).enableViews(true);
            PrefsManager.clearPrefs(mContext);
        }

    }
}
