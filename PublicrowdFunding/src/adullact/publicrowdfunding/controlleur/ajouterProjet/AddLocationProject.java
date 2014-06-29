package adullact.publicrowdfunding.controlleur.ajouterProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.R.id;
import adullact.publicrowdfunding.R.layout;
import adullact.publicrowdfunding.R.menu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddLocationProject extends FragmentActivity implements
		OnMapClickListener {
	private GoogleMap map;
	private Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		InitialiseMap();

		Intent data = getIntent();
		data.getExtras();
		Bundle extras3 = data.getExtras();
		if (extras3 != null) {
			LatLng position = new LatLng(extras3.getDouble("latitude"),
					extras3.getDouble("longitude"));
			marker = map.addMarker(new MarkerOptions().position(position)
					.title("Votre projet"));
		} else {
			marker = null;
		}
	}

	private void InitialiseMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();

			if (map != null) {

			}

			map.setOnMapClickListener(this);
			map.setMyLocationEnabled(true);

		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Toast.makeText(getApplicationContext(),
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.soumission, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.soumettre:
			Intent returnIntent = new Intent();
			returnIntent.putExtra("latitude", marker.getPosition().latitude);
			returnIntent.putExtra("longitude", marker.getPosition().longitude);
			setResult(RESULT_OK, returnIntent);
			finish();

		}
		return false;
	}

}