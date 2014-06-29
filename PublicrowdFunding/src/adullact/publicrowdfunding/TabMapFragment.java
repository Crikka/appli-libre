package adullact.publicrowdfunding;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class TabMapFragment extends Fragment {

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
	
		//googleMap = mySupportMapFragment.getMap();
		
		
	
		//	googleMap = ((MapFragment) fm.findFragmentByTag("mapid")).getMap();
			
		return rootView;

		/*
		 * return view; View view = inflater.inflate(R.layout.maps, container,
		 * false);
		 * 
		 * if (map == null) { try { // SupportMapFragment test =
		 * (SupportMapFragment) //
		 * getFragmentManager().findFragmentById(R.id.map_frag);
		 * 
		 * // map = test.getMap();
		 * 
		 * ServerEmulator serveur = ServerEmulator.instance(); HashMap<String,
		 * Project> projets = serveur.getAllProjets();
		 * 
		 * Iterator<Map.Entry<String, Project>> it = projets.entrySet()
		 * .iterator();
		 * 
		 * while (it.hasNext()) { Entry<String, Project> entry = it.next();
		 * 
		 * map.addMarker(new MarkerOptions().position(
		 * entry.getValue().getPosition()).title( entry.getValue().getName()));
		 * 
		 * }
		 * 
		 * } catch (NullPointerException e) { Toast.makeText(getActivity(),
		 * "Impossible de lancer google Map", Toast.LENGTH_SHORT) .show(); }
		 * 
		 * }
		 * 
		 * return view;
		 */
	}

}