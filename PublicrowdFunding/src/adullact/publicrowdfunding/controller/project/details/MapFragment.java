package adullact.publicrowdfunding.controller.project.details;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ferrand and Nelaupe
 */
public class MapFragment extends Fragment {

	private SupportMapFragment fragment;
	private FragmentManager fm;

	private Project projetCurrent;

	View rootView;
	GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		System.out.println("Load Google Map Fragment");

		try {
			rootView = inflater.inflate(R.layout.activity_maps, container,
					false);

			fragment = new SupportMapFragment();
			fm = getFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.mapView, fragment, "mapProjectDetail").commit();

		} catch (Exception e) {
			TextView text = new TextView(getActivity());
			text.setText("Impossible de charger Google Map");
			return text;
		}

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			String idProject = bundle.getString("idProject");
			Cache<Project> projet = new Project().getCache(idProject);
			projet.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {

					try {

						projetCurrent = project;
						final Handler handler = new Handler();
						handler.post(new Runnable() {

							@Override
							public void run() {

								Fragment fr = fm
										.findFragmentByTag("mapProjectDetail");

								if (fr == null) {
									handler.postDelayed(this, 500);
								} else {
									googleMap = ((SupportMapFragment) fr)
											.getMap();
								}
								if (googleMap != null) {
									displayInfo();
									handler.removeCallbacksAndMessages(null);

								} else {
									handler.postDelayed(this, 1000);
								}
							}
						});

					} catch (Exception e) {
						TextView text = new TextView(getActivity());
						text.setText("Impossible de charger Google Map");
						rootView = text;
					}
				}
			});
		}
		setMapFocus();
		return rootView;
	}

	public void displayInfo() {
		MarkerOptions marker = new MarkerOptions();
		marker.position(projetCurrent.getPosition());
		marker.snippet(projetCurrent.getDescription());
		googleMap.addMarker(marker);

		setMapFocus();

	}

	public void setMapFocus() {
		if (googleMap != null) {

			LatLng projectLocation = projetCurrent.getPosition();

			CameraPosition currentPlace = new CameraPosition.Builder()
					.target(projectLocation).zoom(9f).build();

			googleMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(currentPlace));

		}
	}

	public void onPause() {
		super.onPause();
		this.getActivity().getSupportFragmentManager().beginTransaction()
				.detach(fragment).commit();
		this.getActivity().getSupportFragmentManager().beginTransaction()
				.detach(this).commit();
	}

	public void onResume() {
		super.onResume();
		setMapFocus();
	}

}