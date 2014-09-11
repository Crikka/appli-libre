package adullact.publicrowdfunding.controller.register;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import adullact.publicrowdfunding.MainActivity;
import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.project.all.ListProjectsFragment;
import adullact.publicrowdfunding.model.server.event.RegistrationEvent;
import adullact.publicrowdfunding.model.server.request.RegistrationRequest;

/**
 * @author Ferrand and Nelaupe
 */
public class RegisterFragement extends Fragment {

	private EditText m_login;
	private EditText m_password1;
	private EditText m_password2;
	private Button m_button;
	
	private LinearLayout loading;
	
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View view = inflater.inflate(R.layout.popup_register,
				container, false);
		
		context = getActivity();
		
		m_login = (EditText) view.findViewById(R.id.inscription_login);
		m_password1 = (EditText) view.findViewById(R.id.inscription_password1);
		m_password2 = (EditText) view.findViewById(R.id.inscription_password2);
		m_button = (Button) view.findViewById(R.id.inscription_button);

		loading = (LinearLayout) view.findViewById(R.id.loading);
		loading.setVisibility(View.GONE);
		
		
		Bundle bundle = this.getArguments();
		if(bundle != null){
		    String login = bundle.getString("login");
		    m_login.setText(login);
		}
		
		m_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (m_login.length() == 0) {
					Toast.makeText(context,
							"Vous avez oublié le pseudo", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (m_password1.length() == 0) {
					Toast.makeText(context,
							"Vous avez oublié le 1er mot de passes",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (m_password2.length() == 0) {
					Toast.makeText(context,
							"Vous avez oublié le 2e mot de passes",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!m_password1.getText().toString()
						.equals(m_password2.getText().toString())) {
					Toast.makeText(context,
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
                    	Toast.makeText(context, "Bienvenue "+m_login.getText().toString(), Toast.LENGTH_SHORT).show();
                    	
                    	FragmentTransaction ft = getFragmentManager().beginTransaction();
                		//ft.setCustomAnimations(R.anim.enter, R.anim.exit);
                		Fragment fragment = new ListProjectsFragment();
                		fragment.setHasOptionsMenu(true);
                		ft.replace(R.id.content_frame, fragment);
                		
                		MainActivity _this = (MainActivity) getActivity();
                		_this.isConnect();
                		
                		ft.commit();
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

					@Override
					public void errorServer() {
						loading.setVisibility(View.GONE);
						Toast.makeText(getActivity().getBaseContext(),
								"Une erreur s'est produite", Toast.LENGTH_SHORT)
								.show();
						
					}
                }).execute();
			}
		});
		
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener( new OnKeyListener()
		{
		    @Override
		    public boolean onKey( View v, int keyCode, KeyEvent event )
		    {
		        if( keyCode == KeyEvent.KEYCODE_BACK )
		        {
                	FragmentTransaction ft = getFragmentManager().beginTransaction();
            		//ft.setCustomAnimations(R.anim.enter, R.anim.exit);
            		Fragment fragment = new ConnexionFragment();
            		ft.replace(R.id.big_font, fragment);
            		
            		ft.commit(); 
            		return true;
		        }
		        return false;
		    }
		} );
		
		return view;
		
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_search).setVisible(false);
		menu.findItem(R.id.action_sort).setVisible(false);
	}
	
	

}