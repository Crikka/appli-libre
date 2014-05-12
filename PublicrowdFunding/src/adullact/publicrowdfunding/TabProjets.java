package adullact.publicrowdfunding;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TabProjets extends DownBarMenu {

	private ListView listeProjets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		listeProjets = (ListView) findViewById(R.id.liste);

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();

		Vector<Project> tabProj = new Vector<Project>();

		for (Entry<String, Project> entry : projets.entrySet()) {
			Project projet = entry.getValue();

			tabProj.add(projet);
		}

		ArrayAdapter<Project> adapter = new CustomAdapter(
				this.getBaseContext(), R.layout.projet_list, tabProj);

		listeProjets.setAdapter(adapter);

		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Project projet = (Project) listeProjets
						.getItemAtPosition(position);
				Intent in = new Intent(getBaseContext(),
						DetailProjetActivity.class);
				in.putExtra("key", projet.getName());
				startActivity(in);

			}
		});

		addDownBarMenu();

	}
}
