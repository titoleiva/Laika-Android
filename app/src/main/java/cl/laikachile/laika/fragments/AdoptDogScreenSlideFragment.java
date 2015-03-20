package cl.laikachile.laika.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cl.laikachile.laika.R;
import cl.laikachile.laika.listeners.ConfirmAdoptionDialogOnClickListener;
import cl.laikachile.laika.models.Dog;
import cl.laikachile.laika.utils.Do;

public class AdoptDogScreenSlideFragment extends Fragment {
	
	private int mIdLayout = R.layout.lk_adopt_dog_screen_slide_fragment;
	private Dog dog;
	private Activity activity;
	
	public AdoptDogScreenSlideFragment(Dog dog, Activity activity) {
		
		this.dog = dog;
		this.activity = activity;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(mIdLayout, container, false);

        TextView matchTextView = (TextView) view.findViewById(R.id.match_dogs_screen_slide_textview);
        ImageView pictureImageView = (ImageView) view.findViewById(R.id.picture_dogs_screen_slide_imageview);
        TextView nameTextView = (TextView) view.findViewById(R.id.name_dogs_screen_slide_textview);
        TextView genderTextView = (TextView) view.findViewById(R.id.gender_dogs_screen_slide_textview);
        TextView sterilizedTextView = (TextView) view.findViewById(R.id.sterilized_dogs_screen_slide_textview);
        TextView chipTextView = (TextView) view.findViewById(R.id.chip_dogs_screen_slide_textview);
        TextView trainedTextView = (TextView) view.findViewById(R.id.trained_dogs_screen_slide_textview);
        TextView sizeTextView = (TextView) view.findViewById(R.id.size_dogs_screen_slide_textview);
        TextView yearsTextView = (TextView) view.findViewById(R.id.years_dogs_screen_slide_textview);
        TextView detailsTextView = (TextView) view.findViewById(R.id.detail_adopt_dog_textview);
        Button postulateButton = (Button) view.findViewById(R.id.postulate_adopt_dog_button);

        nameTextView.setText(dog.mName);
        sizeTextView.setText(dog.mSize);
        genderTextView.setText(dog.getGender(view.getContext()));
        yearsTextView.setText(dog.mBirth);
        sterilizedTextView.setText(dog.getSterilized(view.getContext()));
        chipTextView.setText(dog.getChip(view.getContext()));
        trainedTextView.setText(dog.getTrained(view.getContext()));
        matchTextView.setText(Integer.toString(Do.randomInteger(50,100)) + "%"); //FIXME
        pictureImageView.setImageResource(dog.mImage);
        detailsTextView.setText(dog.mDetail);
        
        ConfirmAdoptionDialogOnClickListener listener = new ConfirmAdoptionDialogOnClickListener(dog, this.activity);
        postulateButton.setOnClickListener(listener);
        
        return view;
    }
	
	
}
