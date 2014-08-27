package adullact.publicrowdfunding.fragment.v4.detailProject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdaptor extends FragmentStatePagerAdapter {

	private String idProject;

    private String[] titles = { "Commentaires", "Détail du projet", "Emplacement du projet"};
	
	public PagerAdaptor(FragmentManager fm, String idProject) {
		super(fm);
		this.idProject = idProject;
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
			fragment =  new TabCommentsFragment();
    		bundle.putString("idProject",idProject);
    		fragment.setArguments(bundle);
			return fragment;
		case 1:
				fragment =  new TabProjectFragment();
	    		bundle.putString("idProject",idProject);
	    		fragment.setArguments(bundle);
				return fragment;
		case 2:
			fragment =  new TabMapFragment();
    		bundle.putString("idProject",idProject);
    		fragment.setArguments(bundle);
			return fragment;

		default:
			return new TabProjectFragment();
		}
	}
	

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % PAGE_COUNT];
    }

}
