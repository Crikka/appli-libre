package adullact.publicrowdfunding;

import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

public class TabProjetsFragment extends Fragment {

	private ListView listeProjets;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_liste_projet,
				container, false);

		listeProjets = (ListView) view.findViewById(R.id.liste);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);

		final adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity) getActivity();

		ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
				.getBaseContext(), R.layout.projet_adaptor, _this.projetsToDisplay);

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
	        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	                if (firstVisibleItem == 0)
	                	_this.swipeView.setEnabled(true);
	                else
	                	_this.swipeView.setEnabled(false);
	        }
	    });
	

		return view;
	}
	
}