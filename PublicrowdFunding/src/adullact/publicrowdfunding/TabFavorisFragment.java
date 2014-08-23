package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TabFavorisFragment extends Fragment {

	private ArrayList<Project> projectToDisplay;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_liste_projet, container, false);

		final ListView listv = (ListView) view.findViewById(R.id.liste);
		
		projectToDisplay = new ArrayList<Project>();
		
		TextView empty = (TextView) view.findViewById(R.id.empty);
		listv.setEmptyView(empty);
		
		try {
			Account accout = Account.getOwn();
			accout.getUser(new WhatToDo<User>(){

				@Override
				public void hold(User resource) {

					/*resource.getBookmarkedProjects(new HoldAllToDo<Project>(){

						@Override
						public void holdAll(ArrayList<Project> resources) {
							projectToDisplay = resources;
							
						}

						
					});*/
					
				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub
					
				}
				
			});
		} catch (NoAccountExistsInLocal e) {
			// TODO Auto-generated catch block
			// Pas connecté
		}

		
		ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
				.getBaseContext(), R.layout.projet_adaptor, projectToDisplay);

		listv.setAdapter(adapter);
		listv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Project projet = (Project) listv.getItemAtPosition(position);
				Intent in = new Intent(parent.getContext()
						.getApplicationContext(), MainActivity.class);
				in.putExtra("key", projet.getResourceId());
				startActivity(in);
			}
		});

		return view;
	}
}