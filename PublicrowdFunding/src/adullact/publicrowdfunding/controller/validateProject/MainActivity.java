package adullact.publicrowdfunding.controller.validateProject;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.ProjectAdaptor;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Fragment {

	private ListView listeProjets;

	private SwipeRefreshLayout swipeView;

	private ArrayAdapter<Project> adapter;

	// private adullact.publicrowdfunding.MainActivity _this;

	private LinearLayout loading;

	private ArrayList<Project> p_project_displayed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_liste_projet,
				container, false);

		p_project_displayed = new ArrayList<Project>();

		// _this = (adullact.publicrowdfunding.MainActivity) getActivity();

		listeProjets = (ListView) view.findViewById(R.id.liste);

		loading = (LinearLayout) view.findViewById(R.id.loading);

	

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		refresh();
	
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setEnabled(false);
		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setEnabled(true);
						refresh();

					}

				});

		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				/*
				 * FragmentTransaction ft =
				 * getFragmentManager().beginTransaction();
				 * ft.setCustomAnimations(R.anim.fade_enter, R.anim.fade_exit);
				 * Bundle bundle = new Bundle(); bundle.putString("idProject",
				 * _this.p_project_displayed.get(position).getResourceId());
				 * fragment.setArguments(bundle); ft.addToBackStack(null);
				 * fragment.setHasOptionsMenu(true);
				 * ft.replace(R.id.content_frame, fragment); ft.commit();
				 */
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
		loading.setVisibility(View.GONE);
		return view;

	}

	public void refresh() {

	final SyncServerToLocal sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {
				adapter = new ValidateProjectAdaptor(getActivity().getBaseContext(),
						R.layout.adaptor_project, sync.restrictToNotValidatedProjects());
			

				adapter.addAll(projects);
				adapter.notifyDataSetChanged();
				swipeView.setRefreshing(false);
				
				listeProjets.setAdapter(adapter);

			}
		});
		
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		try {
			menu.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inflater.inflate(R.menu.menu_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

	}

}