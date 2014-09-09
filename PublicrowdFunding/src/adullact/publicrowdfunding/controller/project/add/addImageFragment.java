package adullact.publicrowdfunding.controller.project.add;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author Ferrand and Nelaupe
 */
public class addImageFragment extends Fragment {

	private Button m_valider;

	private Context context;

	private int m_illustration;

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