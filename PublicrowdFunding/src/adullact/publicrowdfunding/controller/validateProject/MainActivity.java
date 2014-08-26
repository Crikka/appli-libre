package adullact.publicrowdfunding.controller.validateProject;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ArrayList<Project> projetsToDisplay;

	private ListView listeProjets;

	private SwipeRefreshLayout swipeView;

	private MainActivity context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_liste_projet);

		projetsToDisplay = new ArrayList<Project>();

		context = this;

		final SyncServerToLocal sync = SyncServerToLocal.getInstance();
		sync.sync(new HoldAllToDo<Project>() {

			@Override
			public void holdAll(ArrayList<Project> projects) {

				ArrayList<Project> allSync = new ArrayList<Project>(sync
						.getProjects());

				for (Project projet : allSync) {
					if (!projet.isValidate()) {
						projetsToDisplay.add(projet);
					}
				}
			}

		});

		listeProjets = (ListView) findViewById(R.id.liste);

		TextView empty = (TextView) findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		swipeView = (SwipeRefreshLayout) findViewById(R.id.refresher);
		swipeView.setEnabled(false);
		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						swipeView.setRefreshing(true);
						SyncServerToLocal sync = SyncServerToLocal
								.getInstance();
						sync.sync(new HoldAllToDo<Project>() {

							@Override
							public void holdAll(ArrayList<Project> projects) {
								projetsToDisplay = new ArrayList<Project>();

								for (Project projet : projects) {
									if (!projet.isValidate()) {
										projetsToDisplay.add(projet);
									}
								}
								swipeView.setRefreshing(false);
								context.recreate();
							}
						});

					}

				});

		ArrayAdapter<Project> adapter = new CustomAdapter(this,
				R.layout.adaptor_project, projetsToDisplay);

		listeProjets.setAdapter(adapter);
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

	}

}
