package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CommentaireAdapteur;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
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

		CommentaireAdapteur adapter = new CommentaireAdapteur(getActivity()
				.getApplicationContext(), R.layout.listitem_discuss);
		// Ajout de quelques commentaires de test
		adapter.add(new Commentary(0, null, null, projet, "Trop cool",
				"Trop cool", 0));
		adapter.add(new Commentary(1, null, null, projet, "Bonne idée",
				"Bonne idée", 0));
		adapter.add(new Commentary(2, null, null, projet, "Idée de merde",
				"Idée de merde", 0));
		adapter.add(new Commentary(3, null, null, projet, "Au top", "Au top", 0));
		adapter.add(new Commentary(4, null, null, projet, "Je test le scroll",
				"Je test le scroll", 0));
		adapter.add(new Commentary(
				5,
				null,
				null,
				projet,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec molestie hendrerit lorem, vitae viverra nisl convallis at. Morbi venenatis, ipsum mattis pharetra dictum, turpis mauris rutrum ante, et laoreet nibh tellus in lorem. Donec tincidunt elit sit amet tincidunt luctus. Curabitur et lectus nec augue pretium tempus ac quis mauris.",
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec molestie hendrerit lorem, vitae viverra nisl convallis at. Morbi venenatis, ipsum mattis pharetra dictum, turpis mauris rutrum ante, et laoreet nibh tellus in lorem. Donec tincidunt elit sit amet tincidunt luctus. Curabitur et lectus nec augue pretium tempus ac quis mauris.",
				0));

		lv.setAdapter(adapter);
		
		
		m_notation
		.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar,
					float rating, boolean fromUser) {
				System.out.println(rating);
				ajouterCommentaireAlert commentaireBuilder = new ajouterCommentaireAlert(
						getActivity(), rating);
				commentaireBuilder.show();
			}

		});
		


		return view;

	}

}
