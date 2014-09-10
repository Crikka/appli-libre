package adullact.publicrowdfunding.controller.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import adullact.publicrowdfunding.R;

/**
 * @author Ferrand and Nelaupe
 */
public class PagerAdaptor extends FragmentStatePagerAdapter {

	private String idUser;
	
	private Context context;

	
    private int[] titles = {R.string.project_submitted, R.string.bookmark, R.string.financed};
	
	public PagerAdaptor(Context context, FragmentManager fm, String idUser) {
		super(fm);
		this.idUser = idUser;
		this.context = context;
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
    	String title = context.getResources().getString(titles[position % PAGE_COUNT]);
        return title;
    }

}
