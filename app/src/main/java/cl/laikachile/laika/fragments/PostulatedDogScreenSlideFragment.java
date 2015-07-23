package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import cl.laikachile.laika.R;
import cl.laikachile.laika.activities.MyDogsActivity;
import cl.laikachile.laika.listeners.RevokeAdoptionDialogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.models.UserAdoptDog;
import cl.laikachile.laika.utils.Do;
import cl.laikachile.laika.utils.Tag;

/**
 * Created by Tito_Leiva on 28-05-15.
 */
public class PostulatedDogScreenSlideFragment extends AdoptDogScreenSlideFragment {

    public UserAdoptDog mUserAdoptDog;

    public PostulatedDogScreenSlideFragment(Dog mDog, UserAdoptDog mUserAdoptDog,
                                            Activity activity) {
        super(mDog, activity);
        this.mUserAdoptDog = mUserAdoptDog;
    }

    @Override
    public void setUserAdoptDog() {

        mIdTextView.setVisibility(View.VISIBLE);
        mFoundationTextView.setVisibility(View.VISIBLE);
        mStatusTextView.setVisibility(View.VISIBLE);

        mIdTextView.setText("ID:" + mUserAdoptDog.mUserAdoptDogId);
        mFoundationTextView.setText(mUserAdoptDog.mFoundationName);
        mStatusTextView.setText(getStatus(mUserAdoptDog.mStatus, mActivity.getApplicationContext()));

        mPostulateButton.setOnClickListener(new RevokeAdoptionDialogOnClickListener(mDog, mActivity,
                mProgressDialog, mUserAdoptDog));

        switch(mUserAdoptDog.mStatus) {

            case Tag.POSTULATION_WAITING:
            case Tag.POSTULATION_ACCEPTED:

                mPostulateButton.setText(Do.getRString(mActivity.getApplicationContext(),
                        R.string.revoke_postulated_status));

                break;

            case Tag.POSTULATION_DISABLED:
            case Tag.POSTULATION_REVOKED:
            case Tag.POSTULATION_REFUSED:

                mPostulateButton.setText(Do.getRString(mActivity.getApplicationContext(),
                        R.string.delete_owner));

                break;

            case Tag.POSTULATION_ADOPTED:

                mPostulateButton.setText(Do.getRString(mActivity.getApplicationContext(),
                        R.string.view_dog_profile));

                break;

        }


    }

    public String getStatus(int status, Context context) {

        String text = "";

        switch (status) {

            case Tag.POSTULATION_WAITING:
                text = Do.getRString(context, R.string.waiting_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_blue));
                break;

            case Tag.POSTULATION_DISABLED:
                text = Do.getRString(context, R.string.disabled_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_red));
                break;

            case Tag.POSTULATION_ACCEPTED:
                text = Do.getRString(context, R.string.accepted_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_dark_green));
                break;

            case Tag.POSTULATION_ADOPTED:
                text = Do.getRString(context, R.string.adopted_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_dark_green));
                break;

            case Tag.POSTULATION_REFUSED:
                text = Do.getRString(context, R.string.refused_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_red));
                break;

            case Tag.POSTULATION_REVOKED:
                text = Do.getRString(context, R.string.revoked_postulated_status);
                mStatusTextView.setTextColor(Do.getColor(context, R.color.laika_red));
                break;

        }

        return text;
    }
}
