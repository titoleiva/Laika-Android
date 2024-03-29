package social.laika.app.responses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.util.Date;

import social.laika.app.activities.HomeActivity;
import social.laika.app.activities.TutorialActivity;
import social.laika.app.fragments.TutorialFragment;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.AlarmReminder;
import social.laika.app.models.Breed;
import social.laika.app.models.CalendarReminder;
import social.laika.app.models.City;
import social.laika.app.models.Country;
import social.laika.app.models.Dog;
import social.laika.app.models.OwnerDog;
import social.laika.app.models.Photo;
import social.laika.app.models.Region;
import social.laika.app.models.UserAdoptDog;
import social.laika.app.models.VetVisit;
import social.laika.app.network.Api;
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

    public FirstInformationResponse(Requestable mRequestable, Context context) {
        this.mRequestable = mRequestable;
        this.mContext = context;
    }

    @Override
    public void onResponse(JSONObject response) {

        //sync general
        Breed.saveBreeds(response);

        City.deleteAll();
        Region.deleteAll();
        Country.deleteAll();

        Country.saveCountries(response);
        Region.saveRegions(response);
        City.saveCities(response);

        //sync mis perros
        Dog.saveDogs(response, Tag.DOG_OWNED);
        AlarmReminder.saveReminders(response, mContext);
        CalendarReminder.saveReminders(response, mContext);
        VetVisit.saveVetVisits(response);
        Photo.saveDogPhotos(response, mContext);
        OwnerDog.saveOwnerDogs(response, mContext);

        //sync postulaciones
        UserAdoptDog.saveUserAdoptDogs(response);
        PrefsManager.saveLastSync(mContext, new Date());

        if (mRequestable != null) {

            mRequestable.onSuccess();
            return;
        }

        if (mProgressBar != null) {

            mProgressBar.setVisibility(View.GONE);
        }

        if (mActivity != null) {
            Do.changeActivity(mContext, HomeActivity.class, mActivity, Intent.FLAG_ACTIVITY_NEW_TASK);
            Do.showLongToast("Bienvenido " + PrefsManager.getUserName(mContext) +
                    ", ¿Cómo están tus perritos?", mContext);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Api.error(error, mContext);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (mActivity instanceof TutorialActivity) {
            PrefsManager.clearPrefs(mContext);
            TutorialFragment fragment = (TutorialFragment) ((TutorialActivity) mActivity).getCurrentFragment();
            fragment.enableViews(true);

        }

        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }

        if (mRequestable != null) {
            mRequestable.onFailure();
            return;
        }

    }
}
