package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.HashMap;

import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMapFragment extends Fragment implements
		OnMarkerClickListener {

	private SupportMapFragment fragment;
	private FragmentManager fm;
	private ProgressDialog mprogressDialog;
	private ArrayList<Project> projets;
	private View rootView;
	private GoogleMap googleMap;
	private HashMap<Marker, String> markers = new HashMap<Marker, String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_maps, container, false);
		
		projets = new ArrayList<Project> ();
		
		mprogressDialog = new ProgressDialog(getActivity());
		mprogressDialog.setMessage("Chargement en cours...");
		mprogressDialog.setTitle("Google Map");
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mprogressDialog.show();

		fragment = new SupportMapFragment();
		fm = getFragmentManager();
		
		SyncServerToLocal sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {
				projets = projects;
			}
		});
		
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.mapView, fragment, "mapid").commit();

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				googleMap = ((SupportMapFragment) fm.findFragmentByTag("mapid"))
						.getMap();

				if (googleMap != null) {
					System.out.println(projets.size());
					for (Project proj : projets) {
						MarkerOptions marker = new MarkerOptions();
						marker.position(proj.getPosition());
						marker.title(proj.getName());
						Marker m = googleMap.addMarker(marker);
						markers.put(m, proj.getResourceId());

					}

					handler.removeCallbacksAndMessages(null);

					googleMap.setOnMarkerClickListener(TabMapFragment.this);
					googleMap.setMyLocationEnabled(true);
					LatLng Montpellier = new LatLng(43.652400,3.761380);
					CameraUpdate center = CameraUpdateFactory.newLatLng(Montpellier);
					CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
					googleMap.moveCamera(center);
					googleMap.animateCamera(zoom);
					mprogressDialog.dismiss();
				}

				else {
					handler.postDelayed(this, 1000);
				}
			}
		}, 1000);

		return rootView;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		
		String id = markers.get(marker);
		/*
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		//ft.setCustomAnimations(R.anim.enter_2, R.anim.exit);
		Fragment fragment = new adullact.publicrowdfunding.fragment.v4.detailProject.PagerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("idProject", id);
		fragment.setArguments(bundle);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();
		*/
		
		FragmentTransaction ft = fm.beginTransaction();
		//ft.setCustomAnimations(R.anim.enter_2, R.anim.exit);
		Fragment fragment = new adullact.publicrowdfunding.fragment.v4.detailProject.TabProjectFragment();
		Bundle bundle = new Bundle();
		bundle.putString("idProject", id);
		fragment.setArguments(bundle);
		ft.replace(R.id.FlashBarLayout, fragment);
		ft.commit();
		
		return true;
		
		
		
		
	}

}