package adullact.publicrowdfunding.custom;

import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Project> {

	Context mContext;
	int layoutResourceId;
	Vector<Project> data = null;

	public CustomAdapter(Context context, int resource, Vector<Project> listItem) {
		super(context, resource,listItem);
		this.mContext = context;
		this.layoutResourceId = resource;
		this.data = listItem;
	}

	private static class UserHolder {

		public TextView titre_projet_liste;
		public TextView description_projet_liste;
		public TextView nb_participation_projet_liste;
		public TextView temps_restant_projet_liste;
		public CustomProgressBar avancement_projet_liste;
		public ImageView illustration;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

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
			holder.nb_participation_projet_liste = (TextView) v
					.findViewById(R.id.nb_participation_projet_liste);
			holder.temps_restant_projet_liste = (TextView) v
					.findViewById(R.id.temps_restant_projet_liste);
			holder.avancement_projet_liste = (CustomProgressBar) v
					.findViewById(R.id.avancement_projet_liste);
			holder.illustration = (ImageView) v
					.findViewById(R.id.icon);

			v.setTag(holder);
		} else {
			holder = (UserHolder) v.getTag();
		}
		
		Project projet = data.elementAt(position);
		holder.titre_projet_liste.setText(projet.getName());
		holder.description_projet_liste.setText(projet.getDescription());
		holder.nb_participation_projet_liste.setText("nb participants non implémenté");
		holder.temps_restant_projet_liste.setText(projet.getNumberOfDayToEnd() + " jours restants");		

		holder.avancement_projet_liste.setArgent(5000*projet.getPercentOfAchievement()/100);
		holder.avancement_projet_liste.setProgress(projet.getPercentOfAchievement());
		holder.avancement_projet_liste.setMaxArgent(5000);
		if(projet.getIllustration() != 0){
		holder.illustration.setImageResource(projet.getIllustration());
		}else{
			holder.illustration.setImageResource(R.drawable.ic_launcher);
		}
		return v;

	}
}
