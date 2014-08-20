package adullact.publicrowdfunding;

import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.controlleur.detailProjet.MainActivity;
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

import java.util.Vector;

public class TabProjetsFragment extends Fragment {

    private ListView listeProjets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_ab, container, false);

        listeProjets = (ListView) view.findViewById(R.id.liste);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		listeProjets.setEmptyView(empty);
        
        ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
                .getBaseContext(), R.layout.projet_list, new Vector<Project>(SyncServerToLocal.getInstance().getProjects()));

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

        return view;
    }
}