package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import adullact.publicrowdfunding.requester.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

		
		
		
		 //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 

		
		for (Entry<String, Project> entry : projets.entrySet()) {
			String titre = entry.getKey();
			Project projet = entry.getValue();
        
	        //On déclare la HashMap qui contiendra les informations pour un item
	        HashMap<String, String> map;
	        map = new HashMap<String, String>();
	        map.put("titre", titre);
	        map.put("description", projet.name());
	        listItem.add(map);
		}
        
        
 
       
        SimpleAdapter mSchedule = new SimpleAdapter (
        		this.getBaseContext(), 
        		listItem, 
        		R.layout.projet_list,
        		new String[] {"titre", "description"}, 
        		new int[] {R.id.titre_projet_liste, R.id.description_projet_liste});
 
        //On attribut à notre listView l'adapter que l'on vient de créer
        listeProjets.setAdapter(mSchedule);
		
		
	}

}
