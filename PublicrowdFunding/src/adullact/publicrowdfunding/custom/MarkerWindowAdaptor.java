package adullact.publicrowdfunding.custom;

import java.util.HashMap;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Calcul;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.views.CustomProgressBar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MarkerWindowAdaptor extends FragmentActivity implements InfoWindowAdapter {

	private LinearLayout loading;

	private TextView titre_projet_liste;
	private TextView description_projet_liste;
	private TextView temps_restant_projet_liste;
	private CustomProgressBar avancement_projet_liste;
	private ImageView illustration;
	private TextView sommeFunded;
	private TextView sommeDemander;
	private TextView distance;
	
	private boolean finish;
	
	private final HashMap<Marker, String> markers;
	
	private LayoutInflater inflater;
	
	public MarkerWindowAdaptor(LayoutInflater inflater,  HashMap<Marker, String> markers) {
        this.inflater=inflater;
        this.markers = markers;
        finish = false;
    }

	public void displayInfo(Project projet) {
		titre_projet_liste.setText(projet.getName());
		description_projet_liste.setText(projet.getDescription());
		temps_restant_projet_liste.setText(projet.getNumberOfDayToEnd()
				+ " jours");

		avancement_projet_liste.setProgress(projet.getPercentOfAchievement());
		sommeDemander.setText(projet.getRequestedFunding() + "€");
		sommeFunded.setText(projet.getCurrentFunding() + "€");
		if (projet.getIllustration() != 0) {
			illustration.setImageResource(Utility.getDrawable(projet
					.getIllustration()));
		} else {
			illustration.setImageResource(R.drawable.ic_launcher);
		}

		distance.setVisibility(View.GONE);
		try {
			distance.setText("Distance : "
					+ Calcul.diplayDistance(Share.position,
							projet.getPosition()));
			distance.setVisibility(View.VISIBLE);
		} catch (NullPointerException e) {
			distance.setVisibility(View.GONE);
		}		
		loading.setVisibility(View.GONE);
		System.out.println("projet chagé");
		finish = true;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		View v = inflater.inflate(
				R.layout.fiche_project, null);
		
		loading = (LinearLayout) v.findViewById(R.id.loading);
		loading.setVisibility(View.GONE);
		
		titre_projet_liste = (TextView) v.findViewById(R.id.titre_projet_liste);
		description_projet_liste = (TextView) v
				.findViewById(R.id.description_projet_liste);
		temps_restant_projet_liste = (TextView) v
				.findViewById(R.id.nombre_jour_restant_detail);
		avancement_projet_liste = (CustomProgressBar) v
				.findViewById(R.id.avancement_projet_liste);
		illustration = (ImageView) v.findViewById(R.id.icon);
		sommeDemander = (TextView) v.findViewById(R.id.sommeeDemande);

		sommeFunded = (TextView) v.findViewById(R.id.sommeFund);
		distance = (TextView) v.findViewById(R.id.distance);

		System.out.println("cherche projet :"+markers.get(arg0));
		Cache<Project> projet = new Project().getCache(markers.get(arg0))
				.forceRetrieve();
		projet.toResource(new HoldToDo<Project>() {
			@Override
			public void hold(Project project) {
				System.out.println("trouvé");
				displayInfo(project);
			}
		});
		int chargement = 0;
		while(!finish){
			try {
				System.out.println("on attends le chargement");
				chargement++;
				if(chargement<5){
				Thread.sleep(1000);
				}else{
					finish = true;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return v;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

}