package adullact.publicrowdfunding;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchDialog extends AlertDialog.Builder {

	public SearchDialog(final Context context) {
		super(context);

		LinearLayout linear = new LinearLayout(context);
		linear.setOrientation(1);

		final EditText rechercher = new EditText(context);

	
		linear.addView(rechercher);


		this.setView(linear);

		this.setTitle("Chercher un projet");
		this.setIcon(R.drawable.ic_launcher);
		this.setPositiveButton("Rechercher",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {

						// Faire la recherche

					}
				});
	}

}
