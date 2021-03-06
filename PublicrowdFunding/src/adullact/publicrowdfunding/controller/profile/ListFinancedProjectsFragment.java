package adullact.publicrowdfunding.controller.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.adaptor.ProjectAdaptor;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
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

/**
 * @author Ferrand and Nelaupe
 */
public class ListFinancedProjectsFragment extends Fragment {
	
	private ListView listeProjets;

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

		adapter = new ProjectAdaptor(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, projets, getActivity());
		listeProjets.setAdapter(adapter);

		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				//ft.setCustomAnimations(R.anim.enter_2, R.anim.exit);
				Fragment fragment = new adullact.publicrowdfunding.controller.project.details.ProjectPagerFragment();
        		Bundle bundle = new Bundle();
        		bundle.putString("idProject", projets.get(position).getResourceId());
        		fragment.setArguments(bundle);
        		fragment.setHasOptionsMenu(true);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();
			}
		});

		Bundle bundle = this.getArguments();
		String idUser = bundle.getString("idUser");
		
			Cache<User> cache = new User().getCache(idUser);
			cache.toResource(new HoldToDo<User>() {

				@Override
				public void hold(User resource) {
					resource.getFundedProjects(new HoldToDo<Project>() {

						@Override
						public void hold(Project resource) {
							projets.add(resource);
							adapter.notifyDataSetChanged();
						}

					});
				}
			});

		return view;
	}
}