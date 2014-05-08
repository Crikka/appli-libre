package adullact.publicrowdfunding;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
	private ImageView m_illustration;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;

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
		m_illustration = (ImageView) findViewById(R.id.projet_illustration);

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
				// TODO Auto-generated method stub
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
						CustomMapsActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(in);
			}
		});

		supprimerCalendarView();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == PICK_FROM_CAMERA) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				m_illustration.setImageBitmap(photo);

			}
		}

		if (requestCode == PICK_FROM_GALLERY) {
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				m_illustration.setImageBitmap(photo);

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
			Toast.makeText(getApplicationContext(),
					"Projet soumis en attente de validation",
					Toast.LENGTH_SHORT).show();
			return true;

		}
		return false;
	}

}
