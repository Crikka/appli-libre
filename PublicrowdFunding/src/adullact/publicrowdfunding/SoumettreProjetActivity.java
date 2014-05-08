package adullact.publicrowdfunding;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SoumettreProjetActivity extends Activity {

	private LinearLayout layout;

	private EditText m_titre;
	private EditText m_Description;
	private DatePicker m_dateFin;
	private Button m_bouttonPhoto;
	private Button m_bouttonValider;
	private ImageView photo;

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
		m_bouttonPhoto = (Button) findViewById(R.id.soumettre_photo);
		m_bouttonValider = (Button) findViewById(R.id.soumettre_valider);


		m_bouttonPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});

		supprimerCalendarView();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bit = (Bitmap) data.getExtras().get("data");

		photo = new ImageView(this);
		photo.setImageBitmap(bit);
		layout.removeView(m_bouttonPhoto);;
		layout.addView(photo, 0);

	}
}
