package adullact.publicrowdfunding.controlleur.detailProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class ajouterCommentaireAlert extends AlertDialog.Builder {

	public ajouterCommentaireAlert(final Context context, float rating) {
		super(context);
		
		LinearLayout linear = new LinearLayout(context);
		linear.setOrientation(1);

		linear.setGravity(Gravity.CENTER);
		final RatingBar notation = new RatingBar(context, null,
				android.R.attr.ratingBarStyleIndicator);
		final EditText titre = new EditText(context);
		final EditText message = new EditText(context);
		titre.setHint("Titre");
		message.setHint("Commentaire"); 
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		notation.setLayoutParams(param);
		
		
		notation.setNumStars(5);
		notation.setMax(5);
		notation.setRating((int) rating);
		notation.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;

			}
		});

		linear.addView(notation);
		linear.addView(titre);
		linear.addView(message);
		
		this.setView(linear);

		this.setTitle("Ajouter un commentaire");
		this.setIcon(R.drawable.ic_launcher);
		this.setPositiveButton("Commenter",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						
					}
				});
	}

}
