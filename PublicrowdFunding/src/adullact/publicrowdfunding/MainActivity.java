package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.controlleur.ajouterProjet.SoumettreProjetActivity;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	private TabProjetsFragment fram1;
	private TabFavorisFragment fram2;
	private TabMapFragment fram3;
	private ImageButton m_ajouter_projet;
	private ImageButton m_mon_compte;
	private ImageButton m_rechercher;
	private Button m_connexion;
	private LinearLayout layoutConnect;
	private LinearLayout layoutDisconnect;
	private SearchView searchView;
	private SyncServerToLocal sync;
	protected Vector<Project> projetsToDisplay;
	private ActionBar bar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		layoutConnect = (LinearLayout) findViewById(R.id.connect);
		layoutDisconnect = (LinearLayout) findViewById(R.id.disconnect);

		projetsToDisplay = new Vector<Project>();

		isConnect();

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Récuperation des projets...");
		progressDialog.setTitle("Initialisation de l'application");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();

		sync = SyncServerToLocal.getInstance();
		final MainActivity _this = this;
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {
				projetsToDisplay = new Vector<Project>(projects);
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
				progressDialog.dismiss();

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

		m_rechercher = (ImageButton) findViewById(R.id.button_search);
		m_rechercher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchDialog alertDialogBuilder = new SearchDialog(
						getBaseContext());
				alertDialogBuilder.show();

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
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
			layoutDisconnect.setVisibility(View.GONE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
			layoutDisconnect.setVisibility(View.VISIBLE);
		}
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
		searchView.setOnCloseListener(new OnCloseListener(){

			@Override
			public boolean onClose() {
				projetsToDisplay  = new Vector<Project>(sync.getProjects());
				reLoad();
				return false;
			}
			
		});
		
		
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {
				projetsToDisplay = new Vector<Project>(sync.searchInName(query));
				reLoad();
				return false;
			}

			public boolean onQueryTextChange(String newText) {
				if (searchView.isShown()) {
					
				}else{
				
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
		bar.addTab(tab,position);
		bar.selectTab(tab);
	}

}
