package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.choisirMontantDialog;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabProjetFragment extends Fragment {

	private TextView m_titre;
	private TextView m_description;
	private TextView m_current_funding;
	private TextView m_jour_restant;
	private TextView m_utilisateur_soumission;
	private TextView m_pourcentage_accomplish;

	private Button m_payer;
	private Button m_mail;
	private Button m_website;
	private Button m_call;
	
	private Button m_connexion;

	private ImageView m_illustration;

	private Project projet;
	private User user;
	
	private LinearLayout layoutConnect;
	private LinearLayout layoutDisconnect;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet, container,
				false);

		
		layoutConnect = (LinearLayout) view.findViewById(R.id.connect);
		layoutDisconnect = (LinearLayout) view.findViewById(R.id.disconnect);

		isConnect();
		
	
		final MainActivity activity = (MainActivity) getActivity();
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

		
		m_connexion = (Button) view.findViewById(R.id.connexion);
		m_connexion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(
						activity.getApplicationContext(),
						ConnexionActivity.class);
				startActivity(in);
			}
		});

		
		
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
				user = resource;
				m_utilisateur_soumission.setText(resource.getPseudo());
			}

			@Override
			public void eventually() {

			}

		});
		
		LinearLayout userLayoutButton= (LinearLayout) view.findViewById(R.id.layoutUser);
		userLayoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				String id = user.getCache().getResourceId();
				Context c = getActivity().getBaseContext();
				Intent in = new Intent(
						c,
						adullact.publicrowdfunding.controlleur.profile.MainActivity.class);
				in.putExtra("myCount", false);
				in.putExtra("id", id);
				startActivity(in);

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
	
	
	public void isConnect() {
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
			layoutDisconnect.setVisibility(View.GONE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
			layoutDisconnect.setVisibility(View.VISIBLE);
		}
	}
	
	
}