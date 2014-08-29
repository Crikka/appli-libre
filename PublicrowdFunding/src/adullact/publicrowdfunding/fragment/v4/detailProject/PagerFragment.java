package adullact.publicrowdfunding.fragment.v4.detailProject;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.fragment.v4.register.connexionFragment;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;

public class PagerFragment extends Fragment {

	protected Project projetCurrent;

	FragmentTransaction fragMentTra;
	FragmentManager fm;
	protected int idProject;
	
	private Fragment _current;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.pager_tab, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		_current = this;
		Bundle bundle = this.getArguments();
		String idProject = bundle.getString("idProject");
		adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity)  getActivity();
		fm = _this.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();
		
		PagerAdaptor adaptor = new PagerAdaptor(fm, idProject);
		viewPager.setAdapter(adaptor);
		viewPager.setCurrentItem(1);
		
		
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener( new OnKeyListener()
		{
		    @Override
		    public boolean onKey( View v, int keyCode, KeyEvent event )
		    {
		        if( keyCode == KeyEvent.KEYCODE_BACK )
		        {
		        	fm.popBackStack();
		  
            		return true;
		        }
		        return false;
		    }
		} );
		return view;

	}
	
}