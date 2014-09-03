package adullact.publicrowdfunding.controller.project.add;

import java.util.ArrayList;
import java.util.Calendar;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.adaptor.CarouselAdaptor;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

/**
 * @author Ferrand and Nelaupe
 */
public class addProjectFragment extends Fragment {

	private EditText m_titre;
	private EditText m_Description;
	private EditText m_edit_text_somme;
	private EditText m_email;
	private EditText m_phone;
	private EditText m_website;

	private TextView m_user_pseudo;
	private TextView m_user_ville;

	private DatePicker m_dateFin;

	private Button m_valider;

	private Context context;

	private int m_illustration;

	private addLocationProjectFragment _map;

	private User user;

	private ImageView avatar;

	private HorizontalCarouselStyle mStyle;
	private HorizontalCarouselLayout mCarousel;
	private CarouselAdaptor mAdapter;
	private ArrayList<Integer> mData;
	
	private LinearLayout loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_project, container,
				false);

		context = this.getActivity().getApplicationContext();

		m_titre = (EditText) view.findViewById(R.id.titre);
		m_Description = (EditText) view.findViewById(R.id.description);
		m_dateFin = (DatePicker) view.findViewById(R.id.date_de_fin);
		m_valider = (Button) view.findViewById(R.id.button_valider);
		m_edit_text_somme = (EditText) view.findViewById(R.id.edit_text_somme);
		m_user_pseudo = (TextView) view
				.findViewById(R.id.utilisateur_soumission);
		m_user_ville = (TextView) view.findViewById(R.id.ville);

		m_email = (EditText) view.findViewById(R.id.mail);
		m_website = (EditText) view.findViewById(R.id.website);
		m_phone = (EditText) view.findViewById(R.id.phone);

		m_edit_text_somme = (EditText) view.findViewById(R.id.edit_text_somme);

		avatar = (ImageView) view.findViewById(R.id.avatar);

		mCarousel = (HorizontalCarouselLayout) view
				.findViewById(R.id.carousel_layout);
		
		loading = (LinearLayout) view
		.findViewById(R.id.loading);
		
		mCarousel.setOnCarouselViewChangedListener(new CarouselInterface() {

			@Override
			public void onItemChangedListener(View v, int position) {
				m_illustration = position;
			}
		});

		m_valider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/*
				 * Validation
				 */
			}
		});

		loadContent();

		return view;
	}

	public void addProjectRequest() {

		String titre = null;
		if (m_titre.length() == 0) {
			Toast.makeText(context, "Merci de mettre un titre",
					Toast.LENGTH_SHORT).show();
			return;
		}
		titre = m_titre.getText().toString();

		String description = null;
		if (m_Description.length() == 0) {
			Toast.makeText(context, "Merci de mettre une description",
					Toast.LENGTH_SHORT).show();
			return;
		}
		description = m_Description.getText().toString();

		String somme = null;
		if (m_edit_text_somme.length() == 0) {
			Toast.makeText(context, "Merci de mettre la somme désiré",
					Toast.LENGTH_SHORT).show();
			return;
		}
		somme = m_edit_text_somme.getText().toString();

		try {
			int s = Integer.parseInt(somme);
			if (s < 1) {
				Toast.makeText(context, "Le montant est invalide",
						Toast.LENGTH_SHORT).show();
				return;
			}

		} catch (Exception e) {
			Toast.makeText(context, "Le montant est invalide",
					Toast.LENGTH_SHORT).show();
			return;
		}

		int day = m_dateFin.getDayOfMonth();
		int month = m_dateFin.getMonth() + 1;
		int year = m_dateFin.getYear();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String Stryear = "" + year;
		String StrMonth = null;
		if (month < 10) {
			StrMonth = "0" + month;
		} else {
			StrMonth = "" + month;
		}

		String StrDay = "" + day;
		LatLng position = _map.getPosition();
		new Project(titre, description, user.getResourceId(), somme,
				Utility.stringToDateTime(Stryear + "-" + StrMonth + "-"
						+ StrDay + " 00:00:00"),
				Utility.stringToDateTime(Stryear + "-" + StrMonth + "-"
						+ StrDay + " 00:00:00"), position, m_illustration,
				m_email.getText().toString(), m_website.getText().toString(),
				m_phone.getText().toString(), false)
				.serverCreate(new CreateEvent<Project>() {
					@Override
					public void errorResourceIdAlreadyUsed() {
						System.out.println("id déja utilisé");
						Toast.makeText(context, "Erreur interne du serveur",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onCreate(Project resource) {
						System.out.println("Le projet à bien était ajouté");
						Toast.makeText(context,
								"Le projet à bien était ajouté",
								Toast.LENGTH_SHORT).show();
						System.out.println("Tout est ok");
					}

					@Override
					public void errorAuthenticationRequired() {
						Toast.makeText(context, "Vous devez vous authentifier",
								Toast.LENGTH_SHORT).show();
						System.out.println("Auth required");
					}

					@Override
					public void errorNetwork() {
						Toast.makeText(context, "Probléme avec le réseau",
								Toast.LENGTH_SHORT).show();
						System.out.println("network error");
					}

					@Override
					public void errorServer() {
						Toast.makeText(context, "Erreur interne du serveur",
								Toast.LENGTH_SHORT).show();

					}
				});

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void supprimerCalendarView() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			m_dateFin.setCalendarViewShown(false);
		}

	}

	public void loadMaps() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = new addLocationProjectFragment();
		ft.replace(R.id.mapView, fragment);
		ft.commit();

		_map = (addLocationProjectFragment) fragment;

	}

	public void loadContent() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Account compte = Account.getOwn();
					compte.getUser(new HoldToDo<User>() {

						@Override
						public void hold(User resource) {

							user = resource;
							System.out.println(user.getResourceId());

							m_user_pseudo.setText(resource.getPseudo());
							m_user_ville.setText(resource.getCity());
							if (resource.getGender().equals("0")) {
								avatar.setImageResource(R.drawable.male_user_icon);
							} else {
								avatar.setImageResource(R.drawable.female_user_icon);
							}

						}

					});
				} catch (NoAccountExistsInLocal e) {
					System.out.println("L'utilisateur n'est pas connecté !");
				}

				m_illustration = 0;

				mData = new ArrayList<Integer>();
				mData.add(R.drawable.ic_launcher);
				mData.add(R.drawable.roi);
				mData.add(R.drawable.basketball);
				mData.add(R.drawable.plante);
				mData.add(R.drawable.fete);

				mAdapter = new CarouselAdaptor(context);
				mAdapter.setData(mData);
				mStyle = new HorizontalCarouselStyle(context,
						HorizontalCarouselStyle.NO_STYLE);
				mCarousel.setStyle(mStyle);
				mCarousel.setAdapter(mAdapter);

				supprimerCalendarView();
				loading.setVisibility(View.GONE);

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				loadMaps();
			}
		}).start();

	}
}