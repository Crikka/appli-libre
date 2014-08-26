package adullact.publicrowdfunding.controller.register;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConnexionActivity extends Activity {

	private EditText m_login;
	private EditText m_password;
	private Button m_buttonValider;
	private Button m_buttonInscription;
	private Context context;

	private LinearLayout loading;
    private LinearLayout loadingPersonnelInfo;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authetificate);

		context = this;

		m_login = (EditText) findViewById(R.id.login_connexion);
		m_password = (EditText) findViewById(R.id.password_connexion);

		m_buttonValider = (Button) findViewById(R.id.valider_connexion);
		m_buttonInscription = (Button) findViewById(R.id.inscription_button);

		loading = (LinearLayout) findViewById(R.id.loading);
		loading.setVisibility(View.GONE);

        loadingPersonnelInfo = (LinearLayout) findViewById(R.id.loadingLoadingInfo);
        loadingPersonnelInfo.setVisibility(View.GONE);

		m_buttonValider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (m_login.length() == 0) {
					Toast.makeText(context, "Vous avez oublié le login",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (m_password.length() == 0) {

					Toast.makeText(context, "Vous avez oublié le mot de passe",
							Toast.LENGTH_SHORT).show();
					return;
				}

				loading.setVisibility(View.VISIBLE);
				String login = m_login.getText().toString();
				String password = m_password.getText().toString();

				new AuthenticationRequest(login, password,
						new AuthenticationEvent() {
							@Override
							public void errorUsernamePasswordDoesNotMatch(
									String username, String password) {
								System.out
										.println("Login ou mot de passe incorect");
								Toast.makeText(context,
										"Login ou mot de passe incorect",
										Toast.LENGTH_LONG).show();
								loading.setVisibility(View.GONE);
							}

							@Override
							public void onAuthentication() {
                                try {
                                    loading.setVisibility(View.GONE);
                                    loadingPersonnelInfo.setVisibility(View.VISIBLE);
                                    Account.getOwn().getUser(new HoldToDo<User>() {
                                        @Override
                                        public void hold(User resource) {
                                            Toast.makeText(context, "Bienvenue " + resource.getPseudo() + "!", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    });
                                } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
                                    finish();

                                }
							}

							@Override
							public void errorNetwork() {
								System.out.println("Erreur serveur");
								Toast.makeText(
										getApplicationContext(),
										"Connexion impossible au serveur, veuillez vérifier vos paramètres resaux.",
										Toast.LENGTH_LONG).show();
								loading.setVisibility(View.GONE);
							}
						}).execute();
			}
		});

		m_buttonInscription.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String login = m_login.getText().toString();

				Intent in = new Intent(getBaseContext(),
						registerActivity.class);
				in.putExtra("login", login);
				startActivityForResult(in, 1);

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				finish();
			}
			if (resultCode == RESULT_CANCELED) {
				/*
				Toast.makeText(getApplicationContext(), "Inscription refusé",
						Toast.LENGTH_LONG).show();*/
			}
		}
	}
}