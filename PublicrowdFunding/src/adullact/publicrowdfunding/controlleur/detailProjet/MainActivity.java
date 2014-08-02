package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.cache.CacheManager;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
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
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements TabListener {

	private Project projet;
	private FrameLayout rl;
	private TabProjetFragment fram1;
	private TabCommentaireFragment fram2;
	private TabMapFragment fram3;

	private Drawable m_favorite;
	private boolean m_Is_favorite;

	FragmentTransaction fragMentTra = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail_projet);

		String id = getIntent().getExtras().getString("key");
		if (id == null) {
			Toast.makeText(getApplicationContext(),
					"Impossible de récupérer l'ID du projet",
					Toast.LENGTH_SHORT).show();
			finish();
		}

        Cache<Project> projet = CacheManager.getInstance().getProjectById(id);

        final MainActivity _this = this;
        projet.toResource(new HoldToDo<Project>() {
            @Override
            public void hold(Project project) {
                try {
                    if(isDeleted()) {
                        Toast.makeText(getApplicationContext(),
                                "Aucun projet avec cet ID.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        _this.projet = project;
                        rl = (FrameLayout) findViewById(R.id.tabcontent);

                        ActionBar bar = getActionBar();

                        bar.addTab(bar.newTab().setText("Projets").setTabListener(_this));
                        bar.addTab(bar.newTab().setText("Commentaires").setTabListener(_this));
                        bar.addTab(bar.newTab().setText("Localisation")
                                .setTabListener(_this));

                        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                                | ActionBar.DISPLAY_USE_LOGO);
                        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                        bar.setDisplayShowHomeEnabled(true);
                        bar.setDisplayShowTitleEnabled(true);
                        bar.show();
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (tab.getText().equals("Projets")) {

			fram1 = new TabProjetFragment();
			ft.replace(rl.getId(), fram1);

		} else if (tab.getText().equals("Commentaires")) {

			fram2 = new TabCommentaireFragment();
			ft.replace(rl.getId(), fram2);

		} else if (tab.getText().equals("Localisation")) {


			fram3 = new TabMapFragment();
			ft.replace(rl.getId(), fram3);

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
			emailIntent
					.putExtra(android.content.Intent.EXTRA_TEXT,
							"J'aime le projet venez le financer. (via PublicrowdFunding)");
			startActivity(emailIntent);
			return true;

		}
		return false;
	}

	public Project getIdProjet() {
		return projet;

	}
}
