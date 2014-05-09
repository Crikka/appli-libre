package adullact.publicrowdfunding;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CustomMapsActivity extends FragmentActivity {
	private GoogleMap map;
	private LatLng ltlng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		InitialiseMap();

	}

	private void InitialiseMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();

			if (map != null) {

			}

			map.setMyLocationEnabled(true);
			for (int i = 60; i < 70; i++) {
				ltlng = new LatLng(i, i + 30);

				map.addMarker(new MarkerOptions().position(ltlng).title(
						"Hello world:" + i));
			}
		}
	}
}