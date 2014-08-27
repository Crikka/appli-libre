package adullact.publicrowdfunding;

import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.controller.register.ConnexionActivity;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;

	private Button m_button_add_projet;
	private LinearLayout m_button_account;
	private Button m_button_sort;
	private Button m_button_validate_projects;
	private Button m_button_authentificate;
	private Button m_button_map_projects;
	private Button m_button_all_projects;

	private TextView utilisateurVille;
	private TextView utilisateurName;

	private ImageView avatar;

	private LocationManager locationManager;
	private LocationListener locationListener;
	private String locationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (LinearLayout) findViewById(R.id.left);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, /* nav drawer image */
				R.string.ask, /* "open drawer" */
				R.string.ask /* "close drawer" */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			launchDefaultFragment();
		}

		gererPanneauMenu();
		isConnect();
		geolocalisation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		/*
		 * case R.id.action_websearch: // create intent to perform web search
		 * for this planet Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		 * intent.putExtra(SearchManager.QUERY, getActionBar().getTitle()); //
		 * catch event that there's no activity to handle intent if
		 * (intent.resolveActivity(getPackageManager()) != null) {
		 * startActivity(intent); } else { Toast.makeText(this,
		 * R.string.app_not_available, Toast.LENGTH_LONG).show(); } return true;
		 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void gererPanneauMenu() {

		m_button_authentificate = (Button) findViewById(R.id.connexion);
		m_button_authentificate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						ConnexionActivity.class);
				startActivity(in);
			}
		});

		m_button_add_projet = (Button) findViewById(R.id.button_soumettre_projet);
		m_button_add_projet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						adullact.publicrowdfunding.controller.addProject.MainActivity.class);
				startActivity(in);

			}
		});

		m_button_account = (LinearLayout) findViewById(R.id.button_mon_compte);
		m_button_account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						adullact.publicrowdfunding.controller.profile.MainActivity.class);
				in.putExtra("myCount", true);
				startActivity(in);
			}
		});

		m_button_validate_projects = (Button) findViewById(R.id.button_valider_projet);
		m_button_validate_projects
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						/*
						 * Intent in = new Intent(
						 * getBaseContext().getApplicationContext(),
						 * adullact.publicrowdfunding
						 * .controller.validateProject.MainActivity.class);
						 * startActivity(in);
						 */
					}
				});

		// OK
		m_button_map_projects = (Button) findViewById(R.id.button_map_projet);
		m_button_map_projects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Fragment fragment = new TabMapFragment();

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		m_button_all_projects = (Button) findViewById(R.id.button_tout_les_projet);
		m_button_all_projects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchDefaultFragment();

			}
		});

		utilisateurVille = (TextView) findViewById(R.id.utilisateur_ville);
		utilisateurName = (TextView) findViewById(R.id.utilisateur_name);
		avatar = (ImageView) findViewById(R.id.avatar);

	}

	public void isConnect() {
		try {
			Account account = Account.getOwn();
			m_button_account.setVisibility(View.VISIBLE);
			m_button_authentificate.setVisibility(View.GONE);

			account.getUser(new WhatToDo<User>() {

				@Override
				public void hold(User resource) {
					utilisateurName.setText(resource.getPseudo());
					utilisateurVille.setText(resource.getCity());
					if (resource.getGender().equals("0")) {
						avatar.setImageResource(R.drawable.male_user_icon);
					} else {
						avatar.setImageResource(R.drawable.female_user_icon);
					}

				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub

				}

			});
		} catch (NoAccountExistsInLocal e1) {
			m_button_account.setVisibility(View.GONE);
			m_button_authentificate.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		isConnect();

		try {
			locationManager = (LocationManager) this
					.getSystemService(LOCATION_SERVICE);
			locationManager.requestLocationUpdates(locationProvider, 10000, 0,
					locationListener);
		} catch (Exception e) {
			System.out.println("Erreur 2");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			locationManager.removeUpdates(locationListener);
		} catch (Exception e) {
			System.out.println("Erreur 1");
		}
	}

	public void launchDefaultFragment() {

		Fragment fragment = new TabProjectsFragment();

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment, "allProjectFragment")
				.commit();

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void geolocalisation() {

		if (Share.position != null) {
			try{
			locationManager.removeUpdates(locationListener);
			}catch(Exception e){
				e.printStackTrace();
			}
			return;
		}

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationProvider = LocationManager.GPS_PROVIDER;
		} else {
			locationProvider = LocationManager.NETWORK_PROVIDER;
		}

		locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				Share.position = new LatLng(location.getLatitude(),
						location.getLongitude());
				try {
					Fragment myFragment = (Fragment) getFragmentManager()
							.findFragmentByTag("allProjectFragment");
					if (myFragment.isVisible()) {
						launchDefaultFragment();
					}

				} catch (NullPointerException e) {
					// 1er Initialisation
				}

			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

		};

		locationManager.requestLocationUpdates(locationProvider, 60000, 0,
				locationListener);

	}

}