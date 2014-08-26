package adullact.publicrowdfunding.controller.preferences;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
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
			account.getUser(new WhatToDo<User>() {

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

				@Override
				public void eventually() {
					// TODO Auto-generated method stub

				}

			});
		} catch (NoAccountExistsInLocal e) {
			getActivity().finish();
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

		try {
			Account account = Account.getOwn();
			account.getUser(new WhatToDo<User>() {

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
							// TODO Auto-generated method stub

						}

						@Override
						public void errorAdministratorRequired() {
							// TODO Auto-generated method stub

						}

						@Override
						public void errorAuthenticationRequired() {
							// TODO Auto-generated method stub

						}

						@Override
						public void errorNetwork() {
							// TODO Auto-generated method stub

						}

					});

				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub

				}

			});
		} catch (NoAccountExistsInLocal e) {
			getActivity().finish();
		}

	}
}