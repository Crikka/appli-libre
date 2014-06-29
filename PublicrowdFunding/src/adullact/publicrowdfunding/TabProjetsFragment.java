package adullact.publicrowdfunding;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
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
import android.widget.ListView;

public class TabProjetsFragment extends Fragment {

	private ListView listeProjets;

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
						.getApplicationContext(), MainActivity.class);
				in.putExtra("key", projet.getId());
				startActivity(in);

			}
		});

		

		return view;
	}
}
