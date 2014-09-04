package adullact.publicrowdfunding.controller.profile.preferences;

import adullact.publicrowdfunding.MainActivity;
import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.support.v4.preference.PreferenceFragment;
import android.widget.Toast;

/**
 * @author Ferrand and Nelaupe
 */
public class preferencesFragment extends PreferenceFragment {

	private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.layout.fragment_preferences);

		/*
		 * Utilisateur
		 */

		final EditTextPreference ville = (EditTextPreference) findPreference("edittext_preference_ville");
		final EditTextPreference nom = (EditTextPreference) findPreference("edittext_preference_nom");
		final EditTextPreference prenom = (EditTextPreference) findPreference("edittext_preference_prenom");
		final EditTextPreference email = (EditTextPreference) findPreference("edittext_preference_email");

		final SwitchPreference genre = (SwitchPreference) findPreference("genre");
		genre.setSwitchTextOn("Femme");
		genre.setSwitchTextOff("Homme");

		/*
		 * Compte
		 */

		CheckBoxPreference isAdmin = (CheckBoxPreference) findPreference("checkbox_admin");

		try {
			Account account = Account.getOwn();
			if (account.isAdmin()) {
				isAdmin.setChecked(true);
			}
			account.getUser(new HoldToDo<User>() {

				@Override
				public void hold(User resource) {

					user = resource;
					resource.getName();
					ville.setText(resource.getCity());
					nom.setText(resource.getName());
					prenom.setText(resource.getFirstName());
					email.setText("");
					if (resource.getGender().equals("1")) {
						genre.setChecked(true);
					} else {
						genre.setChecked(false);
					}

				}

			});
		} catch (NoAccountExistsInLocal e) {
			System.out.println("Il n'a rien à faire là !!");
		}

		ville.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				String value = (String) newValue;
				System.out.println("La ville a change : " + value);
				user.setCity(value);
				update();
				return true;
			}
		});

		genre.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				Boolean value = (Boolean) newValue;
				String gender = null;
				if (value) {
					gender = "1";
				} else {
					gender = "0";
				}

				System.out.println("Le genre a change : " + gender);
				user.setGender(gender);
				update();
				return true;
			}
		});
		
		email.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				String value = (String) newValue;
				System.out.println("L'émail à change : " + value);
				//user.setEmail(value);
				update();
				return true;
			}
		});


	}

	public void update() {
		super.onResume();
		MainActivity _this = (MainActivity)	this.getActivity();
		_this.isConnect();

		try {
			Account account = Account.getOwn();
			account.getUser(new HoldToDo<User>() {

				@Override
				public void hold(User user) {
					user.serverUpdate(new UpdateEvent<User>() {

						@Override
						public void onUpdate(User resource) {
							Toast.makeText(getActivity().getBaseContext(),
									"Profile mis à jour", Toast.LENGTH_SHORT)
									.show();

						}

						@Override
						public void errorResourceIdDoesNotExist() {
							Toast.makeText(getActivity().getBaseContext(),
									"Une erreur s'est produite", Toast.LENGTH_SHORT)
									.show();

						}

						@Override
						public void errorAdministratorRequired() {
							Toast.makeText(getActivity().getBaseContext(),
									"Une erreur s'est produite", Toast.LENGTH_SHORT)
									.show();

						}

						@Override
						public void errorAuthenticationRequired() {
							Toast.makeText(getActivity().getBaseContext(),
									"Une erreur s'est produite", Toast.LENGTH_SHORT)
									.show();

						}

						@Override
						public void errorNetwork() {
							Toast.makeText(getActivity().getBaseContext(),
									"Une erreur s'est produite", Toast.LENGTH_SHORT)
									.show();

						}

						@Override
						public void errorServer() {
							Toast.makeText(getActivity().getBaseContext(),
									"Une erreur s'est produite", Toast.LENGTH_SHORT)
									.show();
							
						}

					});

				}
				
			});
		} catch (NoAccountExistsInLocal e) {
			this.getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		}

	}
}