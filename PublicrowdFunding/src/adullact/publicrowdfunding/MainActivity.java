package adullact.publicrowdfunding;

import adullact.publicrowdfunding.exceptions.UserNotFoundException;
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
		
		try {
			Requester.authentificateUser("MisterGate", "azE45WIN");
		} catch (UserNotFoundException exception) {
			System.out.println("Impossible de trouver " + exception.pseudo() + " avec le mot de passe : " + exception.password());
		}
		System.out.print("Je suis "+ Share.user.pseudo()+" "+ Share.user.name()+" "+ Share.user.firstName());
		System.out.println(" et je suis admin : " + (Share.user instanceof Administrator));
		
		try {
			Requester.authentificateUser("Miaou", "abjectDominera");
		} catch (UserNotFoundException exception) {
			System.out.println("Impossible de trouver " + exception.pseudo() + " avec le mot de passe : " + exception.password());
		}
		System.out.print("Je suis "+ Share.user.pseudo()+" "+ Share.user.name()+" "+ Share.user.firstName());
		System.out.println(" et je suis admin : " + (Share.user instanceof Administrator));
		
		try {
			Requester.authentificateUser("MiaouBis", "abjectDominera");
		} catch (UserNotFoundException exception) {
			System.out.println("Impossible de trouver " + exception.pseudo() + " avec le mot de passe : " + exception.password());
		}

		Project p = new Project("Parking sous terrain","Parking au centre de Montpellier", "50000");
		p.finance("5000");
		System.out.println(p.percentOfAchievement());
		System.out.println(p.id());
	} 

}
