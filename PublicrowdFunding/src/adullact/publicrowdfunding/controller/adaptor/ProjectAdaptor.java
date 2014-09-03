package adullact.publicrowdfunding.controller.adaptor;

import java.util.ArrayList;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Calcul;
import adullact.publicrowdfunding.model.local.utilities.CanI;
import adullact.publicrowdfunding.model.local.utilities.Share;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.views.CustomProgressBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Ferrand and Nelaupe
 */
public class ProjectAdaptor extends ArrayAdapter<Project> {

	Context mContext;
	int layoutResourceId;
	ArrayList<Project> data;
	private Activity activity;
	private View v;
	
	public ProjectAdaptor(Context context, int resource, ArrayList<Project> listItem, Activity activity) {
		super(context, resource, listItem);
		this.mContext = context;
		this.layoutResourceId = resource;
		this.data = listItem;
		this.activity = activity;
		
	}

	private static class UserHolder {

		public TextView titre_projet_liste;
		public TextView description_projet_liste;
		public TextView temps_restant_projet_liste;
		public CustomProgressBar avancement_projet_liste;
		public ImageView illustration;
		public TextView sommeFunded;
		public TextView sommeDemander;
		public TextView distance;

	}

	@Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }
	
	public Project getItem(int index) {
		return this.data.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    v = convertView;
		UserHolder holder = new UserHolder();
		
		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(layoutResourceId, null);

			holder.titre_projet_liste = (TextView) v
					.findViewById(R.id.titre_projet_liste);
			holder.description_projet_liste = (TextView) v
					.findViewById(R.id.description_projet_liste);
			holder.temps_restant_projet_liste = (TextView) v
					.findViewById(R.id.nombre_jour_restant_detail);
			holder.avancement_projet_liste = (CustomProgressBar) v
					.findViewById(R.id.avancement_projet_liste);
			holder.illustration = (ImageView) v.findViewById(R.id.icon);
			holder.sommeDemander =  (TextView) v
					.findViewById(R.id.sommeeDemande);
			
			holder.sommeFunded =  (TextView) v
					.findViewById(R.id.sommeFund);
			holder.distance =  (TextView) v
					.findViewById(R.id.distance);
			v.setTag(holder);
		} else {
			holder = (UserHolder) v.getTag();
		}
		Project projet = data.get(position);
		holder.titre_projet_liste.setText(projet.getName());
		holder.description_projet_liste.setText(projet.getDescription());
		holder.temps_restant_projet_liste.setText(projet.getNumberOfDayToEnd()
				+ " jours");

		holder.avancement_projet_liste.setProgress(projet
				.getPercentOfAchievement());
		holder.sommeDemander.setText(projet.getRequestedFunding()+"€");
		holder.sommeFunded.setText(projet.getCurrentFunding()+"€");
		if (projet.getIllustration() != 0) {
			holder.illustration.setImageResource(Utility.getDrawable(projet
					.getIllustration()));
		} else {
			holder.illustration.setImageResource(R.drawable.ic_launcher);
		}
		
		
		
		new CanI(){

			@Override
			protected void yes() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void no() {
				// Il est dans les fav
				v.findViewById(R.id.fav_layout).setVisibility(View.VISIBLE);
				
			}
			
		}.bookmark(projet);
		
		
		
		holder.distance.setVisibility(View.GONE);
		try{
		holder.distance.setText("Distance : "+Calcul.diplayDistance(Share.position, projet.getPosition()));
		holder.distance.setVisibility(View.VISIBLE);
		}catch(NullPointerException e){
			holder.distance.setVisibility(View.GONE);
		}
				
		return v;

	}
}
