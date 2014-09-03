package adullact.publicrowdfunding.fragment.v4.validateProject;

import adullact.publicrowdfunding.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;

public class validatePopup extends Fragment {

	private validatePopup _this;
	
	private Button valider;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.popup_activer, container,
				false);
		
		_this = this;

		/*
		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}

		});
*/
		
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
		ft.commit();

		// Multi Thread pour que l'animation s'éxécute

		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					getActivity().getWindow().getDecorView()
							.findViewById(R.id.alpha_projets)
							.setVisibility(View.GONE);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 500);
	}

}