package adullact.publicrowdfunding.controller.project.add;

import adullact.publicrowdfunding.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class addLocationProjectFragment extends Fragment implements
		OnMapClickListener {

	private GoogleMap map;
	private Marker marker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_maps, container, false);

		InitialiseMap();

		/*
		 * Intent data = getIntent(); data.getExtras(); Bundle extras3 =
		 * data.getExtras(); if (extras3 != null) { LatLng position = new
		 * LatLng(extras3.getDouble("latitude"),
		 * extras3.getDouble("longitude")); marker = map.addMarker(new
		 * MarkerOptions().position(position) .title("Votre projet")); } else {
		 * marker = null; }
		 */
		marker = null;
		return view;
	}

	private void InitialiseMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();

			if (map != null) {

			}

			map.setOnMapClickListener(this);
			map.setMyLocationEnabled(true);

		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Toast.makeText(getActivity().getApplicationContext(),
				"Emplacement de votre projet ajouté", Toast.LENGTH_SHORT)
				.show();
		if (marker == null) {
			marker = map.addMarker(new MarkerOptions().position(arg0).title(
					"Votre projet"));
		} else {
			marker.remove();
			marker = map.addMarker(new MarkerOptions().position(arg0).title(
					"Votre projet"));
		}
	}

	public LatLng getPosition() {
		return marker.getPosition();
	}

}