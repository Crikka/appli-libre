package adullact.publicrowdfunding.controller.profile;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.detailProject.MainActivity;
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

public class TabFinancedProjectFragment extends Fragment {

	private ListView listeProjets;

	private adullact.publicrowdfunding.controller.profile.MainActivity _this;

	private ArrayList<Project> projets;

	private ArrayAdapter<Project> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(
				R.layout.fragment_list_project_no_refresh, container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);
		
		projets = new ArrayList<Project>();

		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, projets);

		listeProjets.setAdapter(adapter);

		_this = (adullact.publicrowdfunding.controller.profile.MainActivity) getActivity();

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
		_this.user.getFundedProjects(new HoldToDo<Project>() {

			@Override
			public void hold(Project resources) {
				projets.add(resources);
				adapter.notifyDataSetChanged();
				System.out.println("taile : "+adapter.getCount());
			}



		});
	}
}