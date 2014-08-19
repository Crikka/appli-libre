package adullact.publicrowdfunding.controlleur.profile;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.SoumettreProjetActivity;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	private TabProjetsSoumisFragment fram1;
	private TabFavorisFragment fram2;
	private TabProjetsFinanceFragment fram3;

	protected boolean myAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean myAccount = this.getIntent().getExtras()
				.getBoolean("myCount", false);

		setContentView(R.layout.activity_main_profile);

		final TextView pseudo = (TextView) findViewById(R.id.pseudo);
		TextView ville = (TextView) findViewById(R.id.ville);

		// L'utilisateur affiche son propre profile
		if (myAccount) {

			try {
				Account compte = Account.getOwn();
				pseudo.setText(compte.getUsername());

			} catch (NoAccountExistsInLocal e) {
				Toast.makeText(getApplicationContext(),
						"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
			}

		} else {
			// Affichage du profile de quelqu'un d'autre.
			 String id = this.getIntent().getExtras().getString("id");
			 
			 Cache<User> cache = new User().getCache(id);
			 cache.toResource(new WhatToDo<User>(){

				@Override
				public void hold(User resource) {
					pseudo.setText(resource.getResourceId());
					
				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub
					
				}
				 
			 });
		}

		rl = (FrameLayout) findViewById(R.id.tabcontent);

		ActionBar bar = getActionBar();

		bar.addTab(bar.newTab().setText("Projets").setTabListener(this));
		bar.addTab(bar.newTab().setText("Favoris").setTabListener(this));
		bar.addTab(bar.newTab().setText("Financé").setTabListener(this));

		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_USE_LOGO);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayShowTitleEnabled(true);
		bar.show();

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (tab.getText().equals("Projets")) {

			fram1 = new TabProjetsSoumisFragment();
			ft.replace(rl.getId(), fram1);

		} else if (tab.getText().equals("Favoris")) {

			fram2 = new TabFavorisFragment();
			ft.replace(rl.getId(), fram2);

		} else if (tab.getText().equals("Financé")) {

			fram3 = new TabProjetsFinanceFragment();
			ft.replace(rl.getId(), fram3);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

}