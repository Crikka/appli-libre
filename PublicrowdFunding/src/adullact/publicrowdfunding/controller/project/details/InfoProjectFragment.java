package adullact.publicrowdfunding.controller.project.details;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ferrand and Nelaupe
 */
public class InfoProjectFragment extends Fragment {

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

	//private ImageView m_illustration;
	private ImageView m_avatar;

	private Project projetToDisplay;
	private User user;

	private FrameLayout layoutConnect;

	private RelativeLayout layout_website;
	private RelativeLayout layout_call;
	private RelativeLayout layout_mail;

	private GraphiqueView graph;

	private View view;

	private FragmentManager fm;

	private LinearLayout loading;

	private FrameLayout filter;
	
	private ScrollView showLoaded;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		view = inflater.inflate(R.layout.fragment_detail_project, container,
				false);
		fm = this.getActivity().getSupportFragmentManager();
		
		filter = (FrameLayout)  getActivity().getWindow().getDecorView().findViewById(R.id.big_filter);
		filter.setVisibility(View.GONE);
		layoutConnect = (FrameLayout) view.findViewById(R.id.connect);

		
		showLoaded = (ScrollView) view.findViewById(R.id.showLoaded);
		showLoaded.setVisibility(View.GONE);
		
		loading = (LinearLayout) view.findViewById(R.id.loading);

		layout_website = (RelativeLayout) view
				.findViewById(R.id.layout_website);
		layout_call = (RelativeLayout) view.findViewById(R.id.layout_call);
		layout_mail = (RelativeLayout) view.findViewById(R.id.layout_mail);

		graph = (GraphiqueView) view.findViewById(R.id.graphique);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		android.view.ViewGroup.LayoutParams params = graph.getLayoutParams();
		params.height = 500;
		graph.setLayoutParams(params);

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

		//m_illustration = (ImageView) view.findViewById(R.id.icon);

		m_avatar = (ImageView) view.findViewById(R.id.avatar);

		isConnect();



		Bundle bundle = this.getArguments();
        if (bundle != null) {
			String idProject = bundle.getString("idProject");
			Cache<Project> projet = new Project().getCache(idProject);
            projet.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {
                    projetToDisplay = project;
					graph.setProject(projetToDisplay);
					graph.invalidate();
					displayInfo();
				}
			});
		} else{
			this.getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		}

		return view;
	}

	public void isConnect() {
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
		} catch (NoAccountExistsInLocal e1) {
			// layoutConnect.setVisibility(View.GONE);
		}
	}

	public void displayInfo() {
		/*
		if (projetToDisplay.getIllustration() != 0) {
			m_illustration.setImageResource(Utility.getDrawable(projetToDisplay
					.getIllustration()));
		} else {
			m_illustration.setImageResource(R.drawable.ic_launcher);
		}*/

		if (projetToDisplay.getEmail() == null
				|| projetToDisplay.getEmail().length() == 0) {
			layout_mail.setVisibility(View.GONE);
		} else {
			m_mail.setText(projetToDisplay.getEmail());
		}

		if (projetToDisplay.getWebsite() == null
				|| projetToDisplay.getWebsite().length() == 0) {
			layout_website.setVisibility(View.GONE);
		} else {
			m_website.setText(projetToDisplay.getWebsite());
		}

		if (projetToDisplay.getPhone() == null
				|| projetToDisplay.getPhone().length() == 0) {
			layout_call.setVisibility(View.GONE);
		} else {
			m_call.setText(projetToDisplay.getPhone());
		}

		m_request_funding.setText(projetToDisplay.getRequestedFunding() + "€");

		projetToDisplay.getUser(new HoldToDo<User>() {

			@Override
			public void hold(User resource) {
				try {
					user = resource;
					m_utilisateur_soumission.setText(Share
							.formatString(resource.getPseudo()));
					m_utilisateur_ville.setText(Share.formatString(resource
							.getCity()));
					if (user.getGender().equals("0")) {
						m_avatar.setImageResource(R.drawable.male_user_icon);
					} else {
						m_avatar.setImageResource(R.drawable.female_user_icon);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		LinearLayout userLayoutButton = (LinearLayout) view
				.findViewById(R.id.layoutUser);

		userLayoutButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				try {

					FragmentTransaction ft = fm.beginTransaction();

					// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
					Fragment fragment = new adullact.publicrowdfunding.controller.profile.ProfilePagerFragment();
					Bundle bundle = new Bundle();
					bundle.putString("idUser", user.getResourceId());
					fragment.setArguments(bundle);
					fragment.setHasOptionsMenu(false);
					ft.addToBackStack(null);
					ft.replace(R.id.content_frame, fragment);
					ft.commit();

				} catch (NullPointerException e) {
					Toast.makeText(getActivity(), "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		m_pourcentage_accomplish.setText(projetToDisplay
				.getPercentOfAchievement() + "%");
		m_current_funding.setText(projetToDisplay.getCurrentFunding() + "€");
		projetToDisplay.getCreationDate();
		m_titre.setText(projetToDisplay.getName());
		m_description.setText(projetToDisplay.getDescription());
		if (projetToDisplay.getNumberOfDayToEnd() > 1) {
			m_jour_restant.setText("" + projetToDisplay.getNumberOfDayToEnd()
					+ " jours");
		} else {
			m_jour_restant.setText("" + projetToDisplay.getNumberOfDayToEnd()
					+ " jour");
		}
		

		m_payer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				FragmentTransaction ft = fm.beginTransaction();

				Fragment fragment = new adullact.publicrowdfunding.controller.project.details.ParticipatePopup();
				Bundle bundle = new Bundle();
				bundle.putString("idProject", projetToDisplay.getResourceId());
				fragment.setArguments(bundle);
				ft.addToBackStack(null);
				ft.setCustomAnimations(R.anim.popup_enter, R.anim.no_anim);
				ft.add(R.id.big_font, fragment);
				ft.commit();

				filter.setVisibility(View.VISIBLE);

			}
		});
		showLoaded.setVisibility(View.VISIBLE);
		loading.setVisibility(View.GONE);
		//view.invalidate();
	}

}