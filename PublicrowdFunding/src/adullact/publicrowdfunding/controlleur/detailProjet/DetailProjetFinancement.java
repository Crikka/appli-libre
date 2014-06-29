package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailProjetFinancement extends Fragment {

	// private Project projet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet_financement,
				container, false);

		/*
		 * OneProjectActivity activity = (OneProjectActivity) getActivity();
		 * projet = activity.getIdProjet();
		 */
		
		GraphiqueView graph = (GraphiqueView) view.findViewById(R.id.graphique);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		android.view.ViewGroup.LayoutParams params = graph.getLayoutParams();
		params.height = metrics.widthPixels;
		graph.setLayoutParams(params);

		return view;

	}

}
