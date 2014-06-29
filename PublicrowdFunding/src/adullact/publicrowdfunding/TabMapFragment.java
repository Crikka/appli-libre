package adullact.publicrowdfunding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMapFragment extends Fragment {
	
	private GoogleMap map;
	View rootView;
	GoogleMap googleMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		


		        MapFragment fragment = new MapFragment();
		        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		        transaction.add(R.id.mapView, fragment).commit();
		        
		        rootView = inflater.inflate(R.layout.tabMaps, container, false);

		        googleMap = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.mapView)).getMap();
		 return rootView

		}
	        				
	        				
		/*
			 return view;
		View view = inflater.inflate(R.layout.maps, container, false);

		if (map == null) {
			try {
				// SupportMapFragment test = (SupportMapFragment)
				// getFragmentManager().findFragmentById(R.id.map_frag);

				// map = test.getMap();

				ServerEmulator serveur = ServerEmulator.instance();
				HashMap<String, Project> projets = serveur.getAllProjets();

				Iterator<Map.Entry<String, Project>> it = projets.entrySet()
						.iterator();

				while (it.hasNext()) {
					Entry<String, Project> entry = it.next();

					map.addMarker(new MarkerOptions().position(
							entry.getValue().getPosition()).title(
							entry.getValue().getName()));

				}

			} catch (NullPointerException e) {
				Toast.makeText(getActivity(),
						"Impossible de lancer google Map", Toast.LENGTH_SHORT)
						.show();
			}

		}

		return view;
*/
	}

}