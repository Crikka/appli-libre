package adullact.publicrowdfunding.fragment.v4.detailProject;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
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
import android.widget.Toast;

public class addCommentAlert extends AlertDialog.Builder {

	
	public addCommentAlert(final Context context,final float rating, final Project projet) {
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
						String s_titre = titre.getText().toString();
						String s_message = message.getText().toString();
						
						try {
							projet.postCommentary(s_titre, s_message, (int) rating,new  CreateEvent<Commentary>(){

								@Override
								public void errorResourceIdAlreadyUsed() {
									Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();									
								}

								@Override
								public void onCreate(Commentary resource) {
									Toast.makeText(context, "Commentaire ajouté !", Toast.LENGTH_SHORT).show();									
									
								}
								
								@Override
								public void errorAuthenticationRequired() {
									Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();									
									
								}

								@Override
								public void errorNetwork() {
									Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();									
									
								}
								
							});
						} catch (NoAccountExistsInLocal e) {
							Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();									
							e.printStackTrace();
						}
						
					}
				});
	}

}
