package adullact.publicrowdfunding.controlleur.detailProjet;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Bookmark;
import adullact.publicrowdfunding.model.local.ressource.Funding;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements TabListener {

	protected Project projet;

	private FrameLayout rl;

	private TabProjetFragment fram1;
	private TabCommentaireFragment fram2;
	private TabMapFragment fram3;

	private Drawable m_favorite;
	private boolean m_Is_favorite;

	private ProgressDialog mprogressDialog;

	FragmentTransaction fragMentTra = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_detail_projet);
		
		
		
		String id = getIntent().getExtras().getString("key");
		if (id == null) {
			Toast.makeText(getApplicationContext(),
					"Impossible de récupérer l'ID du projet",
					Toast.LENGTH_SHORT).show();
			finish();
		}

		mprogressDialog = new ProgressDialog(this);
		mprogressDialog.setMessage("Chargement en cours...");
		mprogressDialog.setTitle("Affichage du projet");
		mprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mprogressDialog.show();

		Cache<Project> projet = new Project().getCache(id);

		final MainActivity _this = this;
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
						_this.projet = project;
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
						setBookmarked();
						mprogressDialog.dismiss();
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
		if (isConnect()) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.detail_projet, menu);
			m_favorite = menu.getItem(1).getIcon();
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add_favorite:
			System.out.println("Ajout au favoris");
			try {
				Account account = Account.getOwn();
				account.getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {
						System.out.println("récupération de l'utilisateur ok");
						System.out.println("favorite ?" + m_Is_favorite);
						if (m_Is_favorite) {
							resource.removeBookmark(projet,
									new DeleteEvent<Bookmark>() {

										@Override
										public void errorResourceIdDoesNotExist() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();

										}

										@Override
										public void onDelete(Bookmark resource) {
											Toast.makeText(
													getBaseContext(),
													"Projet retiré des favoris",
													Toast.LENGTH_SHORT).show();
											m_Is_favorite = false;
											setColorStar(m_Is_favorite);

										}

										@Override
										public void errorAdministratorRequired() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();

										}

										@Override
										public void errorAuthenticationRequired() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();

										}

										@Override
										public void errorNetwork() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();

										}

									});

							Toast.makeText(getBaseContext(),
									"Projet retiré des favoris",
									Toast.LENGTH_SHORT).show();
						} else {
							System.out.println("On l'ajoute");
							resource.addBookmark(projet,
									new CreateEvent<Bookmark>() {

										@Override
										public void errorResourceIdAlreadyUsed() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();
										}

										@Override
										public void onCreate(Bookmark resource) {
											System.out
													.println("Bookmark Ajouté !");
											Toast.makeText(
													getBaseContext(),
													"Projet ajouté aux favoris",
													Toast.LENGTH_SHORT).show();
											m_Is_favorite = true;
											setColorStar(m_Is_favorite);

										}

										@Override
										public void errorAuthenticationRequired() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();
										}

										@Override
										public void errorNetwork() {
											Toast.makeText(
													getBaseContext(),
													"Une erreur s'est produite",
													Toast.LENGTH_SHORT).show();

										}

									});

						}

					}

					@Override
					public void eventually() {
						// TODO Auto-generated method stub

					}

				});
			} catch (NoAccountExistsInLocal e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public boolean isConnect() {
		try {
			Account.getOwn();
			return true;
		} catch (NoAccountExistsInLocal e) {
			return false;
		}

	}

	public void setColorStar(boolean status) {

		PorterDuffColorFilter filter = null;
		if (!status) {
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

		/*
		 * try { Account account = Account.getOwn(); account.getUser(new
		 * WhatToDo<User>() {
		 * 
		 * @Override public void hold(User resource) {
		 * resource.getBookmarkedProjects(new HoldAllToDo<Bookmark>() {
		 * 
		 * @Override public void holdAll(ArrayList<Bookmark> resources) {
		 * 
		 * for (Bookmark bookmark : resources) { bookmark.getProject(new
		 * WhatToDo<Project>() {
		 * 
		 * @Override public void hold(Project resource) { if
		 * (resource.getResourceId() == projet .getResourceId()) { // Enfin !!
		 * oui le projet est dans // les favoris setColorStar(true); }
		 * 
		 * }
		 * 
		 * @Override public void eventually() { // TODO Auto-generated method
		 * stub
		 * 
		 * } }); } } }); }
		 * 
		 * @Override public void eventually() { // TODO Auto-generated method
		 * stub
		 * 
		 * }
		 * 
		 * }); } catch (NoAccountExistsInLocal e) {
		 * System.out.println("l'utilisateur n'est pas connecté"); }
		 */
	}
}
