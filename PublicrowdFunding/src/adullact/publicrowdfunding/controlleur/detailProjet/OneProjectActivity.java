package adullact.publicrowdfunding.controlleur.detailProjet;


import java.util.HashMap;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OneProjectActivity extends Activity implements TabListener {

	private Project projet;
	private RelativeLayout rl;
	private String idProjet;
	private DetailProjetActivity fram1;
	private DetailProjetFinancement fram2;
	private DetailProjetMap fram3;

	private Drawable m_favorite;
	private boolean m_Is_favorite;
	
	FragmentTransaction fragMentTra = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_main);
		
		String id = getIntent().getExtras().getString("key");
		if(id == null){
			Toast.makeText(getApplicationContext(), "Impossible de récupérer l'ID du projet",
					Toast.LENGTH_SHORT).show();
			finish();
		}else{
			idProjet = id;
		}
		
		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();
		projet = projets.get(id);

		if (projet == null) {
			Toast.makeText(getApplicationContext(), "Aucun projet avec cet ID.",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		
		try {
			rl = (RelativeLayout) findViewById(R.id.mainLayout);
			fragMentTra = getFragmentManager().beginTransaction();
			ActionBar bar = getActionBar();

			bar.addTab(bar.newTab().setText("Projets").setTabListener(this));
			bar.addTab(bar.newTab().setText("Financement").setTabListener(this));
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

	}		

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (tab.getText().equals("Projets")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
			}
			fram1 = new DetailProjetActivity();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram1);
			fragMentTra.commit();
		} else if (tab.getText().equals("Financement")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
			}
			
			fram2 = new DetailProjetFinancement();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram2);
			fragMentTra.commit();
		} else if (tab.getText().equals("Localisation")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), "Cette onglet est encore instable", Toast.LENGTH_SHORT).show();
			
/*
			fram3 = new DetailProjetMap();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram3);
			fragMentTra.commit();*/
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail_projet, menu);
		m_favorite = menu.getItem(1).getIcon();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add_favorite:

			PorterDuffColorFilter filter = null;
			if (m_Is_favorite) {
				Toast.makeText(getBaseContext(), "Projet retiré des favoris",
						Toast.LENGTH_SHORT).show();
				filter = new PorterDuffColorFilter(Color.TRANSPARENT,
						PorterDuff.Mode.SRC_ATOP);
			} else {
				Toast.makeText(getBaseContext(), "Projet ajouté aux favoris",
						Toast.LENGTH_SHORT).show();

				filter = new PorterDuffColorFilter(
						Color.parseColor("#aaf0f000"), PorterDuff.Mode.SRC_ATOP);

			}
			m_Is_favorite = !m_Is_favorite;
			m_favorite.setColorFilter(filter);
			return true;
			
		case R.id.Share:
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Financement participatif");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"J'aime le projet venez le financer. (via PublicrowdFunding)");
			startActivity(emailIntent);
			return true;

		}
		return false;
	}
	

	public Project getIdProjet(){
		return projet;
		
	}
}
