package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.fragment.register.connexionFragment;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

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

	protected ArrayList<Project> projetsToDisplay;

	private SyncServerToLocal sync;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		projetsToDisplay = new ArrayList<Project>();

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
		// geolocalisation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		search(menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_sort).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
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

		m_button_authentificate = (Button) findViewById(R.id.connexion);
		m_button_authentificate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new connexionFragment();
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

				
				
				

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.enter, R.anim.exit);
				Fragment fragment = new TabMapFragment();

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

		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.enter, R.anim.exit);
		Fragment fragment = new ProjectsFragment();

		ft.replace(R.id.content_frame, fragment, "allProjectFragment");
		ft.commit();

		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void geolocalisation() {

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

	public void sort() {
		String names[] = { "Les plus gros projets en premier",
				"Le plus petit projets en premier", "Le plus avancés" };

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MainActivity.this);
		LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.listeview, null);
		alertDialog.setView(convertView);
		alertDialog.setTitle("Trier par");
		ListView lv = (ListView) convertView.findViewById(R.id.liste);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1, names);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					/*
					 * sortBiggestProjectFirst(); reLoad(); dialog.dismiss();
					 */
					break;
				case 1:
					/*
					 * sortBiggestProjectLast(); reLoad(); dialog.dismiss();
					 */
					break;
				case 2:
					/*
					 * sortAlmostFunded(); reLoad(); dialog.dismiss();
					 */
					break;

				}
			}
		});

		AlertDialog dialog = alertDialog.create();
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
				projetsToDisplay = new ArrayList<Project>(sync.getProjects());
				// reLoad();
				return false;
			}

		});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {
				/*
				 * projetsToDisplay = sync.searchInName(query); reLoad();
				 */
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
				projetsToDisplay = allSync;

			}
		});
	}

}