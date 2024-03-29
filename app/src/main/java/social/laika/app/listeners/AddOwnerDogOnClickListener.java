package social.laika.app.listeners;

import social.laika.app.R;
import social.laika.app.interfaces.Requestable;
import social.laika.app.models.Dog;
import social.laika.app.models.Owner;
import social.laika.app.network.Api;
import social.laika.app.network.VolleyManager;
import social.laika.app.responses.AddOwnerResponse;
import social.laika.app.utils.Do;
import social.laika.app.utils.Flurry;
import social.laika.app.utils.PrefsManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddOwnerDogOnClickListener implements OnClickListener, Requestable {

    private static final String TAG = AddOwnerDogOnClickListener.class.getSimpleName();

    private int mIdLayout = R.layout.lk_add_owner_dialog;
    public Dog mDog;
    private EditText emailEditText;
    private Context mContext;
    private String mEmail;
    private Fragment mFragment;

    public AddOwnerDogOnClickListener(Dog mDog) {

        this.mDog = mDog;
    }

    @Override
    public void onClick(View v) {

        mContext = v.getContext();
        addOwner(mContext);
    }

    private View getView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mIdLayout, null, false);

        emailEditText = (EditText) view.findViewById(R.id.email_add_owners_my_dog_editext);

        return view;

    }

    public void addOwner(final Context context, Fragment mFragment) {

        this.mFragment = mFragment;
        addOwner(context);

    }

    public void addOwner(final Context context) {

        mContext = context;
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(getView(context));
        dialog.setPositiveButton(R.string.accept_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = emailEditText.getText().toString();

                if (!Do.isNullOrEmpty(email) && Do.isValidEmail(email)) {

                    mEmail = email;

                    request();
                    Flurry.logEvent(Flurry.ADD_OWNER);

                } else {

                    Do.showLongToast(R.string.not_valid_email_error, context);

                }
            }
        });

        dialog.setNegativeButton(R.string.cancel_dialog, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        dialog.setTitle(Do.getRString(context, R.string.add_owners_my_dog));
        dialog.show();

    }

    @Override
    public void request() {

        Map<String, String> params = new HashMap<>();

        params.put(Owner.COLUMN_EMAIL, mEmail);
        params.put(Dog.COLUMN_DOG_ID, Integer.toString(mDog.mDogId));

        JSONObject jsonObject = new JSONObject(params);
        AddOwnerResponse response = new AddOwnerResponse(mDog, mEmail, mContext, this);
        String address = Api.ADDRESS_ADD_DOG_OWNER;
        String token = PrefsManager.getUserToken(mContext);

        Request request = Api.postRequest(jsonObject, address, response, response, token);
        VolleyManager.getInstance(mContext).addToRequestQueue(request, TAG);

        String message = "Hemos enviado una invitación al correo " + mEmail;
        Do.showShortToast(message, mContext);

    }

    @Override
    public void onSuccess() {

        if (mFragment != null) {

            mFragment.onResume();
        }


    }

    @Override
    public void onFailure() {

        String message = "Lo sentimos! No pudimos enviar la invitación. Inténtalo nuevamente más tarde";
        Do.showLongToast(message, mContext);

    }
}
