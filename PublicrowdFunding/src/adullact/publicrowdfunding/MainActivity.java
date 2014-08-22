package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Vector;

import adullact.publicrowdfunding.controlleur.ajouterProjet.SoumettreProjetActivity;
import adullact.publicrowdfunding.controlleur.ajouterProjet.choisirMontantDialog;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.controlleur.preferences.preferences;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	private TabProjetsFragment fram1;
	private TabFavorisFragment fram2;
	private TabMapFragment fram3;

	private ImageButton m_ajouter_projet;
	private ImageButton m_mon_compte;
	private ImageButton m_sort;

	private Button m_connexion;

	private LinearLayout layoutConnect;
	private LinearLayout layoutDisconnect;
	private LinearLayout layoutLoading;

	private SearchView searchView;

	private SyncServerToLocal sync;

	protected ArrayList<Project> projetsToDisplay;

	private ActionBar bar;

	private AlertDialog dialog;

	protected SwipeRefreshLayout swipeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		layoutConnect = (LinearLayout) findViewById(R.id.connect);
		layoutDisconnect = (LinearLayout) findViewById(R.id.disconnect);
		layoutLoading = (LinearLayout) findViewById(R.id.loading);
		
		
		projetsToDisplay = new ArrayList<Project>();

		swipeView = (SwipeRefreshLayout) findViewById(R.id.refresher);
		swipeView.setEnabled(false);
		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow, R.color.red);

		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setRefreshing(true);
						sync.sync(new HoldAllToDo<Project>() {

							@Override
							public void holdAll(ArrayList<Project> projects) {
								projetsToDisplay = projects;
								reLoad();
								swipeView.setRefreshing(false);
							}});

						}
						
					
				});

		isConnect();

		sync = SyncServerToLocal.getInstance();
		final MainActivity _this = this;
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {

				projetsToDisplay = projects;

				rl = (FrameLayout) findViewById(R.id.tabcontent);

				bar = getActionBar();

				bar.addTab(bar.newTab().setText("Projets")
						.setTabListener(_this));
				bar.addTab(bar.newTab().setText("Favoris")
						.setTabListener(_this));
				bar.addTab(bar.newTab().setText("Localisation")
						.setTabListener(_this));

				bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
						| ActionBar.DISPLAY_USE_LOGO);
				bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				bar.setDisplayShowHomeEnabled(true);
				bar.setDisplayShowTitleEnabled(true);
				bar.show();
				layoutLoading.setVisibility(View.GONE);

			}
		});

		m_connexion = (Button) findViewById(R.id.connexion);
		m_connexion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						ConnexionActivity.class);
				startActivity(in);
			}
		});

		m_ajouter_projet = (ImageButton) findViewById(R.id.button_soumettre_projet);
		m_ajouter_projet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						SoumettreProjetActivity.class);
				startActivity(in);

			}
		});

		m_mon_compte = (ImageButton) findViewById(R.id.button_mon_compte);
		m_mon_compte.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(
						getBaseContext().getApplicationContext(),
						adullact.publicrowdfunding.controlleur.profile.MainActivity.class);
				in.putExtra("myCount", true);
				startActivity(in);
			}
		});
		
		m_sort = (ImageButton) findViewById(R.id.button_filtrer);
		m_sort.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String names[] = { "Les plus gros projet en premier",
						"Le plus petit projet en premier", "Le plus avancé" };

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivity.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = (View) inflater.inflate(
						R.layout.simple_listeview, null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("List");
				ListView lv = (ListView) convertView.findViewById(R.id.liste);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						names);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						switch (position) {
						case 0:
							sortBiggestProjectFirst();
							reLoad();
							dialog.dismiss();
							break;
						case 1:
							sortBiggestProjectLast();
							reLoad();
							dialog.dismiss();
							break;
						case 2:
							sortAlmostFunded();
							reLoad();
							dialog.dismiss();
							break;

						}
					}
				});

				dialog = alertDialog.create();
				dialog.show();

			}
		});

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (tab.getText().equals("Projets")) {

			fram1 = new TabProjetsFragment();
			ft.replace(rl.getId(), fram1);

		} else if (tab.getText().equals("Favoris")) {

			fram2 = new TabFavorisFragment();
			ft.replace(rl.getId(), fram2);

		} else if (tab.getText().equals("Localisation")) {

			fram3 = new TabMapFragment();
			ft.replace(rl.getId(), fram3);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	public void isConnect() {
		layoutConnect.setVisibility(View.VISIBLE);
		layoutDisconnect.setVisibility(View.GONE);
		/*
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
			layoutDisconnect.setVisibility(View.GONE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
			layoutDisconnect.setVisibility(View.VISIBLE);
		}*/
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		isConnect();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);
		MenuItem searchItem;
		searchItem = menu.findItem(R.id.SearchWords);
		assert searchItem != null;
		searchView = (SearchView) searchItem.getActionView();
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
				reLoad();
				return false;
			}

		});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {
				projetsToDisplay = sync.searchInName(query);
				reLoad();
				return false;
			}

			public boolean onQueryTextChange(String newText) {
				if (searchView.isShown()) {

				} else {

				}
				return false;
			}
		});
		
		
		return true;
	}

	public void reLoad() {
		
		ActionBar.Tab tab = bar.getSelectedTab();
		int position = tab.getPosition();
		bar.removeTab(tab);
		bar.addTab(tab, position);
		bar.selectTab(tab);
	}

	public void sortBiggestProjectFirst() {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (Integer.parseInt(lhs.getRequestedFunding()) >= Integer
						.parseInt(rhs.getRequestedFunding())) {
					return -1;
				} else {
					return 1;
				}
			}
		});

	}

	public void sortBiggestProjectLast() {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (Integer.parseInt(lhs.getRequestedFunding()) >= Integer
						.parseInt(rhs.getRequestedFunding())) {
					return 1;
				} else {
					return -1;
				}
			}
		});

	}

	public void sortAlmostFunded() {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (lhs.getPercentOfAchievement() >= rhs
						.getPercentOfAchievement()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

	}

}
