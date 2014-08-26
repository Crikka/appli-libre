package adullact.publicrowdfunding.controlleur.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
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
import android.widget.TextView;

public class TabFavorisFragment extends Fragment {

	private ListView listeProjets;

	private adullact.publicrowdfunding.controlleur.profile.MainActivity _this;

	private ArrayList<Project> projets;

	private ArrayAdapter<Project> adapter;
	
	private View view;
	
	private TextView empty;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_liste_projet_no_refresh,
				container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		projets = new ArrayList<Project>();

		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.projet_adaptor, projets);
		listeProjets.setAdapter(adapter);

		_this = (adullact.publicrowdfunding.controlleur.profile.MainActivity) getActivity();
		
		refresh();
		
		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Project projet = (Project) listeProjets
						.getItemAtPosition(position);
				Intent in = new Intent(parent.getContext()
						.getApplicationContext(), MainActivity.class);
				in.putExtra("key", projet.getResourceId());
				startActivity(in);
			}
		});

		return view;
	}
	
	public void refresh(){
		_this.user.getBookmarkedProjects(new HoldToDo<Project>() {

			@Override
			public void hold(Project resources) {
				
				adapter.add(resources);
				adapter.notifyDataSetChanged();
				

			}

		});
	
	}

}