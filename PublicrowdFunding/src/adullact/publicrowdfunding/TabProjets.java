package adullact.publicrowdfunding;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class TabProjets extends Fragment {

	private ListView listeProjets;
	private ImageButton m_ajouter_projet;
	private ImageButton m_mon_compte;
	private ImageButton m_rechercher;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.tab, container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();

		Vector<Project> tabProj = new Vector<Project>();

		for (Entry<String, Project> entry : projets.entrySet()) {
			Project projet = entry.getValue();

			tabProj.add(projet);
		}

		ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
				.getBaseContext(), R.layout.projet_list, tabProj);

		listeProjets.setAdapter(adapter);

		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Project projet = (Project) listeProjets
						.getItemAtPosition(position);
				Intent in = new Intent(parent.getContext()
						.getApplicationContext(), OneProjectActivity.class);
				in.putExtra("key", projet.id());
				startActivity(in);

			}
		});

		m_ajouter_projet = (ImageButton) view
				.findViewById(R.id.button_soumettre_projet);
		m_ajouter_projet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(view.getContext()
						.getApplicationContext(), SoumettreProjetActivity.class);
				startActivity(in);

			}
		});

		m_mon_compte = (ImageButton) view.findViewById(R.id.button_mon_compte);
		m_mon_compte.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(view.getContext()
						.getApplicationContext(), ConnexionActivity.class);
				startActivity(in);
			}
		});

		m_rechercher = (ImageButton) view.findViewById(R.id.button_search);
		m_rechercher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchDialog alertDialogBuilder = new SearchDialog(view.getContext());
				alertDialogBuilder.show();

			}
		});

		return view;
	}
}
