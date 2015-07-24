package social.laika.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import social.laika.app.R;
import social.laika.app.activities.PhotosFragmentActivity;
import social.laika.app.models.Photo;

public class PhotoScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.photo_screen_slider_fragment;
	public Photo mPhoto;
	public PhotosFragmentActivity mActivity;
    public boolean mIsFullScreen;

    public RelativeLayout mFullLayout;
    public LinearLayout mInformationLayout;
    public TextView mDetailTextView;
    public TextView mOwnerTextView;
    public TextView mDateTextView;
    public ImageView mMainImageView;

	public PhotoScreenSlideFragment(Photo mPhoto, PhotosFragmentActivity mActivity) {

        this.mActivity = mActivity;
		this.mPhoto = mPhoto;
        this.mIsFullScreen = mActivity.mIsFullScreen;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        mFullLayout = (RelativeLayout) view.findViewById(R.id.fullscreen_photo_screen_slider_relativelayout);
        mInformationLayout = (LinearLayout) view.findViewById(R.id.information_photo_screen_slider_linearlayout);
        mDetailTextView = (TextView) view.findViewById(R.id.detail_photo_screen_slider_textview);
        mOwnerTextView = (TextView) view.findViewById(R.id.owner_photo_screen_slider_textview);
        mDateTextView = (TextView) view.findViewById(R.id.date_photo_screen_slider_textview);
		mMainImageView = (ImageView) view.findViewById(R.id.main_photo_screen_slider_imageview);
        
		mDetailTextView.setText(mPhoto.mDetail);
        mOwnerTextView.setText(mPhoto.mOwnerName);
        mDateTextView.setText(mPhoto.mDate);
        mMainImageView.setImageBitmap(mPhoto.getPicture(640));

        return view;
    }

    public void showFullScreen() {


    }
}