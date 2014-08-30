package adullact.publicrowdfunding;

import java.util.ArrayList;

import adullact.publicrowdfunding.controller.preferences.preferencesFragment;
import adullact.publicrowdfunding.fragment.v4.register.ConnexionFragment;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.utilities.sortProjects;
import adullact.publicrowdfunding.views.SimpleLine;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;

	public ActionBarDrawerToggle mDrawerToggle;

	private Button m_button_add_projet;
	private LinearLayout m_button_account;
	private Button m_button_validate_projects;
	private Button m_button_authentificate;
	private Button m_button_map_projects;
	private Button m_button_all_projects;
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

	protected ArrayList<Project> p_project_displayed;

	private SyncServerToLocal sync;

	private User me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		p_project_displayed = new ArrayList<Project>();

		syncProjects();

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
				mDrawerLayout.setClickable(true);
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
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {

		case R.id.action_sort:
			sort();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void gererPanneauMenu() {
		final MainActivity _this = this;
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
				// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new ConnexionFragment();
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				mDrawerLayout.closeDrawer(mDrawerList);
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

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new adullact.publicrowdfunding.fragment.v4.profile.ProfilePagerFragment();
				Bundle bundle = new Bundle();
				bundle.putString("idUser", me.getResourceId());
				fragment.setArguments(bundle);
				fragment.setHasOptionsMenu(true);
				ft.addToBackStack(null);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				mDrawerLayout.closeDrawer(mDrawerList);

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

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new MapFragment();

				ft.replace(R.id.content_frame, fragment);
				ft.commit();

				mDrawerLayout.closeDrawer(mDrawerList);
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
				ft.commit();

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

	}

	public void setDrawerMenu(boolean connect) {
		if (connect) {
			m_button_account.setVisibility(View.VISIBLE);
			m_button_authentificate.setVisibility(View.GONE);
			m_button_deconnexion.setVisibility(View.VISIBLE);
			m_Button_preferences.setVisibility(View.VISIBLE);
			m_button_add_projet.setVisibility(View.VISIBLE);
			m_button_validate_projects.setVisibility(View.VISIBLE);

			m_separator_1.setVisibility(View.VISIBLE);
			m_separator_2.setVisibility(View.VISIBLE);
			m_separator_3.setVisibility(View.VISIBLE);
		} else {
			m_button_account.setVisibility(View.GONE);
			m_button_authentificate.setVisibility(View.VISIBLE);
			m_button_deconnexion.setVisibility(View.GONE);
			m_Button_preferences.setVisibility(View.GONE);
			m_button_add_projet.setVisibility(View.GONE);
			m_button_validate_projects.setVisibility(View.GONE);

			m_separator_1.setVisibility(View.GONE);
			m_separator_2.setVisibility(View.GONE);
			m_separator_3.setVisibility(View.GONE);
		}
	}

	public void isConnect() {
		try {
			Account account = Account.getOwn();

			setDrawerMenu(true);
			account.getUser(new WhatToDo<User>() {

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

				@Override
				public void eventually() {

				}

			});
		} catch (NoAccountExistsInLocal e1) {
			setDrawerMenu(false);
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

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new ListProjectsFragment();
		fragment.setHasOptionsMenu(true);
		ft.replace(R.id.content_frame, fragment, "allProjectFragment");
		ft.commit();

		mDrawerLayout.closeDrawer(mDrawerList);
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
							sortProjects.sortAlmostFunded(p_project_displayed);
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

	public void sort() {
		ArrayAdapter<String> adapter = null;
		if (Share.position == null) {
			String names[] = { "Le plus gros projet", "Le plus petit projet",
					"Le plus avancé" };
			adapter = new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_list_item_1, names);
		} else {
			String names[] = { "Le plus gros projet", "Le plus petit",
					"Le plus avancé", "Le plus proche (Défaut)" };
			adapter = new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_list_item_1, names);
		}
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.listeview, null);
		alertDialog.setView(convertView);
		alertDialog.setTitle("Trier par");
		ListView lv = (ListView) convertView.findViewById(R.id.liste);

		lv.setAdapter(adapter);
		final AlertDialog dialog = alertDialog.create();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:

					sortProjects.sortBiggestProjectFirst(p_project_displayed);
					launchDefaultFragment();
					dialog.dismiss();

					break;
				case 1:
					sortProjects.sortBiggestProjectLast(p_project_displayed);
					launchDefaultFragment();
					dialog.dismiss();
					break;
				case 2:

					sortProjects.sortAlmostFunded(p_project_displayed);
					launchDefaultFragment();
					dialog.dismiss();
					break;

				case 3:
					sortProjects.sortClothersProject(p_project_displayed);
					launchDefaultFragment();
					dialog.dismiss();
					break;

				}
			}
		});

		dialog.show();

	}

	public void search(Menu menu) {
		MenuItem searchItem;
		searchItem = menu.findItem(R.id.action_search);
		assert searchItem != null;
		SearchView searchView = (SearchView) searchItem.getActionView();
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
		}
		searchView.setIconifiedByDefault(true);
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				p_project_displayed = new ArrayList<Project>(sync.getProjects());
				launchDefaultFragment();
				return false;
			}

		});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {

				p_project_displayed = sync.searchInName(query);
				launchDefaultFragment();

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}

		});

	}

	public void syncProjects() {

		sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {

				ArrayList<Project> allSync = new ArrayList<Project>(sync
						.getProjects());
				p_project_displayed = allSync;

				launchDefaultFragment();

			}
		});
	}

}