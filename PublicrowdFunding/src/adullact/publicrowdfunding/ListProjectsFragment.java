package adullact.publicrowdfunding;

import java.util.ArrayList;

import adullact.publicrowdfunding.controller.adaptor.ProjectAdaptor;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class ListProjectsFragment extends Fragment {

	private ListView listeProjets;

	private SwipeRefreshLayout swipeView;

	private ArrayAdapter<Project> adapter;

	private LinearLayout loading;
	
	private Fragment fragment;
	
	private SyncServerToLocal sync;
	
	private ArrayList<Project> p_project_displayed;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_liste_projet,
				container, false);
		
		p_project_displayed = new ArrayList<Project>();
		
		fragment = new adullact.publicrowdfunding.controller.project.details.ProjectPagerFragment();

		listeProjets = (ListView) view.findViewById(R.id.liste);

		loading =  (LinearLayout) view.findViewById(R.id.loading);
		loading.setVisibility(View.VISIBLE);
		
		adapter = new ProjectAdaptor(this.getActivity().getBaseContext(),
				R.layout.adaptor_project, p_project_displayed, getActivity());

		listeProjets.setAdapter(adapter);
		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setVisibility(View.GONE);
		swipeView.setEnabled(false);

		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setRefreshing(true);
						reload();

					}

				});
		
		listeProjets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.fade_enter, R.anim.fade_exit);
				Bundle bundle = new Bundle();
        		bundle.putString("idProject", p_project_displayed.get(position).getResourceId());
        		fragment.setArguments(bundle);
        		ft.addToBackStack(null);
        		fragment.setHasOptionsMenu(true);
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
		reload();
		return view;
		
	}

	public void refresh() {
		adapter.clear();
		adapter.addAll(p_project_displayed);
		adapter.notifyDataSetChanged();
		swipeView.setRefreshing(false);
		loading.setVisibility(View.GONE);
		swipeView.setVisibility(View.VISIBLE);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		try{
			menu.clear();
		}catch(Exception e){
			e.printStackTrace();
		}
		inflater.inflate(R.menu.menu_main, menu);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

	}
	
	public void sort() {
		ArrayAdapter<String> adapter = null;
		if (Share.position == null) {
			String names[] = { "Le plus gros projet", "Le plus petit projet",
					"Le plus avancé" };
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, names);
		} else {
			String names[] = { "Le plus gros projet", "Le plus petit",
					"Le plus avancé", "Le plus proche (Défaut)" };
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, names);
		}
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.listeview, null);
		alertDialog.setView(convertView);
		alertDialog.setTitle("Trier par");
		ListView lv = (ListView) convertView.findViewById(R.id.liste);

		lv.setAdapter(adapter);
		final AlertDialog dialog = alertDialog.create();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
                    p_project_displayed = sync.sortByRequestingProjectMaxToMin();
					refresh();
					dialog.dismiss();

					break;
				case 1:
                    p_project_displayed = sync.sortByRequestingProjectMinToMax();
                    refresh();
					dialog.dismiss();
					break;
				case 2:
                    p_project_displayed = sync.sortByAlmostFunded();
                    refresh();
					dialog.dismiss();
					break;
				case 3:
                    p_project_displayed = sync.sortByProximity();
                    refresh();
					dialog.dismiss();
					break;

				}
			}
		});

		dialog.show();

	}

	public void reload() {
		sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {
			@Override
			public void holdAll(ArrayList<Project> projects) {

				ArrayList<Project> allSync = new ArrayList<Project>(sync
						.restrictToValidatedProjects());
				p_project_displayed = allSync;

				refresh();

			}
		});
	}

	public void search(MenuItem searchItem) {
		
		SearchView searchView = (SearchView) searchItem.getActionView();
		SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getActivity().getComponentName()));
		}
		searchView.setIconifiedByDefault(true);
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				p_project_displayed = new ArrayList<Project>(sync.getProjects());
				refresh();
				return false;
			}

		});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {

				p_project_displayed = sync.searchInName(query);
				refresh();

				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}

		});

	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_search:
			search(item);
			break;
		case R.id.action_sort:
			sort();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}