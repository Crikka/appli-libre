package adullact.publicrowdfunding.controller.project.add;

import adullact.publicrowdfunding.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ferrand and Nelaupe
 */
public class addLocationProjectFragment extends Fragment implements
		OnMapClickListener {

	private GoogleMap map;
	private Marker marker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = null;
		try {
		    view = inflater.inflate(R.layout.fragment_maps, container, false);
			InitialiseMap();

		} catch (Exception e) {
			e.printStackTrace();
			TextView texte = new TextView(getActivity());
			texte.setText("Impossible de charger Google Map");
			view = texte;
		}

		marker = null;
		return view;
	}

	private void InitialiseMap() {

		map = ((SupportMapFragment) getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.map_frag)).getMap();

		map.setOnMapClickListener(this);
		map.setMyLocationEnabled(true);

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