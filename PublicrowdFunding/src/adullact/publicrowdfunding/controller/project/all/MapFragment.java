package adullact.publicrowdfunding.controller.project.all;

import java.util.ArrayList;
import java.util.HashMap;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.R.id;
import adullact.publicrowdfunding.R.layout;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ferrand and Nelaupe
 */
public class MapFragment extends Fragment implements OnInfoWindowClickListener {

	private SupportMapFragment fragment;
	private FragmentManager fm;
	private ProgressDialog mprogressDialog;
	private View rootView;
	private GoogleMap googleMap;
	private final HashMap<Marker, Project> markers = new HashMap<Marker, Project>();

	private ArrayList<Project> projets;

	private MapFragment _this;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			rootView = inflater.inflate(R.layout.activity_maps, container,
					false);

		} catch (Exception e) {
			TextView text = new TextView(getActivity());
			text.setText("Impossible de charger Google Map");
			return text;
		}

		projets = new ArrayList<Project>();

		_this = this;

		mprogressDialog = new ProgressDialog(getActivity());
		mprogressDialog.setMessage("Chargement en cours...");
		mprogressDialog.setTitle("Google Map");
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mprogressDialog.show();

		fragment = new SupportMapFragment();
		fm = getFragmentManager();

		final SyncServerToLocal sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {
			@Override
			public void holdAll(ArrayList<Project> projects) {

				projets = new ArrayList<Project>(sync
						.restrictToValidatedProjects());

				initMaps();

			}
		});

		return rootView;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {

		String id = markers.get(marker).getResourceId();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment fragment = new adullact.publicrowdfunding.controller.project.details.ProjectPagerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("idProject", id);
		fragment.setArguments(bundle);
		fragment.setHasOptionsMenu(true);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();

	}

	public void initMaps() {

		try {

			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.mapView, fragment, "mapAllFragment").commit();
			fm.executePendingTransactions();

			googleMap = ((SupportMapFragment) fm
					.findFragmentByTag("mapAllFragment")).getMap();

			for (Project proj : projets) {
				MarkerOptions marker = new MarkerOptions();
				marker.position(proj.getPosition());
				marker.title(proj.getName());
				Marker m = googleMap.addMarker(marker);

				markers.put(m, proj);
			}

			googleMap.setOnInfoWindowClickListener(_this);
			googleMap
					.setInfoWindowAdapter(new adullact.publicrowdfunding.controller.adaptor.MarkerWindowAdaptor(
							getLayoutInflater(null), markers));

			googleMap.setMyLocationEnabled(true);
			
			LatLng localisation = new LatLng(46.937199, 2.429674);

			CameraPosition currentPlace = new CameraPosition.Builder()
					.target(localisation).zoom(6f).build();

			googleMap.moveCamera(CameraUpdateFactory
					.newCameraPosition(currentPlace));

			
			mprogressDialog.dismiss();

		} catch (Exception e) {
			System.out.println("Impossible de lancer Google Map");
		}
	}
}
