package adullact.publicrowdfunding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabAllProjectsMaps extends FragmentActivity {
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		if (map == null) {
			try {
				map = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map_frag)).getMap();

				ServerEmulator serveur = ServerEmulator.instance();
				HashMap<String, Project> projets = serveur.getAllProjets();

				Iterator<Map.Entry<String, Project>> it = projets.entrySet()
						.iterator();

				while (it.hasNext()) {
					Entry<String, Project> entry = it.next();

										
					
					map.addMarker(new MarkerOptions().position(entry.getValue().getPosition()).title(
							entry.getValue().getName()));
					
					
				}


			} catch (NullPointerException e) {
				Toast.makeText(getApplication(),
						"Impossible de lancer google Map", Toast.LENGTH_SHORT)
						.show();
			}

		}

	}

}