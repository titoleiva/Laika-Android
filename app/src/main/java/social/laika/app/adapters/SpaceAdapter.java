package social.laika.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import social.laika.app.R;
import social.laika.app.models.indexes.Space;

/**
 * Created by Tito_Leiva on 04-05-15.
 */
public class SpaceAdapter extends BaseAdapter {

    public List<Space> mSpaces;
    public Context mContext;
    public int mIdLayout;
    public int mIdTextview;

    public SpaceAdapter(Context context, int resource, int textViewResourceId, List objects) {

        mContext = context;
        mIdLayout = resource;
        mIdTextview = textViewResourceId;
        mSpaces = objects;

    }

    public int getPosition(int homeType) {

        for (Space space : mSpaces) {
            if (space.mIndex == homeType) {
                return mSpaces.indexOf(space);
            }
        }

        return 0;
    }

    @Override
    public int getCount() {
        return mSpaces.size();
    }

    @Override
    public Object getItem(int position) {
        return mSpaces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSpaces.get(position).mIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        textView.setText(mSpaces.get(position).mName);
        textView.setTextColor(mContext.getResources().getColor(R.color.light_black_font));
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.laikatheme_textfield_default_holo_light));
        return textView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mIdLayout, null);
        }

        ((TextView) convertView).setText(mSpaces.get(position).mName);
        return convertView;


    }

}
