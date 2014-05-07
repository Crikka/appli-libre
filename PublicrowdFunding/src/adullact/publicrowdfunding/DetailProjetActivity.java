package adullact.publicrowdfunding;

import java.util.HashMap;

import adullact.publicrowdfunding.requester.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DetailProjetActivity extends Activity {

	private RatingBar notation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_projet);

		String titre = getIntent().getExtras().getString("key");
		System.out.println(titre);

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();
		Project projet = projets.get(titre);

		notation = (RatingBar) findViewById(R.id.rating_bar_projet_detail);
		TextView txvTitre = (TextView) findViewById(R.id.titre_projet_detail);

		txvTitre.setText(projet.name());

		notation.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Toast.makeText(getApplicationContext(),
						"Notation de : " + rating, Toast.LENGTH_SHORT).show();
			}

		});
	}

}
