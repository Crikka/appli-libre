package adullact.publicrowdfunding.controller.project.details;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

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
		
		rootView = inflater.inflate(R.layout.activity_maps, container, false);

		fragment = new SupportMapFragment();

		fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.mapView, fragment, "mapid").commit();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			String idProject = bundle.getString("idProject");
			Cache<Project> projet = new Project().getCache(idProject);
			projet.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {
					projetCurrent = project;
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable() {

						@Override
						public void run() {

							Fragment fr = fm.findFragmentByTag("mapid");

							if (fr == null) {
                                if(getActivity() != null) {
                                    getActivity().finish();
                                }
							}

							if (fr instanceof SupportMapFragment) {
								handler.removeCallbacksAndMessages(null);
								googleMap = ((SupportMapFragment) fr).getMap();
							} else {
								handler.removeCallbacksAndMessages(null);
							}

							if (googleMap != null) {
								displayInfo();
								handler.removeCallbacksAndMessages(null);

							}

							else {
								handler.postDelayed(this, 500);
							}
						}
					}, 500);

				}
			});
		}

		return rootView;
	}

	public void displayInfo() {
		MarkerOptions marker = new MarkerOptions();
		marker.position(projetCurrent.getPosition());
		marker.snippet(projetCurrent.getDescription());
		googleMap.addMarker(marker);

		CameraUpdate center = CameraUpdateFactory.newLatLng(projetCurrent
				.getPosition());
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);
	}

}