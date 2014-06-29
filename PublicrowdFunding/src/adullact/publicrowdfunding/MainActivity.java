package adullact.publicrowdfunding;

import adullact.publicrowdfunding.controlleur.ajouterProjet.SoumettreProjetActivity;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	TabProjetsFragment fram1;
	TabFavorisFragment fram2;
	TabMapFragment fram3;
	private ImageButton m_ajouter_projet;
	private ImageButton m_mon_compte;
	private ImageButton m_rechercher;
	FragmentTransaction fragMentTra = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		m_ajouter_projet = (ImageButton) findViewById(R.id.button_soumettre_projet);
		m_ajouter_projet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(getBaseContext()
						.getApplicationContext(), SoumettreProjetActivity.class);
				startActivity(in);

			}
		});

		m_mon_compte = (ImageButton) findViewById(R.id.button_mon_compte);
		m_mon_compte.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getBaseContext()
						.getApplicationContext(), ConnexionActivity.class);
				startActivity(in);
			}
		});

		m_rechercher = (ImageButton) findViewById(R.id.button_search);
		m_rechercher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchDialog alertDialogBuilder = new SearchDialog(getBaseContext());
				alertDialogBuilder.show();

			}
		});

		
		try {
			rl = (FrameLayout) findViewById(R.id.tabcontent);
			
			ActionBar bar = getActionBar();

			bar.addTab(bar.newTab().setText("Projets").setTabListener(this));
			bar.addTab(bar.newTab().setText("Favoris").setTabListener(this));
			bar.addTab(bar.newTab().setText("Localisation")
					.setTabListener(this));

			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_USE_LOGO);
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayShowHomeEnabled(true);
			bar.setDisplayShowTitleEnabled(true);
			bar.show();

		} catch (Exception e) {
			e.getMessage();
		}

		/*ExampleAndTest ex = new ExampleAndTest();
		ex.listAllUsers();*/
	}		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_action_bar_main, menu);
		return true;
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

}
