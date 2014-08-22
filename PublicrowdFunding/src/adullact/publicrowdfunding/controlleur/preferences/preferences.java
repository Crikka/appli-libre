package adullact.publicrowdfunding.controlleur.preferences;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class preferences extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.layout.preferences);

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
					resource.getName();
					ville.setText(resource.getCity());
					nom.setText(resource.getName());
					prenom.setText(resource.getFirstName());
					email.setText("");

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