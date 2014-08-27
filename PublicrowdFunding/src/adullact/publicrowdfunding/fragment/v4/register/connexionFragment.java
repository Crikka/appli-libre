package adullact.publicrowdfunding.fragment.v4.register;

import adullact.publicrowdfunding.MainActivity;
import adullact.publicrowdfunding.ProjectsFragment;
import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class connexionFragment extends Fragment {

	private EditText m_login;
	private EditText m_password;
	private Button m_buttonValider;
	private Button m_buttonInscription;
	private Context context;
	
	private LinearLayout loading;
    private LinearLayout loadingPersonnelInfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.activity_authetificate,
				container, false);
		
		context = getActivity();
		
		this.setHasOptionsMenu(false);
		
		m_login = (EditText) view.findViewById(R.id.login_connexion);
		m_password = (EditText) view.findViewById(R.id.password_connexion);

		m_buttonValider = (Button) view.findViewById(R.id.valider_connexion);
		m_buttonInscription = (Button) view.findViewById(R.id.inscription_button);

		loading = (LinearLayout) view.findViewById(R.id.loading);
		loading.setVisibility(View.GONE);

        loadingPersonnelInfo = (LinearLayout) view.findViewById(R.id.loadingLoadingInfo);
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
                                            Toast.makeText(context, "Bienvenue " + resource.getPseudo(), Toast.LENGTH_LONG).show();
                                            
                                            
                                    		FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    		//ft.setCustomAnimations(R.anim.enter, R.anim.exit);
                                    		Fragment fragment = new ProjectsFragment();

                                    		ft.replace(R.id.content_frame, fragment, "allProjectFragment");
                                    		
                                    		MainActivity _this = (MainActivity) getActivity();
                                    		_this.isConnect();
                                    		
                                    		ft.commit();
                                            
                                            
                                        }
                                    });
                                } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
                                	Toast.makeText(context, "Impossible de se conncecté", Toast.LENGTH_LONG).show();

                                }
							}

							@Override
							public void errorNetwork() {
								System.out.println("Erreur serveur");
								Toast.makeText(
										context,
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

				
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
        		//ft.setCustomAnimations(R.anim.enter, R.anim.exit);
        		Fragment fragment = new registerFragement();

        		ft.replace(R.id.content_frame, fragment);
        		
        		String login = m_login.getText().toString();
        		Bundle bundle = new Bundle();
        		bundle.putString("login", login);
        		fragment.setArguments(bundle);
        		
        		MainActivity _this = (MainActivity) getActivity();
        		_this.isConnect();
        		
        		ft.commit();

			}
		});
		return view;
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_sort).setVisible(false);
	}

}