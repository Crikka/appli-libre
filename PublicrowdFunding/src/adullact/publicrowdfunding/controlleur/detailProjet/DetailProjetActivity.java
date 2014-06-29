package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.ajouterProjet.choisirMontantDialog;
import adullact.publicrowdfunding.custom.CustomProgressBar;
import adullact.publicrowdfunding.shared.Project;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DetailProjetActivity extends Fragment {

	private TextView m_titre;
	private TextView m_description;
	private TextView m_nombre_participants;
	private TextView m_date_de_fin;
	private TextView m_utilisateur_soumission;
	private Button m_payer;
	private RatingBar m_notation;
	private CustomProgressBar m_progression;

	private Project projet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.detail_projet, container, false);
	
		OneProjectActivity activity = (OneProjectActivity) getActivity();
		projet = activity.getIdProjet();
		
		m_titre = (TextView) view.findViewById(R.id.titre_projet_detail);
		m_description = (TextView) view.findViewById(R.id.detail_projet_detail);
		m_payer = (Button) view.findViewById(R.id.payer);
		m_nombre_participants = (TextView) view.findViewById(R.id.nombre_participants_detail);
		m_date_de_fin = (TextView) view.findViewById(R.id.nombre_jour_restant_detail);
		m_utilisateur_soumission = (TextView) view.findViewById(R.id.utilisateur_soumission);
		m_notation = (RatingBar) view.findViewById(R.id.rating_bar_projet_detail);
		m_progression = (CustomProgressBar) view.findViewById(R.id.avancement_projet_liste);

		if (m_progression == null) {
			System.out.println("c'est nul");
		}

		System.out.println("Progression");
		m_progression.setArgent(1000);
		m_progression.setProgress(20);
		m_progression.setMaxArgent(5000);

		m_titre.setText(projet.getName());
		m_description.setText(projet.getDescription());
		
		System.out.println("Notation");
		m_notation
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						Toast.makeText(getActivity(),
								"Notation de : " + rating, Toast.LENGTH_SHORT)
								.show();
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
