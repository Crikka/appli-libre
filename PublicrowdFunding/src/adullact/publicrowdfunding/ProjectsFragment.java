package adullact.publicrowdfunding;

import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class ProjectsFragment extends Fragment {

	private ListView listeProjets;

	private SwipeRefreshLayout swipeView;

	private ArrayAdapter<Project> adapter;
	
	private adullact.publicrowdfunding.MainActivity _this;
	
	private LinearLayout loading;
	
	private Fragment fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_liste_projet,
				container, false);
		
		 fragment = new adullact.publicrowdfunding.fragment.v4.detailProject.PagerFragment();
		_this = (adullact.publicrowdfunding.MainActivity) getActivity();

		listeProjets = (ListView) view.findViewById(R.id.liste);

		loading =  (LinearLayout) view.findViewById(R.id.loading);
		
		adapter = new CustomAdapter(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, _this.p_project_displayed);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		listeProjets.setAdapter(adapter);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setEnabled(false);

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
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.fade_enter, R.anim.fade_exit);
				Bundle bundle = new Bundle();
        		bundle.putString("idProject", _this.p_project_displayed.get(position).getResourceId());
        		fragment.setArguments(bundle);
        		ft.addToBackStack(null);
				ft.replace(R.id.content_frame, fragment);
				ft.commit();

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
		_this.syncProjects();

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		/*
		boolean drawerOpen = getActivity().mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
		menu.findItem(R.id.action_sort).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
		*/

	}


}