package adullact.publicrowdfunding;

import adullact.publicrowdfunding.request.AuthentificationRequest;
import adullact.publicrowdfunding.requester.AuthentificationRequester;
import adullact.publicrowdfunding.shared.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
			public void onClick(View v) {

				String login = m_login.getText().toString();
				String password = m_password.getText().toString();
			
				if(login == "" || password == ""){
					
					Toast.makeText(getApplicationContext(),
							"Merci de renseigner les champs",
							Toast.LENGTH_SHORT).show();
					return;
				}
				

				// A toi de faire la connexion ??
				// Comment je fait pour le requester car je ne sais pas si c'est
				// admin ou non. L'authentification doit être transparente.

				User utilisateur = new User(login);

				// Da fuck
				AuthentificationRequester authentificationRequester = new AuthentificationRequester();
				authentificationRequester.post(
						new AuthentificationRequest(utilisateur)).request();

			}
		});

		m_buttonInscription.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				String login = m_login.getText().toString();
				
				Intent in = new Intent(getBaseContext(), InscriptionActivity.class);
				in.putExtra("login", login);
				startActivity(in);
				
			}
		});
	}
}