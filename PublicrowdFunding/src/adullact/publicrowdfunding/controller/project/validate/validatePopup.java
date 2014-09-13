package adullact.publicrowdfunding.controller.project.validate;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

/**
 * @author Ferrand and Nelaupe
 */
public class validatePopup extends Fragment {

	private validatePopup _this;
	
	private Button valider;
	
	private Project currentProject;


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		final View view = inflater.inflate(R.layout.popup_activer, container,
				false);
		
		_this = this;
		
		valider = (Button) view.findViewById(R.id.valider);
		Bundle bundle = this.getArguments();
		
		final String idProject = bundle.getString("idProject");
		
		final RadioGroup selected = (RadioGroup) view.findViewById(R.id.radioGroup);

		new Project().getCache(idProject).toResource(new HoldToDo<Project>(){

			@Override
			public void hold(Project resource) {
				currentProject = resource;
			}
		});
		
		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				switch(selected.getCheckedRadioButtonId()){
				case R.id.validation:
					
					currentProject.setValidate(true);
					update();
					break;
				case R.id.reject:
					currentProject.setValidate(false);
					update();
					break;
					default:
						return;
				}
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
	
	public void back() {

		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.no_anim, R.anim.popup_exit);
		ft.remove(_this);

		FrameLayout filter = (FrameLayout) getActivity().getWindow()
				.getDecorView().findViewById(R.id.big_filter);
		Animation fadeInAnimation = AnimationUtils.loadAnimation(
				_this.getActivity(), R.anim.fade_exit);
		filter.setAnimation(fadeInAnimation);
		ft.commit();
		filter.animate();
	}
	
	public void update(){
		
		currentProject.serverUpdate(new UpdateEvent<Project>(){

			@Override
			public void onUpdate(Project resource) {
				
				
				
				FragmentManager fm = getActivity().getSupportFragmentManager();

				Fragment validate = fm.findFragmentByTag("validateFragment");
				FragmentTransaction ft = fm.beginTransaction();
			
				ft.detach(validate).attach(validate).commit();
				
				back();
				
			}

			@Override
			public void errorResourceIdDoesNotExist() {
				back();
				
			}

			@Override
			public void errorAdministratorRequired() {
				back();
				
			}

			@Override
			public void errorAuthenticationRequired() {
				back();
				
			}

			@Override
			public void errorNetwork() {
				back();
				
			}

			@Override
			public void errorServer() {
				back();
				
			}
			
		});
	}

}