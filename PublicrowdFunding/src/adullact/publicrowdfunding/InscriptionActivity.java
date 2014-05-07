package adullact.publicrowdfunding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionActivity extends Activity {

	private EditText m_login;
	private EditText m_email;
	private EditText m_password1;
	private EditText m_password2;
	private Button m_button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);

		// Si le mec à tapé le login pour la connexion mais appuyé sur
		// inscription,
		// Il récupére ce qu'il a tapé
		String login = getIntent().getExtras().getString("login");

		m_login = (EditText) findViewById(R.id.inscription_login);
		m_email = (EditText) findViewById(R.id.inscription_email);
		m_password1 = (EditText) findViewById(R.id.inscription_password1);
		m_password2 = (EditText) findViewById(R.id.inscription_password2);
		m_button = (Button) findViewById(R.id.inscription_button);

		m_login.setText(login);

		m_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (m_login.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vous avez oublié le pseudo", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (m_email.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vous avez oublié l'email", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (m_password1.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vous avez oublié le 1er mot de passes",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (m_password2.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vous avez oublié le 2e mot de passes",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (m_password1.getText() != m_password2.getText()) {
					Toast.makeText(getApplicationContext(),
							"Les mots de passe sont différents",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String login = m_login.getText().toString();
				String password = m_password1.getText().toString();
				String email = m_email.getText().toString();

				Toast.makeText(getApplicationContext(), "Inscription validé !",
						Toast.LENGTH_SHORT).show();
			}

		});

	}
}
