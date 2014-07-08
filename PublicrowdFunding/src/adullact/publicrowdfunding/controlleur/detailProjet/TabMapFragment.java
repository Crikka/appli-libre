package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMapFragment extends Fragment {

	private Drawable m_favorite;
	private boolean m_Is_favorite;
	private Project projet;
	private MapFragment fragment;
	private FragmentManager fm;
	private ProgressDialog mprogressDialog;

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

		fragment = new MapFragment();

		fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.tabcontent, fragment, "mapid").commit();

		MainActivity activity = (MainActivity) getActivity();
		projet = activity.getIdProjet();

		rootView = inflater.inflate(R.layout.tab_maps, container, false);

		final Handler handler = new Handler();

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				googleMap = ((MapFragment) fm.findFragmentByTag("mapid"))
						.getMap();

				if (googleMap != null) {

					googleMap.addMarker(
							new MarkerOptions().position(projet.position()))
							.setVisible(true);

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