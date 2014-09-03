package adullact.publicrowdfunding.controller.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Ferrand and Nelaupe
 */
public class PagerAdaptor extends FragmentStatePagerAdapter {

	private String idUser;

    private String[] titles = { "Projets soumis", "Favoris", "Financés"};
	
	public PagerAdaptor(FragmentManager fm, String idUser) {
		super(fm);
		this.idUser = idUser;
	}

	final int PAGE_COUNT = 3;

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment fragment = null;
		Bundle bundle = new Bundle();
		
		switch (position) {
		
		case 0:
			fragment =  new ListSubmitedProjectsFragment();
    		bundle.putString("idUser",idUser);
    		fragment.setArguments(bundle);
			return fragment;
		case 1:
				fragment =  new ListBookmarksFragment();
	    		bundle.putString("idUser",idUser);
	    		fragment.setArguments(bundle);
				return fragment;
		case 2:
			fragment =  new ListFinancedProjectsFragment();
    		bundle.putString("idUser",idUser);
    		fragment.setArguments(bundle);
			return fragment;

		default:
			return new ListBookmarksFragment();
		}
	}
	

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % PAGE_COUNT];
    }

}
