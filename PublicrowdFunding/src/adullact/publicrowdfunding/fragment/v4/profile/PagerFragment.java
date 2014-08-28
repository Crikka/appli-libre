package adullact.publicrowdfunding.fragment.v4.profile;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;

public class PagerFragment extends Fragment {

	protected Project projetCurrent;

	FragmentTransaction fragMentTra = null;
	private String idUser;
	private FragmentManager fm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.pager_tab, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

		Bundle bundle = this.getArguments();
		idUser = bundle.getString("idUser");
		adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity) getActivity();
		fm = _this.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();

		PagerAdaptor adaptor = new PagerAdaptor(fm, idUser);
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