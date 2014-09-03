package adullact.publicrowdfunding.fragment.v4.project.details;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.fragment.v4.project.participate.PaypalActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import android.widget.CheckBox;
import android.widget.EditText;

public class ParticipatePopup extends Fragment {

	private EditText somme;
	private CheckBox conditions;
	private Button valider;

	private String idProject;

	private Animation shake;

	private Fragment _this;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.popup_participate, container,
				false);

		_this = this;

		somme = (EditText) view.findViewById(R.id.participation);
		conditions = (CheckBox) view.findViewById(R.id.conditions);
		valider = (Button) view.findViewById(R.id.valider);

		shake = AnimationUtils.loadAnimation(this.getActivity()
				.getBaseContext(), R.anim.shake);

		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!conditions.isChecked()) {
					conditions.setTextColor(Color.parseColor("#ff0000"));
					// conditions.invalidate();
					return;
				}

				int sommeToFund = 0;
				try {
					sommeToFund = Integer.parseInt(somme.getText().toString());
				} catch (Exception e) {
					somme.startAnimation(shake);
					return;
				}
				if (sommeToFund < 1) {
					somme.startAnimation(shake);
				}
					
				Intent intent = new Intent(getActivity(), PaypalActivity.class);
				intent.putExtra("participation", sommeToFund);
				getActivity().startActivity(intent);
			}

		});

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					FragmentTransaction ft = getActivity()
							.getSupportFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.no_anim, R.anim.popup_exit);
					ft.remove(_this);
					ft.commit();

					// Multi Thread pour que l'animation s'éxécute
					
					new Handler().postDelayed(new Runnable() {
		                public void run() {
		                	try {
								getActivity().getWindow().getDecorView()
								.findViewById(R.id.filter)
								.setVisibility(View.GONE);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
		                }
		            }, 500);
					
					return true;
				}
				return false;
			}
		});
		return view;

	}

}