package adullact.publicrowdfunding.controlleur.membre;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
	private ProgressDialog mProgressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);

		m_login = (EditText) findViewById(R.id.inscription_login);
		m_email = (EditText) findViewById(R.id.inscription_email);
		m_password1 = (EditText) findViewById(R.id.inscription_password1);
		m_password2 = (EditText) findViewById(R.id.inscription_password2);
		m_button = (Button) findViewById(R.id.inscription_button);

		String login = getIntent().getExtras().getString("login");
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

				if (!m_password1.getText().toString()
						.equals(m_password2.getText().toString())) {
					Toast.makeText(getApplicationContext(),
							"Les mots de passe sont différents : ",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String username = m_login.getText().toString();
				String password = m_password1.getText().toString();
				// String email = m_email.getText().toString();

				mProgressDialog = ProgressDialog.show(InscriptionActivity.this,
						"Chargement", "Inscription en cours ...", true);

				// Je differencie compte (username + password) et user
				// maintenant, je te divise ta requête
				final User user = new User(username, "prenom", "nom");
                final Account account = new Account(username, password);


                account.serverCreate(new CreateEvent<Account>() {

					@Override
					public void errorResourceIdAlreadyUsed(String id) {
						mProgressDialog.dismiss();
						Intent returnIntent = new Intent();
						setResult(RESULT_CANCELED, returnIntent);
						finish();
					}

					
					
					@Override
					public void onCreate(Account resource) {
						account.setOwn();
						mProgressDialog.dismiss();
						Intent returnIntent = new Intent();
						setResult(RESULT_OK, returnIntent);
						finish();
					}
/*
					@Override
					public void errorAuthenticationRequired() {
						// TODO Auto-generated method stub
						
					}
			*/
				});
				
			}
		});
	}
}
				
		
				/*
				user.serverCreate(new CreateEvent<User>() {
					@Override
					public void errorResourceIdAlreadyUsed(String id) {

					}

					@Override
					public void onCreate(User resource) {
						account.setUser(user);

							@Override
							public void errorAuthenticationRequired() {

							}
						});

					}

					@Override
					public void errorAuthenticationRequired() {

					}
				});

			}

		});
*/
			

