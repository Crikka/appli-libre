package adullact.publicrowdfunding;

import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.User;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		intent.putExtra("valeur", "Liste des projets");
		tabSpec = tabHost.newTabSpec("liste projets").setIndicator("Projets")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		intent = new Intent(this, TabFavoris.class);
		intent.putExtra("valeur", "Ici les favoris");
		tabSpec = tabHost.newTabSpec("mes favoris").setIndicator("Favoris")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		
		Communicator communicator = new Communicator();
		User userAdmin = communicator.authentificateUser("MisterGate", "azE45WIN");
		User userNormal = communicator.authentificateUser("Miaou", "abjectDominera");
		
		System.out.print("Je suis "+userAdmin.pseudo()+" "+userAdmin.name()+" "+ userAdmin.firstName());
		System.out.println(" et je suis admin : " + (userAdmin instanceof Administrator));
		
		System.out.print("Je suis "+userNormal.pseudo()+" "+userNormal.name()+" "+ userNormal.firstName());
		System.out.println(" et je suis admin : " + (userNormal instanceof Administrator));


	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent in = null;
		switch (item.getItemId()) {

		case R.id.mon_compte:
			in = new Intent(getBaseContext(), ConnexionActivity.class);
			startActivity(in);
			return true;

		case R.id.add_project:
			in = new Intent(getBaseContext(), SoumettreProjetActivity.class);
			startActivity(in);
			return true;

		}
		return false;
	}

}
