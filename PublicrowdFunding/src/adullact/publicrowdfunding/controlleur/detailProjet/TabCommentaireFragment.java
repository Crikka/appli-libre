package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CommentaireAdapteur;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class TabCommentaireFragment extends Fragment {

	private RatingBar m_notation;
	private ListView lv;
	
	private Project projet;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet_commentaire,
				container, false);

		MainActivity activity = (MainActivity) getActivity();
		projet = activity.getIdProjet();

		
		m_notation = (RatingBar) view
				.findViewById(R.id.rating_bar_projet_detail);
		
		
		lv = (ListView) view.findViewById(R.id.commentaires);

		final CommentaireAdapteur adapter = new CommentaireAdapteur(getActivity()
				.getApplicationContext(), R.layout.listitem_discuss);

		
        projet.getCommentaries(new WhatToDo<Commentary>() {
            @Override
            public void hold(Commentary resource) {
                adapter.add(resource);
            }

            @Override
            public void eventually() {
                lv.setAdapter(adapter);
            }
        });

		
		m_notation
		.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar,
					float rating, boolean fromUser) {
				System.out.println(rating);
				ajouterCommentaireAlert commentaireBuilder = new ajouterCommentaireAlert(
						getActivity(), rating, projet);
				commentaireBuilder.show();
			}

		});
		


		return view;

	}

}
