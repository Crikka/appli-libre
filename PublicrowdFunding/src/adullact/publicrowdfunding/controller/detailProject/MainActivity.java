package adullact.publicrowdfunding.controller.detailProject;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.fragment.v4.detailProject.TabCommentsFragment;
import adullact.publicrowdfunding.fragment.v4.detailProject.TabMapFragment;
import adullact.publicrowdfunding.fragment.v4.detailProject.TabProjectFragment;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Bookmark;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.CanI;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;
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

	protected Project projetCurrent;

	private FrameLayout rl;

	private TabProjectFragment fram1;
	private TabCommentsFragment fram2;
	private TabMapFragment fram3;

	private Drawable m_favorite;
	private boolean m_Is_favorite;

	FragmentTransaction fragMentTra = null;

	private MainActivity _this;

	private boolean doRefresh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detail_project);

		doRefresh = false;

		_this = this;

		String id = getIntent().getExtras().getString("key");
		if (id == null) {
			Toast.makeText(getApplicationContext(),
					"Impossible de récupérer l'ID du projet",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		
		Cache<Project> projet = new Project().getCache(id);

		projet.toResource(new HoldToDo<Project>() {
			@Override
			public void hold(Project project) {
				try {
					if (isDeleted()) {
						Toast.makeText(getApplicationContext(),
								"Aucun projet avec cet ID.", Toast.LENGTH_SHORT)
								.show();
						finish();
					} else {
						projetCurrent = project;
						rl = (FrameLayout) findViewById(R.id.tabcontent);

						ActionBar bar = getActionBar();

						bar.addTab(bar.newTab().setText("Projets")
								.setTabListener(_this));
						bar.addTab(bar.newTab().setText("Commentaires")
								.setTabListener(_this));
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

			fram1 = new TabProjectFragment();
			//ft.replace(rl.getId(), fram1);

		} else if (tab.getText().equals("Commentaires")) {

			/*
			fram2 = new TabCommentsFragment();
			ft.replace(rl.getId(), fram2);
*/
		} else if (tab.getText().equals("Localisation")) {
/*
			fram3 = new TabMapFragment();
			ft.replace(rl.getId(), fram3);
*/
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	

	public boolean isConnect() {
		try {
			Account.getOwn();
			return true;
		} catch (NoAccountExistsInLocal e) {
			return false;
		}

	}

	public void changeColorStar() {

		PorterDuffColorFilter filter = null;
		if (!m_Is_favorite) {
			// Color initial
			filter = new PorterDuffColorFilter(Color.TRANSPARENT,
					PorterDuff.Mode.SRC_ATOP);
		} else {
			// Couleur jaune

			filter = new PorterDuffColorFilter(Color.parseColor("#aaf0f000"),
					PorterDuff.Mode.SRC_ATOP);

		}
		m_favorite.setColorFilter(filter);
	}

	public void setBookmarked() {

		try{
		new CanI() {
			@Override
			protected void yes() {
				m_Is_favorite = false;
				changeColorStar();
			}

			@Override
			protected void no() {
				m_Is_favorite = true;
				changeColorStar();
			}

		}.bookmark(projetCurrent);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Project getCurrentProject() {
		return projetCurrent;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Un peux brutal.
		if (doRefresh) {
			this.recreate();
		}


	}

	@Override
	public void onPause() {
		super.onPause();
		//doRefresh = true;
		finish();
	}

	public void refresh() {
		Cache<Project> projet = new Project().getCache(projetCurrent
				.getResourceId());

		projet.toResource(new HoldToDo<Project>() {
			@Override
			public void hold(Project project) {
				projetCurrent = project;

			}
		});
	}

}
