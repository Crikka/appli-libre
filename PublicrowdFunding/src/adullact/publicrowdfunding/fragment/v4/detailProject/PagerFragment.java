package adullact.publicrowdfunding.fragment.v4.detailProject;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {

	protected Project projetCurrent;

	FragmentTransaction fragMentTra = null;
	protected int idProject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.pager_tab, container, false);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		
		Bundle bundle = this.getArguments();
		String idProject = bundle.getString("idProject");
		adullact.publicrowdfunding.MainActivity _this = (adullact.publicrowdfunding.MainActivity)  getActivity();
		FragmentManager fm = _this.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();
		
		PagerAdaptor adaptor = new PagerAdaptor(fm, idProject);
		viewPager.setAdapter(adaptor);
		viewPager.setCurrentItem(1);
		return view;

	}
}