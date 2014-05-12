package adullact.publicrowdfunding;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabAllProjectsMaps extends FragmentActivity {
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		if (map == null) {
			try{
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();
			LatLng position = new LatLng(43.6, 2.7);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));
			position = new LatLng(46.9, 0);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));
			position = new LatLng(47, 4.5);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));
			position = new LatLng(48, 1.5);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));
			position = new LatLng(44, 4.5);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));
			}catch(NullPointerException e){
			Toast.makeText(getApplication(), "Impossible de lancer google Map", Toast.LENGTH_SHORT).show();
			} 

		}
		
	}

}