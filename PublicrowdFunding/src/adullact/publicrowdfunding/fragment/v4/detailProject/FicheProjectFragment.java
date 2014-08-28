package adullact.publicrowdfunding.fragment.v4.detailProject;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CustomProgressBar;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Calcul;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class FicheProjectFragment extends Fragment {

	Context mContext;
	int layoutResourceId;
	ArrayList<Project> data;
	UserHolder holder;
	LinearLayout loading;

	private static class UserHolder {

		public TextView titre_projet_liste;
		public TextView description_projet_liste;
		public TextView temps_restant_projet_liste;
		public CustomProgressBar avancement_projet_liste;
		public ImageView illustration;
		public TextView sommeFunded;
		public TextView sommeDemander;
		public TextView distance;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.fiche_project, container, false);
		holder = new UserHolder();

		loading = (LinearLayout) v.findViewById(R.id.loading);

		holder.titre_projet_liste = (TextView) v
				.findViewById(R.id.titre_projet_liste);
		holder.description_projet_liste = (TextView) v
				.findViewById(R.id.description_projet_liste);
		holder.temps_restant_projet_liste = (TextView) v
				.findViewById(R.id.nombre_jour_restant_detail);
		holder.avancement_projet_liste = (CustomProgressBar) v
				.findViewById(R.id.avancement_projet_liste);
		holder.illustration = (ImageView) v.findViewById(R.id.icon);
		holder.sommeDemander = (TextView) v.findViewById(R.id.sommeeDemande);

		holder.sommeFunded = (TextView) v.findViewById(R.id.sommeFund);
		holder.distance = (TextView) v.findViewById(R.id.distance);

		v.setTag(holder);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			String idProject = bundle.getString("idProject");
			System.out.println("Affichage du projet : " + idProject);
			Cache<Project> projet = new Project().getCache(idProject)
					.forceRetrieve();
			projet.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {
					displayInfo(project);
				}
			});
		} else {
			getActivity().finish();
		}

		return v;

	}

	public void displayInfo(Project projet) {
		holder.titre_projet_liste.setText(projet.getName());
		holder.description_projet_liste.setText(projet.getDescription());
		holder.temps_restant_projet_liste.setText(projet.getNumberOfDayToEnd()
				+ " jours");

		holder.avancement_projet_liste.setProgress(projet
				.getPercentOfAchievement());
		holder.sommeDemander.setText(projet.getRequestedFunding() + "€");
		holder.sommeFunded.setText(projet.getCurrentFunding() + "€");
		if (projet.getIllustration() != 0) {
			holder.illustration.setImageResource(Utility.getDrawable(projet
					.getIllustration()));
		} else {
			holder.illustration.setImageResource(R.drawable.ic_launcher);
		}

		holder.distance.setVisibility(View.GONE);
		try {
			holder.distance.setText("Distance : "
					+ Calcul.diplayDistance(Share.position,
							projet.getPosition()));
			holder.distance.setVisibility(View.VISIBLE);
		} catch (NullPointerException e) {
			holder.distance.setVisibility(View.GONE);
		}
		loading.setVisibility(View.GONE);
	}

	

}