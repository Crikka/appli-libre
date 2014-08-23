package adullact.publicrowdfunding.controlleur.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Bookmark;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TabFavorisFragment extends Fragment {

	private ListView listeProjets;

	private adullact.publicrowdfunding.controlleur.profile.MainActivity _this;

	private ArrayList<Bookmark> bookmarks;
	
	private ArrayList<Project> projets;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_liste_projet, container,
				false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		projets = new ArrayList<Project>();
		bookmarks = new ArrayList<Bookmark>();

		_this = (adullact.publicrowdfunding.controlleur.profile.MainActivity) getActivity();

		_this.user.getBookmarkedProjects(new HoldAllToDo<Bookmark>() {

			@Override
			public void holdAll(ArrayList<Bookmark> resources) {
				bookmarks = resources;

			}

		});
		
		for(Bookmark bookmark : bookmarks){
			bookmark.getProject(new WhatToDo<Project>(){

				@Override
				public void hold(Project resource) {
					projets.add(resource);
					
				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub
					
				}
				
			});
			
		}

		ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
				.getBaseContext(), R.layout.projet_adaptor, projets);

		listeProjets.setAdapter(adapter);
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

}