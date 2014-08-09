package adullact.publicrowdfunding.controlleur.detailProjet;

import org.joda.time.DateTime;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CustomProgressBar;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.shared.Utility;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TabProjetFragment extends Fragment {

	private TextView m_titre;
	private TextView m_description;
	private TextView m_nombre_participants;
	private TextView m_jour_restant;
	private TextView m_utilisateur_soumission;
	private Button m_payer;
	private CustomProgressBar m_progression;
	private ImageView m_illustration;

	private Project projet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet, container,
				false);

		MainActivity activity = (MainActivity) getActivity();
		projet = activity.getIdProjet();

		GraphiqueView graph = (GraphiqueView) view.findViewById(R.id.graphique);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		android.view.ViewGroup.LayoutParams params = graph.getLayoutParams();
		params.height = metrics.widthPixels + 100;
		graph.setLayoutParams(params);

		m_titre = (TextView) view.findViewById(R.id.titre_projet_detail);
		m_description = (TextView) view.findViewById(R.id.detail_projet_detail);
		m_payer = (Button) view.findViewById(R.id.payer);
		m_nombre_participants = (TextView) view
				.findViewById(R.id.nombre_participants_detail);
		m_jour_restant = (TextView) view
				.findViewById(R.id.nombre_jour_restant_detail);
		m_utilisateur_soumission = (TextView) view
				.findViewById(R.id.utilisateur_soumission);
		m_progression = (CustomProgressBar) view
				.findViewById(R.id.avancement_projet_liste);
		m_illustration = (ImageView) view.findViewById(R.id.icon);

		if (projet.getIllustration() != 0) {
			m_illustration.setImageResource(Utility.getDrawable(projet
					.getIllustration()));
		} else {
			m_illustration.setImageResource(R.drawable.ic_launcher);
		}

		if (m_progression == null) {
			System.out.println("c'est nul");
		}

		System.out.println("Progression");
		m_progression.setArgent(5000 * projet.getPercentOfAchievement() / 100);
		m_progression.setProgress(projet.getPercentOfAchievement());
		m_progression.setMaxArgent(5000);
		projet.getCreationDate();
		m_titre.setText(projet.getName());
		m_description.setText(projet.getDescription());
		DateTime date = projet.getCreationDate();

		m_jour_restant.setText("Date de creation : " + date.getDayOfMonth()
				+ "/" + date.getMonthOfYear() + "/" + date.getYear());

		m_payer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				projet.finance("50");
				m_progression.setProgress(m_progression.getProgress() + 1);
				m_progression.setArgent(Integer.parseInt(projet
						.getCurrentFunding()));

				/*
				 * Paypal choisirMontantDialog alertDialogBuilder = new
				 * choisirMontantDialog( getActivity());
				 * alertDialogBuilder.show();
				 */
			}
		});
		return view;
	}

}