package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.ExampleAndTest;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabHost = getTabHost();

		Intent intent = new Intent(this, TabProjets.class);
		tabSpec = tabHost.newTabSpec("liste projets").setIndicator("Projets")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		intent = new Intent(this, TabFavoris.class);
		tabSpec = tabHost.newTabSpec("mes favoris").setIndicator("Favoris")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		intent = new Intent(this, TabAllProjectsMaps.class);
		tabSpec = tabHost.newTabSpec("Maps des projets").setIndicator("Carte des projets")
				.setContent(intent);
		tabHost.addTab(tabSpec);
		
		/* MEA TESTA */
		adullact.publicrowdfunding.model.server.ExampleAndTest exampleAndTest = new ExampleAndTest();
		//exampleAndTest.authenticationAdmin();
		exampleAndTest.createProject();
		/* --------- */

	} 

}
