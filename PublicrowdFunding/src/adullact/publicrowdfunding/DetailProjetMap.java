package adullact.publicrowdfunding;

import java.util.HashMap;

import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Project;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailProjetMap extends FragmentActivity {

	private Drawable m_favorite;
	private boolean m_Is_favorite;
	private GoogleMap map;
	private Project projet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		String id = getIntent().getExtras().getString("key");
		if(id == null){
			Toast.makeText(getApplicationContext(), "Un erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		ServerEmulator serveur = ServerEmulator.instance();
		HashMap<String, Project> projets = serveur.getAllProjets();
		projet = projets.get(id);

		if (projet == null) {
			Toast.makeText(getApplicationContext(), "Un erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		
		System.out.println("lancement de google map");
		try {
			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();
			map.addMarker(new MarkerOptions().position(projet.getPosition()).title(
					projet.getName()));

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(projet.getPosition(), 4));
		} catch (NullPointerException e) {
			Toast.makeText(getApplication(), "Impossible de lancer google Map",
					Toast.LENGTH_SHORT).show();
		}

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail_projet, menu);
		m_favorite = menu.getItem(1).getIcon();
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
			
		case R.id.Share:
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Financement participatif");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"J'aime le projet "+ projet.getName() +" venez le financer. (via PublicrowdFunding)");
			startActivity(emailIntent);
			return true;

		}
		return false;
	}

}
