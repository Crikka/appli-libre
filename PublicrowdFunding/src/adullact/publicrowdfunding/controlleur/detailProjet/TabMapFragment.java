package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMapFragment extends Fragment {

	private MapFragment fragment;
	private FragmentManager fm;
	private ProgressDialog mprogressDialog;
	
	private MainActivity _this;

	View rootView;
	GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mprogressDialog = new ProgressDialog(getActivity());
		mprogressDialog.setMessage("Chargement en cours...");
		mprogressDialog.setTitle("Google Map");

		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		mprogressDialog.show();

		fragment = new MapFragment();

		fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tabcontent, fragment, "mapid").commit();

		_this = (MainActivity) getActivity();

		rootView = inflater.inflate(R.layout.tab_maps, container, false);

		final Handler handler = new Handler();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				Fragment fr = fm.findFragmentByTag("mapid");

				if (fr == null) {
					getActivity().finish();
				}

				if (fr instanceof MapFragment) {
					handler.removeCallbacksAndMessages(null);
					googleMap = ((MapFragment) fr).getMap();
				} else {
					handler.removeCallbacksAndMessages(null);
				}

				if (googleMap != null) {

					MarkerOptions marker = new MarkerOptions();
					marker.position(_this.projetCurrent.getPosition());
					marker.snippet(_this.projetCurrent.getDescription());
					googleMap.addMarker(marker);
					handler.removeCallbacksAndMessages(null);
					mprogressDialog.dismiss();
					CameraUpdate center = CameraUpdateFactory.newLatLng(_this.projetCurrent
							.getPosition());
					CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
					googleMap.moveCamera(center);
					googleMap.animateCamera(zoom);

				}

				else {
					handler.postDelayed(this, 500);
				}
			}
		}, 500);

		return rootView;
	}

}