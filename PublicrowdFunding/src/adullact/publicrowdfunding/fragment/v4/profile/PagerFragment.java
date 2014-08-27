package adullact.publicrowdfunding.fragment.v4.profile;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {

	protected Project projetCurrent;

	FragmentTransaction fragMentTra = null;
	protected String idUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.pager_tab, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		
		Bundle bundle = this.getArguments();
		idUser = bundle.getString("idUser");
		adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity)  getActivity();
		FragmentManager fm = _this.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();
		
		PagerAdaptor adaptor = new PagerAdaptor(fm, idUser);
		viewPager.setAdapter(adaptor);
		viewPager.setCurrentItem(1);
		return view;

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if(idUser.equals("me")){
			inflater.inflate(R.menu.profile, menu);
		}
	    super.onCreateOptionsMenu(menu, inflater);
	}

	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		return;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.preferences:
			Intent in = new Intent(
					getActivity(),
					adullact.publicrowdfunding.controller.preferences.MainActivity.class);
			startActivity(in);
			return true;
		}
		return false;
	}

}