package adullact.publicrowdfunding;

import java.util.Vector;

import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMapFragment extends Fragment {

	private MapFragment fragment;
	private FragmentManager fm;
	private ProgressDialog mprogressDialog;
	private Vector<Project> projets = new Vector<Project>();
	View rootView;
	GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mprogressDialog = new ProgressDialog(getActivity());
		mprogressDialog.setMessage("Chargement en cours...");
		mprogressDialog.setTitle("Google Map");
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mprogressDialog.show();

		MainActivity activity = (MainActivity) getActivity();
		projets = activity.getProjets();

		fragment = new MapFragment();
		fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tabcontent, fragment, "mapid").commit();

		rootView = inflater.inflate(R.layout.tab_maps, container, false);

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				googleMap = ((MapFragment) fm.findFragmentByTag("mapid"))
						.getMap();

				if (googleMap != null) {
					for (Project proj : projets) {
						MarkerOptions marker = new MarkerOptions();
						marker.position(proj.position());
						marker.title(proj.name());
						googleMap.addMarker(marker);
								
					}

					handler.removeCallbacksAndMessages(null);
					mprogressDialog.dismiss();
				}

				else {
					handler.postDelayed(this, 500);
				}
			}
		}, 500);

		return rootView;
	}
}