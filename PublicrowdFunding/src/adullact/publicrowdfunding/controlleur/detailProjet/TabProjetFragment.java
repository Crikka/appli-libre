package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.choisirMontantDialog;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
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
	private TextView m_current_funding;
	private TextView m_nombre_participants;
	private TextView m_jour_restant;
	private TextView m_utilisateur_soumission;
	private TextView m_pourcentage_accomplish;

	private Button m_payer;
	private Button m_mail;
	private Button m_website;
	private Button m_call;

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
		graph.setProject(projet);

		m_titre = (TextView) view.findViewById(R.id.titre_projet_detail);
		m_description = (TextView) view.findViewById(R.id.detail_projet_detail);
		m_payer = (Button) view.findViewById(R.id.payer);
		m_nombre_participants = (TextView) view
				.findViewById(R.id.nombre_participants_detail);
		m_jour_restant = (TextView) view
				.findViewById(R.id.nombre_jour_restant_detail);
		m_utilisateur_soumission = (TextView) view
				.findViewById(R.id.utilisateur_soumission);
		m_current_funding = (TextView) view.findViewById(R.id.sommeFund);

		m_pourcentage_accomplish = (TextView) view
				.findViewById(R.id.pourcentage_accomplit);

		m_mail = (Button) view.findViewById(R.id.mail);
		m_website = (Button) view.findViewById(R.id.website);
		m_call = (Button) view.findViewById(R.id.phone);

		m_illustration = (ImageView) view.findViewById(R.id.icon);

		if (projet.getIllustration() != 0) {
			m_illustration.setImageResource(Utility.getDrawable(projet
					.getIllustration()));
		} else {
			m_illustration.setImageResource(R.drawable.ic_launcher);
		}

		projet.getUser(new WhatToDo<User>() {

			@Override
			public void hold(User resource) {
				System.out.println(resource);
				m_utilisateur_soumission.setText(resource.getPseudo());
			}

			@Override
			public void eventually() {

			}

		});

		m_pourcentage_accomplish
				.setText(projet.getPercentOfAchievement() + "%");
		m_current_funding.setText(projet.getCurrentFunding() + "€");
		projet.getCreationDate();
		m_titre.setText(projet.getName());
		m_description.setText(projet.getDescription());
		if (projet.getNumberOfDayToEnd() > 1) {
			m_jour_restant
					.setText("" + projet.getNumberOfDayToEnd() + " jours");
		} else {
			m_jour_restant.setText("" + projet.getNumberOfDayToEnd() + " jour");
		}

		m_payer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				choisirMontantDialog alertDialogBuilder = new choisirMontantDialog(
						getActivity());
				alertDialogBuilder.show();

			}
		});

		return view;
	}
}