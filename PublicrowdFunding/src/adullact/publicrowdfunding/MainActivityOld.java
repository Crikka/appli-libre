package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adullact.publicrowdfunding.controller.register.ConnexionActivity;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

import com.google.android.gms.maps.model.LatLng;

public class MainActivityOld extends Activity implements TabListener {

	private FrameLayout rl;

	private TabProjectsFragment m_tab_projects_fragment;
	private TabMapFragment m_tab_map_fragment;

	private ImageButton m_button_sort;

	private LinearLayout m_layout_loading;

	private SearchView searchView;

	private SyncServerToLocal sync;

	protected ArrayList<Project> projetsToDisplay;

	private ActionBar m_actionbar;

	private AlertDialog dialog;

	
	public MainActivityOld _this;
	
	private ViewGroup parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		parent = (ViewGroup) findViewById(R.id.layout_general);

		
	
		m_layout_loading = (LinearLayout) findViewById(R.id.loading);

		projetsToDisplay = new ArrayList<Project>();


		sync = SyncServerToLocal.getInstance();
		final MainActivityOld _this = this;
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {

				ArrayList<Project> allSync = new ArrayList<Project>(sync
						.getProjects());
				projetsToDisplay = allSync;

				for (Project projet : allSync) {
					System.out.println("Projet : " + projet.getName()
							+ " validate ?" + projet.isValidate());

				}

				rl = (FrameLayout) findViewById(R.id.tabcontent);

				m_actionbar = getActionBar();

				m_actionbar.addTab(m_actionbar.newTab().setText("Projets")
						.setTabListener(_this));
				m_actionbar.addTab(m_actionbar.newTab().setText("Localisation")
						.setTabListener(_this));

				m_actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
						| ActionBar.DISPLAY_USE_LOGO);
				m_actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				m_actionbar.setDisplayShowHomeEnabled(true);
				m_actionbar.setDisplayShowTitleEnabled(true);
				m_actionbar.show();
				m_layout_loading.setVisibility(View.GONE);

			}
		});

		

		m_button_sort = (ImageButton) findViewById(R.id.button_filtrer);
		m_button_sort.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String names[] = { "Les plus gros projet en premier",
						"Le plus petit projet en premier", "Le plus avancé" };

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivityOld.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = (View) inflater.inflate(R.layout.listeview,
						null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("List");
				ListView lv = (ListView) convertView.findViewById(R.id.liste);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivityOld.this, android.R.layout.simple_list_item_1,
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

			m_tab_projects_fragment = new TabProjectsFragment();
			ft.replace(rl.getId(), m_tab_projects_fragment);

		} else if (tab.getText().equals("Localisation")) {

			m_tab_map_fragment = new TabMapFragment();
			ft.replace(rl.getId(), m_tab_map_fragment);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

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

	protected void reLoad() {
		ActionBar.Tab tab = m_actionbar.getSelectedTab();
		int position = tab.getPosition();
		if (position == 0) {

			m_tab_projects_fragment = new TabProjectsFragment();

			FragmentManager fm = this.getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(rl.getId(), m_tab_projects_fragment);
			ft.commit();
		}

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
