package adullact.publicrowdfunding.controller.project.add;

import java.util.ArrayList;
import java.util.Calendar;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

/**
 * @author Ferrand and Nelaupe
 */
public class addImageFragment extends Fragment {

	private Button m_valider;

	private Context context;

	private int m_illustration;

	private HorizontalCarouselStyle mStyle;
	private HorizontalCarouselLayout mCarousel;
	private ArrayList<Integer> mData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_image, container,
				false);

		context = this.getActivity().getApplicationContext();
		
		BreadCrumbView breadCrumb =  (BreadCrumbView) view.findViewById(R.id.breadcrumb);
		breadCrumb.setPosition(1);
		
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
		
		
		int size = Utility.getDrawableSize();
		for(int i = 0; i < size ; i++){
			ImageView image = new ImageView(context);
			image.setImageDrawable(getResources().getDrawable(Utility.getDrawable(i)));
			layout.addView(image,1);
		}
		
	

		/*
		
		
		
		
		
		mCarousel = (HorizontalCarouselLayout) view
				.findViewById(R.id.carousel_layout);
		m_valider = (Button) view.findViewById(R.id.button_valider);
		mCarousel.setOnCarouselViewChangedListener(new CarouselInterface() {

			@Override
			public void onItemChangedListener(View v, int position) {
				m_illustration = position;
			}
		});

		m_valider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				
			}
		});

		loadContent();
*/
		return view;
	}
		

}