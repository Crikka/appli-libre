package adullact.publicrowdfunding.controller.profile;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;

/**
 * @author Ferrand and Nelaupe
 */
public class ProfilePagerFragment extends Fragment {

	protected Project projetCurrent;

	FragmentTransaction fragMentTra = null;
	private String idUser;
	private FragmentManager fm;

	private TextView m_pseudo;
	private TextView m_ville;
	private ImageView m_avatar;
	
	private FrameLayout ribbon;

	private TextView m_admin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.pager_tab_profile, container,
				false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

		m_pseudo = (TextView) view.findViewById(R.id.pseudo);
		m_ville = (TextView) view.findViewById(R.id.ville);
		
		m_admin = (TextView) view.findViewById(R.id.text_admin);
		RotateAnimation rotate = (RotateAnimation) AnimationUtils
				.loadAnimation(getActivity(), R.anim.rotate);
		m_admin.setAnimation(rotate);
		ribbon = (FrameLayout) view.findViewById(R.id.admin_ribbon);
		ribbon.setVisibility(View.GONE);
		
		m_avatar = (ImageView) view.findViewById(R.id.avatar);

		Bundle bundle = this.getArguments();
		idUser = bundle.getString("idUser");

		new User().getCache(idUser).toResource(new HoldToDo<User>() {

			@Override
			public void hold(User resource) {
				m_pseudo.setText(Share.formatString(resource.getPseudo()));
				m_ville.setText(Share.formatString(resource.getCity()));
				if (resource.getGender().equals("0")) {
					m_avatar.setImageResource(R.drawable.male_user_icon);
				} else {
					m_avatar.setImageResource(R.drawable.female_user_icon);
				}

			}

		});

		new Account().getCache(idUser).toResource(new HoldToDo<Account>() {

			@Override
			public void hold(Account resource) {
				if(resource.isAdmin()){
					m_admin.setVisibility(View.VISIBLE);	
					ribbon.setVisibility(View.VISIBLE);
				}
			}
		});

		adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity) getActivity();

		fm = _this.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();

		Context context = this.getActivity().getBaseContext();
		PagerAdaptor adaptor = new PagerAdaptor(context, fm, idUser);
		viewPager.setAdapter(adaptor);
		viewPager.setCurrentItem(1);

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					fm.popBackStack();

					return true;
				}
				return false;
			}
		});
		return view;

	}

}