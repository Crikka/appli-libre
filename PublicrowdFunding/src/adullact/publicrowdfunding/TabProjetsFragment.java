package adullact.publicrowdfunding;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.custom.CustomAdapter;
import adullact.publicrowdfunding.model.local.SyncServerToLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
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

public class TabProjetsFragment extends Fragment {

    private ListView listeProjets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab, container, false);

        listeProjets = (ListView) view.findViewById(R.id.liste);

        MainActivity activity = (MainActivity) getActivity();

        ArrayAdapter<Project> adapter = new CustomAdapter(this.getActivity()
                .getBaseContext(), R.layout.projet_list, activity.getProjets());

        listeProjets.setAdapter(adapter);
        listeProjets.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Project projet = (Project) listeProjets
                        .getItemAtPosition(position);
                Intent in = new Intent(parent.getContext()
                        .getApplicationContext(), MainActivity.class);
                in.putExtra("key", projet.getId());
                startActivity(in);
            }
        });

        return view;
    }
}