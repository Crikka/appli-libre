package adullact.publicrowdfunding.controlleur.profile;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.SoumettreProjetActivity;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends Activity implements TabListener {

	private FrameLayout rl;

	private TabProjetsSoumisFragment fram1;
	private TabFavorisFragment fram2;
	private TabProjetsFinanceFragment fram3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean myAccount = this.getIntent().getExtras().getBoolean("myCount", false);
		
		// L'utilisateur affiche son propre profile
		if(myAccount){
			
		}else{
			
		}
		
		
		setContentView(R.layout.activity_main_profile);

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Récuperation des projets...");
		progressDialog.setTitle("Initialisation de l'application");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();

		SyncServerToLocal sync = SyncServerToLocal.getInstance();
		final MainActivity _this = this;
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {
				try {
					rl = (FrameLayout) findViewById(R.id.tabcontent);

					ActionBar bar = getActionBar();

					bar.addTab(bar.newTab().setText("Projets")
							.setTabListener(_this));
					bar.addTab(bar.newTab().setText("Favoris")
							.setTabListener(_this));
					bar.addTab(bar.newTab().setText("Financé")
							.setTabListener(_this));

					bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
							| ActionBar.DISPLAY_USE_LOGO);
					bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
					bar.setDisplayShowHomeEnabled(true);
					bar.setDisplayShowTitleEnabled(true);
					bar.show();
					progressDialog.dismiss();
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

			fram1 = new TabProjetsSoumisFragment();
			ft.replace(rl.getId(), fram1);

		} else if (tab.getText().equals("Favoris")) {

			fram2 = new TabFavorisFragment();
			ft.replace(rl.getId(), fram2);

		} else if (tab.getText().equals("Financé")) {

			fram3 = new TabProjetsFinanceFragment();
			ft.replace(rl.getId(), fram3);

		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

}
