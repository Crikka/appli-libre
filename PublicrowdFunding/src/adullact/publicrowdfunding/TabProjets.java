package adullact.publicrowdfunding;

import java.util.HashMap;

import adullact.publicrowdfunding.requester.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class TabProjets extends Activity {

	private ListView listeProjets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		// On r�cup�re notre intent et la valeur nomm�e valeur
		String valeur = getIntent().getStringExtra("valeur");

		listeProjets = (ListView) findViewById(R.id.liste);
		// Récupération des projets ??

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();

		
	}
}