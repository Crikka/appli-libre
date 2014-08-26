package adullact.publicrowdfunding.controlleur.membre;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.server.event.RegistrationEvent;
import adullact.publicrowdfunding.model.server.request.RegistrationRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class InscriptionActivity extends Activity {

	private EditText m_login;
	private EditText m_password1;
	private EditText m_password2;
	private Button m_button;
	
	private LinearLayout loading;
	
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);

		context = this;
		
		m_login = (EditText) findViewById(R.id.inscription_login);
		m_password1 = (EditText) findViewById(R.id.inscription_password1);
		m_password2 = (EditText) findViewById(R.id.inscription_password2);
		m_button = (Button) findViewById(R.id.inscription_button);

		loading = (LinearLayout) findViewById(R.id.loading);
		loading.setVisibility(View.GONE);
		
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

				loading.setVisibility(View.VISIBLE);
				
				String username = m_login.getText().toString();
				String password = m_password1.getText().toString();


				// Je differencie compte (username + password) et user
				// maintenant, je te divise ta requête

                new RegistrationRequest(username, password, username, new RegistrationEvent() {
                    @Override
                    public void onRegister() {
                    	Toast.makeText(context, "Bienvenue "+m_login, Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    public void errorUsernameAlreadyUsed() {
                    	loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Username déjà pris", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void errorPseudoAlreadyUsed() {
                    	loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Username déjà pris", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void errorNetwork() {
                    	loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Probléme de connection", Toast.LENGTH_SHORT).show();
                    }
                }).execute();
			}
		});
	}
}