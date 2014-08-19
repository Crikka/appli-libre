package adullact.publicrowdfunding;

import java.util.Vector;

import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TabFavorisFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab, container, false);

		final ListView listv = (ListView) view.findViewById(R.id.liste);
		
		TextView empty = (TextView) view.findViewById(R.id.empty);
		listv.setEmptyView(empty);

		/*
		adullact.publicrowdfunding.model.local.ressource.User user = 
		user.getBookmarkedProjects(new WhatToDo<Project>(){

			@Override
			public void hold(Project resource) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void eventually() {
				// TODO Auto-generated method stub
				
			}
			
		});
		 */
		
		ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
				.getBaseContext(), R.layout.projet_list, new Vector<Project>());

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