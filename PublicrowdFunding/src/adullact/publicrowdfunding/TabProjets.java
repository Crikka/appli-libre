package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import adullact.publicrowdfunding.requester.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TabProjets extends Activity {

	private ListView listeProjets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		listeProjets = (ListView) findViewById(R.id.liste);

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();

		// Création de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		for (Entry<String, Project> entry : projets.entrySet()) {
			String titre = entry.getKey();
			Project projet = entry.getValue();

			// On déclare la HashMap qui contiendra les informations pour un
			// item
			HashMap<String, String> map;
			map = new HashMap<String, String>();
			map.put("titre", titre);
			map.put("description", projet.name());
			listItem.add(map);
		}

		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),
				listItem, R.layout.projet_list, new String[] { "titre",
						"description" }, new int[] { R.id.titre_projet_liste,
						R.id.description_projet_liste });

		// On attribut à notre listView l'adapter que l'on vient de créer
		listeProjets.setAdapter(mSchedule);

		
		
		//Enfin on met un écouteur d'évènement sur notre listView
		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				HashMap<String, String> map = (HashMap<String, String>) listeProjets.getItemAtPosition(position);
				Intent in = new Intent(getBaseContext(), DetailProjetActivity.class);
				in.putExtra("key",map.get("titre"));
				startActivity(in);
				
			}
         });
		
		
	}

}
