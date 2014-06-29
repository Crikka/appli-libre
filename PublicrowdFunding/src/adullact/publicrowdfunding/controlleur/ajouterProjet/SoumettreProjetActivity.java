package adullact.publicrowdfunding.controlleur.ajouterProjet;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.R.id;
import adullact.publicrowdfunding.R.layout;
import adullact.publicrowdfunding.R.menu;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class SoumettreProjetActivity extends Activity {

	private EditText m_titre;
	private EditText m_Description;
	private DatePicker m_dateFin;
	private ImageButton m_bouttonPhoto;
	private ImageButton m_bouttonGallery;
	private ImageButton m_localisation;
	private ImageView m_illustration;
	private SeekBar m_bar_somme;
	private EditText m_edit_text_somme;
	private CheckBox m_is_Not_Defined_On_Seek_Bar;
	private TextView m_afficher_montant;

	private LatLng position;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private static final int PICK_MAPS = 3;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void supprimerCalendarView() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			m_dateFin.setCalendarViewShown(false);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soumettre_projet);

		m_titre = (EditText) findViewById(R.id.titre);
		m_Description = (EditText) findViewById(R.id.description);
		m_dateFin = (DatePicker) findViewById(R.id.date_de_fin);
		m_bouttonPhoto = (ImageButton) findViewById(R.id.button_photo_camera);
		m_bouttonGallery = (ImageButton) findViewById(R.id.button_photo_gallery);
		m_localisation = (ImageButton) findViewById(R.id.button_localisation);
		m_illustration = (ImageView) findViewById(R.id.illustration);
		m_bar_somme = (SeekBar) findViewById(R.id.seek_bar_somme);
		m_edit_text_somme = (EditText) findViewById(R.id.edit_text_somme);
		m_is_Not_Defined_On_Seek_Bar = (CheckBox) findViewById(R.id.is_Defined_On_Seek_Bar);
		m_afficher_montant = (TextView) findViewById(R.id.afficher_montant);

		m_bouttonPhoto.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 0);
				intent.putExtra("aspectY", 0);
				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 150);

				try {

					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);

				} catch (ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(),
							"Une erreur s'est produite", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		m_bouttonGallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent();

				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 0);
				intent.putExtra("aspectY", 0);
				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 150);

				try {

					intent.putExtra("return-data", true);
					startActivityForResult(Intent.createChooser(intent,
							"Complete action using"), PICK_FROM_GALLERY);

				} catch (ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(),
							"Une erreur s'est produite", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		m_localisation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getBaseContext(),
						AddLocationProject.class);
				if (position != null) {
					in.putExtra("latitude", position.latitude);
					in.putExtra("longitude", position.longitude);
				}

				startActivityForResult(in, 3);
			}
		});

		supprimerCalendarView();

		m_bar_somme.setMax(10000);
		m_bar_somme
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						progress = progress / 10;
						progress = progress * 10;
						m_afficher_montant.setText("Somme à récolter : "
								+ progress + " €");
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						
						
					}

				});

		m_is_Not_Defined_On_Seek_Bar
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							m_bar_somme.setEnabled(false);

							String somme = "0";
							if (m_edit_text_somme.getText().length() != 0) {
								somme = m_edit_text_somme.getText().toString();
							}
							m_afficher_montant.setText("Somme à récolter : "
									+ somme + " €");

						} else {
							m_afficher_montant.setText("Somme à récolter : "
									+ m_bar_somme.getProgress() + " €");
							m_bar_somme.setEnabled(true);
						}

					}

				});
		m_edit_text_somme
				.setOnKeyListener(new android.view.View.OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						if (m_is_Not_Defined_On_Seek_Bar.isChecked()) {

							String somme = "0";
							if (m_edit_text_somme.getText().length() != 0) {
								somme = m_edit_text_somme.getText().toString();
							}
							m_afficher_montant.setText("Somme à récolter : "
									+ somme + " €");

						} else {
							m_afficher_montant.setText("Somme à récolter : "
									+ m_bar_somme.getProgress() + " €");
						}
						return true;
					}
				});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {

		case PICK_FROM_CAMERA:
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				m_illustration.setImageBitmap(photo);

			}
			break;

		case PICK_FROM_GALLERY:
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				m_illustration.setImageBitmap(photo);

			}
			break;

		case PICK_MAPS:
			Bundle extras3 = data.getExtras();
			if (extras3 != null) {

				position = new LatLng(extras3.getDouble("latitude"),
						extras3.getDouble("longitude"));
				Toast.makeText(getApplicationContext(),
						"Position du projet ajouté", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.soumission, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.soumettre:

			/*
			 * try { //Communicator.addProject(name, description,
			 * requestedFunding); if(Share.user.isAdmin()){
			 * Toast.makeText(getApplicationContext(), "Connexion required",
			 * Toast.LENGTH_SHORT).show(); }else{
			 * 
			 * } // Soumettre dans la base de donnée } catch
			 * (AuthentificationRequiredException e) {
			 * Toast.makeText(getApplicationContext(), "Connexion required",
			 * Toast.LENGTH_SHORT).show(); }
			 */
			Toast.makeText(getApplicationContext(),
					"Projet soumis titre : "+m_titre.getText().toString()+ " -> "+m_Description.getText().toString(),
					Toast.LENGTH_SHORT).show();
			return true;

		}
		return false;

	}
}
