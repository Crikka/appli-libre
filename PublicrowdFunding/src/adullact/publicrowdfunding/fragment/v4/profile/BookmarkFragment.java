package adullact.publicrowdfunding.fragment.v4.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.detailProject.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BookmarkFragment extends Fragment {

	private ListView listeProjets;

	private adullact.publicrowdfunding.controller.profile.MainActivity _this;

	private ArrayList<Project> projets;

	private ArrayAdapter<Project> adapter;

	private View view;

	private TextView empty;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_list_project_no_refresh,
				container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		projets = new ArrayList<Project>();

		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, projets);
		listeProjets.setAdapter(adapter);

		// refresh();

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

		Bundle bundle = this.getArguments();
		String idUser = bundle.getString("idUser");
		if (idUser.equals("me")) {
			try {
				Account.getOwn().getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {
						resource.getBookmarkedProjects(new WhatToDo<Project>() {

							@Override
							public void hold(Project resource) {
								projets.add(resource);
								adapter.notifyDataSetChanged();

							}

							@Override
							public void eventually() {
								// TODO Auto-generated method stub

							}

						});

					}

					@Override
					public void eventually() {
						// TODO Auto-generated method stub

					}

				});
			} catch (NoAccountExistsInLocal e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Cache<User> cache = new User().getCache(idUser);
			cache.toResource(new WhatToDo<User>() {

				@Override
				public void hold(User resource) {
					resource.getBookmarkedProjects(new WhatToDo<Project>() {

						@Override
						public void hold(Project resource) {
							projets.add(resource);
							adapter.notifyDataSetChanged();
						}

						@Override
						public void eventually() {
							// TODO Auto-generated method stub

						}

					});
				}

				@Override
				public void eventually() {
					// TODO Auto-generated method stub

				}
			});

		}

		return view;
	}
}