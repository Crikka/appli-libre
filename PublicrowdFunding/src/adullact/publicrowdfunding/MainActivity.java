package adullact.publicrowdfunding;

import java.lang.reflect.Method;
import java.util.List;

import adullact.publicrowdfunding.controller.profile.preferences.preferencesFragment;
import adullact.publicrowdfunding.controller.project.all.ListProjectsFragment;
import adullact.publicrowdfunding.controller.project.all.MapFragment;
import adullact.publicrowdfunding.controller.register.ConnexionFragment;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.views.SimpleLine;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Ferrand and Nelaupe
 */
public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;

	public static ActionBarDrawerToggle mDrawerToggle;

	private Button m_button_add_projet;
	private LinearLayout m_button_account;
	private Button m_button_validate_projects;
	private Button m_button_authentificate;
	private Button m_button_map_projects;
	private Button m_button_all_projects;
	private Button m_button_change_account;
	private Button m_button_deconnexion;
	private Button m_Button_preferences;

	private TextView utilisateurVille;
	private TextView utilisateurName;

	private ImageView avatar;

	private LocationManager locationManager;
	private LocationListener locationListener;
	private String locationProvider;

	private SimpleLine m_separator_1;
	private SimpleLine m_separator_2;
	private SimpleLine m_separator_3;

	private User me;

	private Menu menu;

	private boolean isTablet;

	private android.widget.FrameLayout filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		isTablet = isTabletDevice(this.getBaseContext());

		System.out.println("Is tablet : " + isTablet);

		// isTablet = false;
		if (!isTablet) {

			managerDrawerMenu(savedInstanceState);
		}

		mDrawerList = (LinearLayout) findViewById(R.id.left);

		filter = (android.widget.FrameLayout) findViewById(R.id.big_filter);

		gererPanneauMenu();
		isConnect();
		geolocalisation();

		if (savedInstanceState == null) {
			launchDefaultFragment();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (!isTablet) {
			mDrawerToggle.syncState();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (!isTablet) {
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	}

	private void gererPanneauMenu() {
		this.invalidateOptionsMenu();

		m_separator_1 = (SimpleLine) findViewById(R.id.separator_1);
		m_separator_2 = (SimpleLine) findViewById(R.id.separator_2);
		m_separator_3 = (SimpleLine) findViewById(R.id.separator_3);

		m_button_authentificate = (Button) findViewById(R.id.connexion);
		m_button_authentificate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new ConnexionFragment();
				ft.replace(R.id.big_font, fragment);
				ft.setCustomAnimations(R.anim.popup_enter, R.anim.no_anim);
				ft.commit();

				closeDrawer();
				filter.setVisibility(View.VISIBLE);
			}
		});

		m_button_add_projet = (Button) findViewById(R.id.button_soumettre_projet);
		m_button_add_projet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new adullact.publicrowdfunding.controller.project.add.addProjectFragment();
				ft.addToBackStack(null);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				closeDrawer();
			}
		});

		m_button_account = (LinearLayout) findViewById(R.id.button_mon_compte);
		m_button_account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new adullact.publicrowdfunding.controller.profile.ProfilePagerFragment();
				Bundle bundle = new Bundle();
				bundle.putString("idUser", me.getResourceId());
				fragment.setArguments(bundle);
				fragment.setHasOptionsMenu(true);
				ft.addToBackStack(null);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				closeDrawer();
			}
		});

		m_button_validate_projects = (Button) findViewById(R.id.button_valider_projet);
		m_button_validate_projects
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						FragmentTransaction ft = getSupportFragmentManager()
								.beginTransaction();
						Fragment fragment = new adullact.publicrowdfunding.controller.project.validate.MainActivity();
						Bundle bundle = new Bundle();
						fragment.setArguments(bundle);
						ft.addToBackStack(null);
						ft.replace(R.id.content_frame, fragment);
						ft.commit();

						closeDrawer();
					}
				});

		// OK
		m_button_map_projects = (Button) findViewById(R.id.button_map_projet);
		m_button_map_projects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new MapFragment();
				ft.addToBackStack(null);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				closeDrawer();
			}
		});

		m_Button_preferences = (Button) findViewById(R.id.button_preferences);
		m_Button_preferences.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new preferencesFragment();
				ft.replace(R.id.content_frame, fragment);
				ft.addToBackStack(null);
				ft.commit();

				closeDrawer();
			}
		});

		m_button_all_projects = (Button) findViewById(R.id.button_tout_les_projet);
		m_button_all_projects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchDefaultFragment();

			}
		});

		m_button_change_account = (Button) findViewById(R.id.button_changeAccount);
		m_button_change_account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new ConnexionFragment();
				ft.replace(R.id.big_font, fragment);
				ft.setCustomAnimations(R.anim.popup_enter, R.anim.no_anim);
				ft.commit();

				closeDrawer();
				filter.setVisibility(View.VISIBLE);

			}
		});

		m_button_deconnexion = (Button) findViewById(R.id.button_deconnexion);
		m_button_deconnexion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Account.disconnect();
				isConnect();

			}
		});

		utilisateurVille = (TextView) findViewById(R.id.utilisateur_ville);
		utilisateurName = (TextView) findViewById(R.id.utilisateur_name);
		avatar = (ImageView) findViewById(R.id.avatar);
		geolocalisation();
	}

	public void setDrawerMenu(boolean connect, boolean admin) {
		if (connect) {
			m_button_account.setVisibility(View.VISIBLE);
			m_button_authentificate.setVisibility(View.GONE);
			m_button_deconnexion.setVisibility(View.VISIBLE);
			m_Button_preferences.setVisibility(View.VISIBLE);
			m_button_add_projet.setVisibility(View.VISIBLE);
			m_button_change_account.setVisibility(View.VISIBLE);

			m_separator_1.setVisibility(View.VISIBLE);
			m_separator_2.setVisibility(View.VISIBLE);
			m_separator_3.setVisibility(View.VISIBLE);
			if (admin) {
				m_button_validate_projects.setVisibility(View.VISIBLE);
			} else {
				m_button_validate_projects.setVisibility(View.GONE);
			}
		} else {
			m_button_account.setVisibility(View.GONE);
			m_button_authentificate.setVisibility(View.VISIBLE);
			m_button_deconnexion.setVisibility(View.GONE);
			m_Button_preferences.setVisibility(View.GONE);
			m_button_add_projet.setVisibility(View.GONE);
			m_button_change_account.setVisibility(View.GONE);
			m_button_validate_projects.setVisibility(View.GONE);

			m_separator_1.setVisibility(View.GONE);
			m_separator_2.setVisibility(View.GONE);
			m_separator_3.setVisibility(View.GONE);
		}
	}

	public void isConnect() {
		setDrawerMenu(false, false);
		if (Account.isConnect()) {
			try {
				Account account = Account.getOwn();
				setDrawerMenu(true, account.isAdmin());
				account.getUser(new HoldToDo<User>() {

					@Override
					public void hold(User resource) {
						me = resource;
						utilisateurName.setText(Share.formatString(resource
								.getPseudo()));
						utilisateurVille.setText(Share.formatString(resource
								.getCity()));
						if (resource.getGender().equals("0")) {
							avatar.setImageResource(R.drawable.male_user_icon);
						} else {
							avatar.setImageResource(R.drawable.female_user_icon);
						}

					}

				});
			} catch (NoAccountExistsInLocal e1) {

			}
		} else {

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

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
				.disallowAddToBackStack();
		Fragment fragment = new ListProjectsFragment();
		fragment.setHasOptionsMenu(true);
		ft.replace(R.id.content_frame, fragment, "allProjectFragment");
		ft.commit();

		closeDrawer();
	}

	public void geolocalisation() {
		Share.displayPosition = false;
		if (Share.position != null) {
			try {
				locationManager.removeUpdates(locationListener);
			} catch (Exception e) {
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

					Fragment myFragment = (Fragment) getSupportFragmentManager()
							.findFragmentByTag("allProjectFragment");
					if (myFragment.isVisible()) {
						if (Share.displayPosition == false) {
							launchDefaultFragment();
							locationManager.removeUpdates(locationListener);
							locationListener = null;
							locationManager = null;
							locationProvider = null;
							Share.displayPosition = true;
						}

					}

				} catch (NullPointerException e) {
					System.out.println("Initialisation");
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

		locationManager.requestLocationUpdates(locationProvider, 60000, 1000,
				locationListener);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (!isTablet && MainActivity.mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return false;
	}

	private boolean isTabletDevice(Context context) {

		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
		}

		return false;

	}

	public void managerDrawerMenu(Bundle savedInstanceState) {

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, /* nav drawer image */
				R.string.ask, /* "open drawer" */
				R.string.ask /* "close drawer" */
		) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
				mDrawerLayout.setClickable(true);
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	public void closeDrawer() {
		if (!isTablet) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

}