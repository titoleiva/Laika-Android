package cl.laikachile.laika.activities;

import android.view.View;

import cl.laikachile.laika.R;

public class FoodActivity extends BaseActivity {
	
	private int mIdLayout = R.layout.ai_base_activity;
	
	 @Override
		public void onStart() {

	    	createFragmentView(mIdLayout);
			super.onStart();		
		}
	 
	 @Override
	 public void setActivityView(View view) {
		 
		 // Implementar aqu� la vista
	 }

}
