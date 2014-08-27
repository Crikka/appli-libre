package adullact.publicrowdfunding.controller.detailProject;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabProjectFragment extends Fragment {

	private TextView m_titre;
	private TextView m_description;
	private TextView m_current_funding;
	private TextView m_jour_restant;
	private TextView m_utilisateur_soumission;
	private TextView m_pourcentage_accomplish;
	private TextView m_utilisateur_ville;
	private TextView m_request_funding;

	private Button m_payer;
	private Button m_mail;
	private Button m_website;
	private Button m_call;

	private ImageView m_illustration;
	private ImageView m_avatar;

	private Project projet;
	private User user;

	private LinearLayout layoutConnect;

	private RelativeLayout layout_website;
	private RelativeLayout layout_call;
	private RelativeLayout layout_mail;

	private MainActivity _this;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_detail_project,
				container, false);

		layoutConnect = (LinearLayout) view.findViewById(R.id.connect);

		layout_website = (RelativeLayout) view
				.findViewById(R.id.layout_website);
		layout_call = (RelativeLayout) view.findViewById(R.id.layout_call);
		layout_mail = (RelativeLayout) view.findViewById(R.id.layout_mail);
		isConnect();

		_this = (MainActivity) getActivity();
		projet = _this.projetCurrent;

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
		m_utilisateur_ville = (TextView) view.findViewById(R.id.ville);
		m_request_funding = (TextView) view.findViewById(R.id.sommeRequestFund);

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

		if (projet.getEmail() == null || projet.getEmail().length() == 0) {
			layout_mail.setVisibility(View.GONE);
		} else {
			m_mail.setText(projet.getEmail());
		}

		if (projet.getWebsite() == null || projet.getWebsite().length() == 0) {
			layout_website.setVisibility(View.GONE);
		} else {
			m_website.setText(projet.getWebsite());
		}

		if (projet.getPhone() == null || projet.getPhone().length() == 0) {
			layout_call.setVisibility(View.GONE);
		} else {
			m_call.setText(projet.getPhone());
		}

		m_request_funding.setText(projet.getRequestedFunding() + "€");

		projet.getUser(new WhatToDo<User>() {

			@Override
			public void hold(User resource) {
				try{
				user = resource;
				m_utilisateur_soumission.setText(Share.formatString(resource.getPseudo()));
				m_utilisateur_ville.setText(Share.formatString(resource.getCity()));
				if (user.getGender().equals("0")) {
					m_avatar.setImageResource(R.drawable.male_user_icon);
				} else {
					m_avatar.setImageResource(R.drawable.female_user_icon);
				}
				}catch(Exception e){
					e.printStackTrace();
				}

			}

			@Override
			public void eventually() {

			}

		});

		LinearLayout userLayoutButton = (LinearLayout) view
				.findViewById(R.id.layoutUser);
		userLayoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				try {
					String id = user.getCache().getResourceId();
					Context c = getActivity().getBaseContext();
					Intent in = new Intent(
							c,
							adullact.publicrowdfunding.controller.profile.MainActivity.class);
					in.putExtra("myCount", false);
					in.putExtra("id", id);
					startActivity(in);

				} catch (NullPointerException e) {
					Toast.makeText(getActivity(), "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();
				}
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

				// Start Activity payer

				Intent in = new Intent(
						_this,
						adullact.publicrowdfunding.controller.participate.MainActivity.class);
				in.putExtra("projectId", projet.getResourceId());
				_this.startActivity(in);

			}
		});
		return view;
	}

	public void isConnect() {
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
		}
	}

}