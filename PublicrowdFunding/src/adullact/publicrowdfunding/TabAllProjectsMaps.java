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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabAllProjectsMaps extends Fragment {
	private GoogleMap map;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	      View view = inflater.inflate(R.layout.maps, container, false);
	      
	      if (map == null) {
				try {
					//SupportMapFragment test =	(SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_frag);

				//	map = test.getMap();

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
					Toast.makeText(view.getContext().getApplicationContext(),
							"Impossible de lancer google Map", Toast.LENGTH_SHORT)
							.show();
				}

			}
	      
	      
	      
		return view;

	      
	}

}