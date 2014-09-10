package adullact.publicrowdfunding.controller.project.add;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ferrand and Nelaupe
 */
public class addLocationProjectFragment extends Fragment implements
		OnMapClickListener {

	private Marker marker;

	private SupportMapFragment fragment;
	private FragmentManager fm;

	View rootView;
	GoogleMap googleMap;

	private addLocationProjectFragment _this;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_add_location, container, false);

		FragmentManager fm = getActivity().getSupportFragmentManager();

		googleMap = ((SupportMapFragment) fm.findFragmentById(R.id.map)).getMap();
		
		googleMap.setOnMapClickListener(this);

		return rootView;
	}

	/*
	public void onPause() {
		super.onPause();
		this.getActivity().getSupportFragmentManager().beginTransaction()
				.detach(fragment).commit();
		this.getActivity().getSupportFragmentManager().beginTransaction()
				.detach(this).commit();
	}*/

	@Override
	public void onMapClick(LatLng arg0) {
		Toast.makeText(getActivity().getApplicationContext(),
				"Emplacement de votre projet ajouté", Toast.LENGTH_SHORT)
				.show();

		if (marker == null) {
			marker = googleMap.addMarker(new MarkerOptions().position(arg0)
					.title("Votre projet"));
		} else {
			marker.remove();
			marker = googleMap.addMarker(new MarkerOptions().position(arg0)
					.title("Votre projet"));
		}
	}

}