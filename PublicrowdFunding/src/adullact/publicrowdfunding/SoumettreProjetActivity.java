package adullact.publicrowdfunding;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SoumettreProjetActivity extends Activity {

	private LinearLayout layout;

	private EditText m_titre;
	private EditText m_Description;
	private DatePicker m_dateFin;
	private ImageButton m_bouttonPhoto;
	private ImageButton m_bouttonGallery;
	private ImageButton m_localisation;
	private ImageView photo;

	private static final int SELECT_PHOTO = 100;
	private static final int CAMERA_PIC_REQUEST = 0;

	// Et la on arrive à des trucs bien sale.
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

		layout = (LinearLayout) findViewById(R.id.project_add_layout);

		m_titre = (EditText) findViewById(R.id.soumettre_titre);
		m_Description = (EditText) findViewById(R.id.soumettre_description);
		m_dateFin = (DatePicker) findViewById(R.id.soumettre_date_fin);
		m_bouttonPhoto = (ImageButton) findViewById(R.id.soumettre_photo_camera);
		m_bouttonGallery = (ImageButton) findViewById(R.id.soumettre_photo_gallery);
		m_localisation = (ImageButton) findViewById(R.id.soumettre_localisation);

		m_bouttonPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});

		m_bouttonGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}
		});

		m_localisation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getBaseContext(), CustomMapsActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(in);
			}
		});

		supprimerCalendarView();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		System.out.println(requestCode);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = data.getData();
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
					Bitmap yourSelectedImage = BitmapFactory
							.decodeStream(imageStream);

					photo = new ImageView(this);
					photo.setImageBitmap(yourSelectedImage);

					layout.addView(photo, 0);

				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), "Erreur",
							Toast.LENGTH_LONG).show();
				}

			}
		case CAMERA_PIC_REQUEST:
			if (resultCode == RESULT_OK) {
				Bitmap bit = (Bitmap) data.getExtras().get("data");

				photo = new ImageView(this);
				photo.setImageBitmap(bit);

				layout.addView(photo, 0);
			}
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
			Toast.makeText(getApplicationContext(), "Projet soumis en attente de validation", Toast.LENGTH_SHORT).show();
			return true;

		}
		return false;
	}
	
	
	
	
	
}
