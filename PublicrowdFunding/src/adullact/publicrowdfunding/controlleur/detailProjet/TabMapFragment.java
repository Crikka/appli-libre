package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class TabMapFragment extends Fragment {

	private Drawable m_favorite;
	private boolean m_Is_favorite;
	private GoogleMap map;
	private Project projet;


	View rootView;
	GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		MapFragment fragment = new MapFragment();

		FragmentManager fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tabcontent, fragment, "mapid").commit();
	
		rootView = inflater.inflate(R.layout.tab_maps, container, false);
	
		return rootView;
	}
}
	
	
	
	/*
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		String id = getIntent().getExtras().getString("key");
		if(id == null){
			Toast.makeText(getApplicationContext(), "Un erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();
		projet = projets.get(id);

		if (projet == null) {
			Toast.makeText(getApplicationContext(), "Un erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		
		System.out.println("lancement de google map");
		try {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();
			map.addMarker(new MarkerOptions().position(projet.getPosition()).title(
					projet.name()));

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(projet.getPosition(), 4));
		} catch (NullPointerException e) {
			Toast.makeText(getApplication(), "Impossible de lancer google Map",
					Toast.LENGTH_SHORT).show();
		}

	
	}

}
*/
