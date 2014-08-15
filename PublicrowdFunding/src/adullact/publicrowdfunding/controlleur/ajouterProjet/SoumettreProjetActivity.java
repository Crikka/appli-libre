package adullact.publicrowdfunding.controlleur.ajouterProjet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CarouselAdapter;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

public class SoumettreProjetActivity extends Activity {

	private EditText m_titre;
	private EditText m_Description;
	private DatePicker m_dateFin;
	private ImageButton m_bouttonPhoto;
	private ImageButton m_bouttonGallery;
	private ImageButton m_localisation;
	private EditText m_edit_text_somme;
	private Context context;

	private LatLng position;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private static final int PICK_MAPS = 3;

	private HorizontalCarouselStyle mStyle;
	private HorizontalCarouselLayout mCarousel;
	private CarouselAdapter mAdapter;
	private ArrayList<Integer> mData = new ArrayList<Integer>(0);

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

		context = this;

		m_titre = (EditText) findViewById(R.id.titre);
		m_Description = (EditText) findViewById(R.id.description);
		m_dateFin = (DatePicker) findViewById(R.id.date_de_fin);
		m_bouttonPhoto = (ImageButton) findViewById(R.id.button_photo_camera);
		m_bouttonGallery = (ImageButton) findViewById(R.id.button_photo_gallery);
		m_localisation = (ImageButton) findViewById(R.id.button_localisation);
		m_edit_text_somme = (EditText) findViewById(R.id.edit_text_somme);

		mData.add(R.drawable.ic_launcher);
		mData.add(R.drawable.basketball);
		mData.add(R.drawable.roi);
		mData.add(R.drawable.ic_launcher);
		mData.add(R.drawable.basketball);
		mData.add(R.drawable.roi);
		mData.add(R.drawable.ic_launcher);
		mData.add(R.drawable.basketball);
		mData.add(R.drawable.roi);
		
		mAdapter = new CarouselAdapter(this);
		mAdapter.setData(mData);
		mCarousel = (HorizontalCarouselLayout) findViewById(R.id.carousel_layout);
		mStyle = new HorizontalCarouselStyle(this,
				HorizontalCarouselStyle.NO_STYLE);
		mCarousel.setStyle(mStyle);
		mCarousel.setAdapter(mAdapter);

		  mCarousel.setOnCarouselViewChangedListener(new CarouselInterface() {
		  
		  @Override public void onItemChangedListener(View v, int position) {
		  
		  }});
		 

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

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		switch (requestCode) {

		/*
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
*/
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

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.soumission, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.soumettre:

			String titre = null;
			if (m_titre.length() == 0) {
				Toast.makeText(context, "Merci de mettre un titre",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			titre = m_titre.getText().toString();

			String description = null;
			if (m_Description.length() == 0) {
				Toast.makeText(context, "Merci de mettre une description",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			description = m_Description.getText().toString();

			Date now = new Date();

			Date date_fin = new Date();
			int day = m_dateFin.getDayOfMonth();
			int month = m_dateFin.getMonth();
			int year = m_dateFin.getYear();
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			date_fin = calendar.getTime();

            Project project = new Project(); // TODO titre, description, m_somme_a_recolter, now, date_fin,
            project.serverCreate(new CreateEvent<Project>() {

                @Override
                public void errorResourceIdAlreadyUsed() {

                }

                @Override
                public void onCreate(Project project) {
                    Toast.makeText(context,
                            "Projet en attente de validation",
                            Toast.LENGTH_SHORT).show();
                  /* if(isAdmin()) {
                         // On  valide ?
                    }*/
                }

                @Override
                public void errorNetwork() {

                }

                @Override
                public void errorAuthenticationRequired() {
                    Toast.makeText(context, "Connexion required",
                            Toast.LENGTH_SHORT).show();

                    // Invite le à se reconnecté/connecté avec : retryWithAnotherAccount();
                }
            });

			return true;

		}
		return false;

	}
}
