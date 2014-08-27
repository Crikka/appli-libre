package adullact.publicrowdfunding;

import java.util.ArrayList;

import adullact.publicrowdfunding.controller.detailProject.MainActivity;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectsFragment extends Fragment {

	private ListView listeProjets;

	private SwipeRefreshLayout swipeView;

	private ArrayList<Project> projetsToDisplay;

	private ArrayAdapter<Project> adapter;
	
	private LinearLayout m_layout_loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_liste_projet,
				container, false);

		m_layout_loading = (LinearLayout) view.findViewById(R.id.loading);
		projetsToDisplay = new ArrayList<Project>();

		listeProjets = (ListView) view.findViewById(R.id.liste);

		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, projetsToDisplay);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		listeProjets.setAdapter(adapter);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setEnabled(false);

		refresh();

		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setRefreshing(true);
						refresh();

					}

				});

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

		listeProjets.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0)
					swipeView.setEnabled(true);
				else
					swipeView.setEnabled(false);
			}
		});

		return view;
	}

	public void refresh() {
		SyncServerToLocal sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {
				projetsToDisplay = projects;

				adapter = new CustomAdapter(getActivity().getBaseContext(),
						R.layout.adaptor_project, projetsToDisplay);

				listeProjets.setAdapter(adapter);

				adapter.notifyDataSetChanged();
				swipeView.setRefreshing(false);
				m_layout_loading.setVisibility(View.GONE);
			}
		});

	}

}