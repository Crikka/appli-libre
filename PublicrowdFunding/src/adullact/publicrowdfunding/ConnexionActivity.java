package adullact.publicrowdfunding;

import adullact.publicrowdfunding.exceptions.UserNotFoundException;
import adullact.publicrowdfunding.shared.Share;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnexionActivity extends Activity {

	private EditText m_login;
	private EditText m_password;
	private Button m_buttonValider;
	private Button m_buttonInscription;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connexion);

		m_login = (EditText) findViewById(R.id.login_connexion);
		m_password = (EditText) findViewById(R.id.password_connexion);

		m_buttonValider = (Button) findViewById(R.id.valider_connexion);
		m_buttonInscription = (Button) findViewById(R.id.inscription_button);

		m_buttonValider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (m_login.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Vous avez oublié le login", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (m_password.length() == 0) {

					Toast.makeText(getApplicationContext(),
							"Vous avez oublié le mot de passe",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String login = m_login.getText().toString();
				String password = m_password.getText().toString();

				Communicator communicator = new Communicator();

				try {
					communicator.authentificateUser(login, password);
				}
				catch(UserNotFoundException exception) {
					// TODO
				}
				
				if (Share.user == null) {
					Toast.makeText(getApplicationContext(),
							"Login ou mot de passe incorect", Toast.LENGTH_LONG)
							.show();

				} else {
					Toast.makeText(getApplicationContext(),
							"Vous êtes conencté !", Toast.LENGTH_LONG).show();

				}

				// Utilise seulement la classe communicator pour te simplifier
				// la vie :-)
				// --> D'accord, ca fonctionne tout seul, c'est magique
				// Tu peux downcast avec utilisateur.toAdmin()
				// --> Ah oui, admin est un sous type d'utilisateur, alors que d'un point de vue 
				// --> phylosophique est plutôt l'inverse
				

			}
		});

		m_buttonInscription.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String login = m_login.getText().toString();

				Intent in = new Intent(getBaseContext(),
						InscriptionActivity.class);
				in.putExtra("login", login);
				startActivity(in);

			}
		});
	}
}