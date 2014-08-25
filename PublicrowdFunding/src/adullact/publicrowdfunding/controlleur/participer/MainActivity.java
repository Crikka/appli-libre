package adullact.publicrowdfunding.controlleur.participer;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.ressource.Funding;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private adullact.publicrowdfunding.controlleur.detailProjet.MainActivity _parent;

	private Context context;

	private EditText participation;

	private Button valider;

	private Project projetCurrent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;

		try {
			_parent = (adullact.publicrowdfunding.controlleur.detailProjet.MainActivity) this
					.getParent();

			System.out.println(_parent);

			projetCurrent = _parent.getCurrentProject();
		} catch (Exception e) {
			Toast.makeText(context, "Une erreur s'est produite",
					Toast.LENGTH_SHORT).show();

			Intent returnIntent = new Intent();
			setResult(RESULT_CANCELED, returnIntent);
			e.printStackTrace();
			finish();
		}

		setContentView(R.layout.participer);

		participation = (EditText) findViewById(R.id.participation);
		valider = (Button) findViewById(R.id.valider);

		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				launchPaypal();
			}
		});
	}

	public void financer() {

		try {
			projetCurrent.finance("10", new CreateEvent<Funding>() {

				@Override
				public void errorResourceIdAlreadyUsed() {
					Toast.makeText(context, "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();
					finish();
				}

				@Override
				public void onCreate(Funding resource) {
					System.out.println("Funding crée : " + resource);
					Toast.makeText(context, "Participation prise en compte",
							Toast.LENGTH_SHORT).show();

					finish();
				}

				@Override
				public void errorAuthenticationRequired() {
					Toast.makeText(context, "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();

					finish();
				}

				@Override
				public void errorNetwork() {
					Toast.makeText(context, "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();

					finish();
				}

			});
		} catch (NoAccountExistsInLocal e1) {
			Toast.makeText(context, "Une erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			e1.printStackTrace();
			Intent returnIntent = new Intent();
			setResult(RESULT_CANCELED, returnIntent);
			finish();
		}

	}

	public void launchPaypal() {
		Intent in = new Intent(context, ParticiperPaypalActivity.class);

		int somme = 0;

		try {
			somme = Integer.parseInt(participation.getText().toString());
		} catch (Exception e) {
			Toast.makeText(context, "Le montant est invalide",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (somme < 1) {
			Toast.makeText(context, "1 Euro minimum", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		in.putExtra("participation", somme);
		this.startActivityForResult(in, 1);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				financer();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(context, "Impossible d'éffectuer le payement",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

}
