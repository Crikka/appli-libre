package adullact.publicrowdfunding.controller.profile;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	private TabSubmitedProjectFragment fram1;
	private TabBookmarkFragment fram2;
	private TabFinancedProjectFragment fram3;

	protected boolean myAccount;

	private TextView pseudo;
	private TextView ville;

	private ImageView avatar;

	protected User user;

	private boolean doRefresh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myAccount = this.getIntent().getExtras().getBoolean("myCount", false);

		setContentView(R.layout.activity_main_profile);

		doRefresh = false;

		pseudo = (TextView) findViewById(R.id.pseudo);
		ville = (TextView) findViewById(R.id.ville);
		avatar = (ImageView) findViewById(R.id.avatar);

		// L'utilisateur affiche son propre profile
		if (myAccount) {

			try {
				Account compte = Account.getOwn();
				compte.getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {
						user = resource;
						updateProfile();
					}

					@Override
					public void eventually() {
						// TODO Auto-generated method stub

					}
				});

			} catch (NoAccountExistsInLocal e) {
				Toast.makeText(getApplicationContext(),
						"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
			}

		} else {
			// Affichage du profile de quelqu'un d'autre.
			String id = this.getIntent().getExtras().getString("id");

			Cache<User> cache = new User().getCache(id);
			cache.toResource(new WhatToDo<User>() {

				@Override
				public void hold(User resource) {
					user = resource;
					updateProfile();
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

			/*
			fram1 = new TabSubmitedProjectFragment();
			ft.replace(rl.getId(), fram1);
*/
		} else if (tab.getText().equals("Favoris")) {

			fram2 = new TabBookmarkFragment();
			ft.replace(rl.getId(), fram2);

		} else if (tab.getText().equals("Financé")) {

			fram3 = new TabFinancedProjectFragment();
			ft.replace(rl.getId(), fram3);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (myAccount == true) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.profile, menu);
		}
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.preferences:
			Intent in = new Intent(
					getBaseContext().getApplicationContext(),
					adullact.publicrowdfunding.controller.preferences.MainActivity.class);
			startActivity(in);
			return true;
		}
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Un peux brutal.
		if (doRefresh) {
			this.recreate();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		doRefresh = true;

	}
	
	public void updateProfile(){
		pseudo.setText(user.getResourceId());
		ville.setText(user.getCity());
		if (user.getGender().equals("0")) {
			avatar.setImageResource(R.drawable.male_user_icon);
		} else {
			avatar.setImageResource(R.drawable.female_user_icon);
		}	
	
	}

}
