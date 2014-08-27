package adullact.publicrowdfunding.fragment.v4.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdaptor extends FragmentStatePagerAdapter {

	private String idUser;

    private String[] titles = { "Projets soumis", "Favoris", "Fianncé"};
	
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
			fragment =  new SubmitedFragment();
    		bundle.putString("idUser",idUser);
    		fragment.setArguments(bundle);
			return fragment;
		case 1:
				fragment =  new BookmarkFragment();
	    		bundle.putString("idUser",idUser);
	    		fragment.setArguments(bundle);
				return fragment;
		case 2:
			fragment =  new FinancedFragment();
    		bundle.putString("idUser",idUser);
    		fragment.setArguments(bundle);
			return fragment;

		default:
			return new BookmarkFragment();
		}
	}
	

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % PAGE_COUNT];
    }

}
