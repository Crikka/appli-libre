package adullact.publicrowdfunding.controller.participate;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
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

	private Context context;

	private EditText participation;

	private Button valider;

	private Project projetCurrent;
	
	private String StrSomme;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		StrSomme = null;
		
		try {

			String StridProject = null;
			StridProject = this.getIntent().getExtras().getString("projectId");
			if (StridProject == null) {
				Toast.makeText(getApplicationContext(),
						"Impossible de récupérer l'ID du projet",
						Toast.LENGTH_SHORT).show();
				finish();
			}

			Cache<Project> projetCache = new Project().getCache(StridProject);

			projetCache.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {
					projetCurrent = project;
				}
			});

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

	public void financer(String somme) {

		try {
			projetCurrent.finance(somme, new CreateEvent<Funding>() {

				@Override
				public void errorResourceIdAlreadyUsed() {
					System.out.println("erreur 1");
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
					System.out.println("erreur 2");
					Toast.makeText(context, "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();
					finish();
				}

				@Override
				public void errorNetwork() {
					System.out.println("erreur 3");
					Toast.makeText(context, "Une erreur s'est produite",
							Toast.LENGTH_SHORT).show();
					finish();
				}

			});
		} catch (NoAccountExistsInLocal e1) {
			System.out.println("erreur 4");
			Toast.makeText(context, "Une erreur s'est produite",
					Toast.LENGTH_SHORT).show();
			e1.printStackTrace();
			finish();
		}

	}

	public void launchPaypal() {
		
		if(projetCurrent == null){
			Toast.makeText(context, "Impossible de récupérer le projet",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		
		
		Intent in = new Intent(context, paypalActivity.class);

		int somme = 0;

		try {
			somme = Integer.parseInt(participation.getText().toString());
			StrSomme = participation.getText().toString();
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
		System.out.println("result code : "+requestCode + " result : "+resultCode);
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				System.out.println("insert dans la base de donnée");
				financer(StrSomme);
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(context, "Impossible d'éffectuer le payement",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

}
