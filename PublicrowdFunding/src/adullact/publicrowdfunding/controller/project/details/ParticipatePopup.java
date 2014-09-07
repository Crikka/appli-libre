package adullact.publicrowdfunding.controller.project.details;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controller.project.participate.PaypalActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.FrameLayout;

/**
 * @author Ferrand and Nelaupe
 */
public class ParticipatePopup extends Fragment {

	private EditText somme;
	private CheckBox conditions;
	private Button valider;

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

		Bundle bundle = this.getArguments();
		final String idProject = bundle.getString("idProject");

		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!conditions.isChecked()) {
					conditions.setTextColor(Color.parseColor("#ff0000"));
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

				back();

				Intent intent = new Intent(getActivity(), PaypalActivity.class);
				intent.putExtra("participation", sommeToFund);
				intent.putExtra("idProject", idProject);
				getActivity().startActivityForResult(intent, 1);
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

}