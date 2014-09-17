package adullact.publicrowdfunding.controller.register;

import adullact.publicrowdfunding.MainActivity;
import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author Ferrand and Nelaupe
 */
public class ConnexionFragment extends Fragment {

	private EditText m_login;
	private EditText m_password;
	private Button m_buttonValider;
	private Button m_buttonInscription;
	private Context context;
	
	private LinearLayout loading;
    private LinearLayout loadingPersonnelInfo;
    private LinearLayout connect_content;
    
	private Fragment _this;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.popup_connect,
				container, false);
		
		context = getActivity();
		
		_this = this;
		
		m_login = (EditText) view.findViewById(R.id.login_connexion);
		m_password = (EditText) view.findViewById(R.id.password_connexion);

		m_buttonValider = (Button) view.findViewById(R.id.valider_connexion);
		m_buttonInscription = (Button) view.findViewById(R.id.inscription_button);

		loading = (LinearLayout) view.findViewById(R.id.loading);
		loading.setVisibility(View.GONE);

		connect_content = (LinearLayout) view.findViewById(R.id.connect_content);
		connect_content.setVisibility(View.VISIBLE);
		
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

				connect_content.setVisibility(View.GONE);
				loading.setVisibility(View.VISIBLE);
				String login = m_login.getText().toString();
				String password = m_password.getText().toString();

				new AuthenticationRequest(login, password,
						new AuthenticationEvent() {
							@Override
							public void errorUsernamePasswordDoesNotMatch(
									String username, String password) {
								
								connect_content.setVisibility(View.VISIBLE);
								loading.setVisibility(View.GONE);
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
                                            
                                    		MainActivity _this = (MainActivity) getActivity();
                                    		_this.isConnect();
                                    	
                                    		back();
                                            
                                            
                                        }
                                    });
                                } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
                                	Toast.makeText(context, "Impossible de se conncecté", Toast.LENGTH_LONG).show();

                                }
							}

							@Override
							public void errorNetwork() {
							
								Toast.makeText(
										context,
										"Connexion impossible au serveur, veuillez vérifier vos paramètres resaux.",
										Toast.LENGTH_LONG).show();
								loading.setVisibility(View.GONE);
							}

							@Override
							public void errorServer() {
								
								Toast.makeText(getActivity().getBaseContext(),
										"Une erreur s'est produite", Toast.LENGTH_SHORT)
										.show();
								
							}
						}).execute();
			}
		});

		m_buttonInscription.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
        		Fragment fragment = new RegisterFragement();

        		ft.replace(R.id.big_font, fragment);
        		String login = m_login.getText().toString();
        		Bundle bundle = new Bundle();
        		bundle.putString("login", login);
        		fragment.setArguments(bundle);
        		ft.addToBackStack(null);
        		ft.commit();

			}
		});
		
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					back();
					return true;
				}
				return false;
			}
		});
		return view;
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_sort).setVisible(false);
	}
	
	public void back() {
		
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.no_anim, R.anim.popup_exit);
		ft.remove(_this);

		FrameLayout filter = (FrameLayout) getActivity().getWindow().getDecorView()
				.findViewById(R.id.big_filter);			
		Animation fadeInAnimation = AnimationUtils.loadAnimation(_this.getActivity(), R.anim.fade_exit);
		filter.setAnimation(fadeInAnimation);
		ft.commit();
		filter.animate();
		
		
	}

}