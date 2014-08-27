package adullact.publicrowdfunding.fragment.v4.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FinancedFragment extends Fragment {
	
	private ListView listeProjets;

	private ArrayList<Project> projets;

	private ArrayAdapter<Project> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		final View view = inflater.inflate(
				R.layout.fragment_list_project_no_refresh, container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);
		
		projets = new ArrayList<Project>();

		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, projets);

		listeProjets.setAdapter(adapter);
		
		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				//ft.setCustomAnimations(R.anim.enter_2, R.anim.exit);
				Fragment fragment = new adullact.publicrowdfunding.fragment.v4.detailProject.PagerFragment();
        		Bundle bundle = new Bundle();
        		bundle.putString("idProject", projets.get(position).getResourceId());
        		fragment.setArguments(bundle);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();
			}
		});
		
		
		
		Bundle bundle = this.getArguments();
		String idUser = bundle.getString("idUser");
		if (idUser.equals("me")) {
			try {
				Account.getOwn().getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {
						resource.getFundedProjects(new WhatToDo<Project>() {

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
					resource.getFundedProjects(new WhatToDo<Project>() {

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