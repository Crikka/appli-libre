package adullact.publicrowdfunding.controlleur.membre;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.event.RetrieveEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

import android.app.Activity;
import android.content.Context;
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
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connexion);

		context = this;
		
		m_login = (EditText) findViewById(R.id.login_connexion);
		m_password = (EditText) findViewById(R.id.password_connexion);

		m_buttonValider = (Button) findViewById(R.id.valider_connexion);
		m_buttonInscription = (Button) findViewById(R.id.inscription_button);

		m_buttonValider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (m_login.length() == 0) {
					Toast.makeText(context,
							"Vous avez oublié le login", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (m_password.length() == 0) {

					Toast.makeText(context,
							"Vous avez oublié le mot de passe",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String login = m_login.getText().toString();
				String password = m_password.getText().toString();

                AuthenticationRequest request = new AuthenticationRequest(login, password, new AuthenticationEvent() {
                    @Override
                    public void errorUsernamePasswordDoesNotMatch(String username, String password) {
                        Toast.makeText(context,
                                "Login ou mot de passe incorect",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAuthentication() {
                        Toast.makeText(context,
                                "Vous êtes connecté !",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void errorNetwork() {
                        Toast.makeText(context,
                                "Connexion impossible au serveur, veuillez vérifier vos paramètres resaux.",
                                Toast.LENGTH_LONG).show();
                    }
                });
			}
		});

		m_buttonInscription.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String login = m_login.getText().toString();

				Intent in = new Intent(getBaseContext(),
						InscriptionActivity.class);
				in.putExtra("login", login);
				startActivityForResult(in, 1);

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getApplicationContext(),
						"Incription validé, vous pouvez vous connecter",
						Toast.LENGTH_LONG).show();
			}
			if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "Inscription refusé",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}