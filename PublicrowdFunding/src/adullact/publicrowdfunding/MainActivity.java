package adullact.publicrowdfunding;

import java.util.Date;

import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
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

		/* MEA TESTEA */
		// Admin
		Requester.authentificateUser("MisterGate", "azE45WIN", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);			
			}
		});

		// Classic user
		Requester.authentificateUser("Miaou", "abjectDominera", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);			
			}
		});

		// Not a user
		Requester.authentificateUser("MiaouBis", "abjectDominera", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);				
			}
		});

		Project p = new Project("Parking sous terrain","Parking au centre de Montpellier", "50000", new Date(), new Date(114, 5, 10), new Date(114, 8, 10));
		p.finance("5000");
		System.out.println(p.percentOfAchievement());
		System.out.println(p.id());
		
		Requester.createProject("Parking sous terrain","Parking au centre de Montpellier", "50000", new Date(114, 5, 10), new Date(114, 8, 10), new CreateProjectEvent() {
			
			@Override
			public void errorAuthentificationRequired() {
				System.out.println("L'utilisateur n'est pas connecte");
			}
			
			@Override
			public void onProjectAdded(Project project) {
				System.out.println("Un projet a été ajouté !");
			}

			@Override
			public void ifUserIsAdministrator() {
				System.out.println("Un admin ! On valide ?");
			}
		});
		/* --------------- */
	} 

}
