package adullact.publicrowdfunding.controller.project.add;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Ferrand and Nelaupe
 */
public class addLocationProjectFragment extends Fragment implements
		OnMapClickListener {

	private Marker marker;

	View rootView;
	GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_add_location, container,
				false);

		BreadCrumbView breadCrumbView = (BreadCrumbView) rootView
				.findViewById(R.id.breadcrumb);
		breadCrumbView.setPosition(3);

		FragmentManager fm = getActivity().getSupportFragmentManager();

		googleMap = ((SupportMapFragment) fm.findFragmentById(R.id.map))
				.getMap();

		googleMap.setOnMapClickListener(this);
		googleMap.setMyLocationEnabled(true);

		final Bundle args = this.getArguments();

		Button button = (Button) rootView.findViewById(R.id.button_valider);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (marker == null) {
					Toast.makeText(
							getActivity().getApplicationContext(),
							"Cliquez sur la carte pour positionner votre projet",
							Toast.LENGTH_LONG).show();
					return;
				}

				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction()
						.disallowAddToBackStack();
				Fragment fragment = new validationFragment();
				args.putDouble("location_latitude", marker.getPosition().latitude);
				args.putDouble("location_longitude", marker.getPosition().longitude);
				fragment.setArguments(args);
				fragment.setHasOptionsMenu(true);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

			}

		});

		return rootView;
	}

	@Override
	public void onMapClick(LatLng arg0) {

		if (marker == null) {
			marker = googleMap.addMarker(new MarkerOptions().position(arg0)
					.title("Votre projet"));
			Toast.makeText(getActivity().getApplicationContext(),
					"Emplacement de votre projet ajouté", Toast.LENGTH_SHORT)
					.show();
		} else {
			marker.remove();
			marker = googleMap.addMarker(new MarkerOptions().position(arg0)
					.title("Votre projet"));
			Toast.makeText(getActivity().getApplicationContext(),
					"Emplacement modifié", Toast.LENGTH_SHORT).show();
		}
	}

}