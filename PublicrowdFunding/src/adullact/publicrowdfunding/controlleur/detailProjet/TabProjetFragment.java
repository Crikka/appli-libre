package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.choisirMontantDialog;
import adullact.publicrowdfunding.custom.CommentaireAdapteur;
import adullact.publicrowdfunding.custom.CustomProgressBar;
import adullact.publicrowdfunding.shared.Commentaire;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TabProjetFragment extends Fragment {

	private TextView m_titre;
	private TextView m_description;
	private TextView m_nombre_participants;
	private TextView m_date_de_fin;
	private TextView m_utilisateur_soumission;
	private Button m_payer;
	private RatingBar m_notation;
	private CustomProgressBar m_progression;
	private ListView lv;

	private Project projet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet, container,
				false);

		MainActivity activity = (MainActivity) getActivity();
		projet = activity.getIdProjet();

		lv = (ListView) view.findViewById(R.id.commentaires);

		CommentaireAdapteur adapter = new CommentaireAdapteur(getActivity()
				.getApplicationContext(), R.layout.listitem_discuss);

		// Ajout de quelques commentaires de test
		adapter.add(new Commentaire(null, null, "Trop cool", 0));
		adapter.add(new Commentaire(null, null, "Bonne idée", 0));
		adapter.add(new Commentaire(null, null, "Idée de merde", 0));
		adapter.add(new Commentaire(null, null, "Au top", 0));
		adapter.add(new Commentaire(null, null, "Je test le scroll", 0));
		adapter.add(new Commentaire(
				null,
				null,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec molestie hendrerit lorem, vitae viverra nisl convallis at. Morbi venenatis, ipsum mattis pharetra dictum, turpis mauris rutrum ante, et laoreet nibh tellus in lorem. Donec tincidunt elit sit amet tincidunt luctus. Curabitur et lectus nec augue pretium tempus ac quis mauris.",
				0));

		lv.setAdapter(adapter);

		m_titre = (TextView) view.findViewById(R.id.titre_projet_detail);
		m_description = (TextView) view.findViewById(R.id.detail_projet_detail);
		m_payer = (Button) view.findViewById(R.id.payer);
		m_nombre_participants = (TextView) view
				.findViewById(R.id.nombre_participants_detail);
		m_date_de_fin = (TextView) view
				.findViewById(R.id.nombre_jour_restant_detail);
		m_utilisateur_soumission = (TextView) view
				.findViewById(R.id.utilisateur_soumission);
		m_notation = (RatingBar) view
				.findViewById(R.id.rating_bar_projet_detail);
		m_progression = (CustomProgressBar) view
				.findViewById(R.id.avancement_projet_liste);

		if (m_progression == null) {
			System.out.println("c'est nul");
		}

		System.out.println("Progression");
		m_progression.setArgent(5000 * projet.getPercentOfAchievement() / 100);
		m_progression.setProgress(projet.getPercentOfAchievement());
		m_progression.setMaxArgent(5000);

		m_titre.setText(projet.getName());
		m_description.setText(projet.getDescription());

		System.out.println("Notation");
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

		m_payer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				choisirMontantDialog alertDialogBuilder = new choisirMontantDialog(
						getActivity());
				alertDialogBuilder.show();

			}
		});
		return view;
	}

}