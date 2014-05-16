package adullact.publicrowdfunding;

import java.util.HashMap;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailProjetActivity extends FragmentActivity {

	private TextView m_titre;
	private TextView m_description;
//	private TextView m_nombre_participants;
//	private TextView m_date_de_fin;
//	private TextView m_utilisateur_soumission;
	private RatingBar m_notation;
	private CustomProgressBar m_progression;
	private Drawable m_favorite;
	private boolean m_Is_favorite;
	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_projet);

		String titre = getIntent().getExtras().getString("key");
		System.out.println(titre);

		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();
		Project projet = projets.get(titre);
		System.out.println("titre: titre");
		if(projet == null){
			Toast.makeText(getApplicationContext(), "Un erreur s'est produite", Toast.LENGTH_SHORT).show();
			finish();
		}

		m_titre = (TextView) findViewById(R.id.titre_projet_detail);
		m_description = (TextView) findViewById(R.id.detail_projet_detail);
		//m_nombre_participants = (TextView) findViewById(R.id.nombre_participants_detail);
		//m_date_de_fin = (TextView) findViewById(R.id.nombre_jour_restant_detail);
		//m_utilisateur_soumission = (TextView) findViewById(R.id.utilisateur_soumission);
		m_notation = (RatingBar) findViewById(R.id.rating_bar_projet_detail);
		m_progression = (CustomProgressBar) findViewById(R.id.avancement_projet_liste);
		//MenuItem favorisItem = (MenuItem) findViewById(R.id.add_favorite);
		GraphiqueView graph = (GraphiqueView) findViewById(R.id.graphique);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		android.view.ViewGroup.LayoutParams params = graph.getLayoutParams();
		params.height = metrics.widthPixels;
		graph.setLayoutParams(params);

		m_Is_favorite = false;
		System.out.println("lancement de google map");
		try {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();
			LatLng position = new LatLng(43, 3);
			map.addMarker(new MarkerOptions().position(position).title(
					"Titre du projet"));

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 4));
		} catch (NullPointerException e) {
			Toast.makeText(getApplication(), "Impossible de lancer google Map",
					Toast.LENGTH_SHORT).show();

		}

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
						Toast.makeText(getApplicationContext(),
								"Notation de : " + rating, Toast.LENGTH_SHORT)
								.show();
					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail_projet, menu);
		m_favorite = menu.getItem(0).getIcon();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add_favorite:

			PorterDuffColorFilter filter = null;
			if (m_Is_favorite) {
				Toast.makeText(getBaseContext(), "Projet retiré des favoris",
						Toast.LENGTH_SHORT).show();
				filter = new PorterDuffColorFilter(Color.TRANSPARENT,
						PorterDuff.Mode.SRC_ATOP);
			} else {
				Toast.makeText(getBaseContext(), "Projet ajouté aux favoris",
						Toast.LENGTH_SHORT).show();

				filter = new PorterDuffColorFilter(
						Color.parseColor("#ffffff00"), PorterDuff.Mode.SRC_ATOP);

			}
			m_Is_favorite = !m_Is_favorite;
			m_favorite.setColorFilter(filter);
			return true;

		}
		return false;
	}

}
