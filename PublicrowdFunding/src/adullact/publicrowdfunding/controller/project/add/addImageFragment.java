package adullact.publicrowdfunding.controller.project.add;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Ferrand and Nelaupe
 */
public class addImageFragment extends Fragment {

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_image, container,
				false);

		context = this.getActivity().getApplicationContext();
		
		BreadCrumbView breadCrumb =  (BreadCrumbView) view.findViewById(R.id.breadcrumb);
		breadCrumb.setPosition(2);
		
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
		
		
		int size = Utility.getDrawableSize();
		for(int i = 0; i < size ; i++){
			ImageView image = new ImageView(context);
			image.setImageDrawable(getResources().getDrawable(Utility.getDrawable(i)));
			layout.addView(image,1);
			layout.setId(i);
			layout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
						int id = v.getId();
						nextStep(id);
					
				}
				
			});
		}
		
		return view;
	}
		
	
	public void nextStep(int id){
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction()
				.disallowAddToBackStack();
		Fragment fragment = new addLocationProjectFragment();
		Bundle args = new Bundle();
		
		Bundle bundle = this.getArguments();
		bundle.putInt("illustration", id);
		
		fragment.setArguments(args);
		fragment.setHasOptionsMenu(true);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();
		
	}

}