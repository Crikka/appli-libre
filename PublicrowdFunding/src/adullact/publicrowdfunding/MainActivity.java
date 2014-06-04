package adullact.publicrowdfunding;

import com.google.android.gms.maps.SupportMapFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements TabListener {
	
	private RelativeLayout rl;
	
	
	
	TabProjets fram1;
	TabFavoris fram2;
	TabAllProjectsMaps fram3;

	FragmentTransaction fragMentTra = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_main);
		try {
			rl = (RelativeLayout) findViewById(R.id.mainLayout);
			fragMentTra = getFragmentManager().beginTransaction();
			ActionBar bar = getActionBar();

			bar.addTab(bar.newTab().setText("Projets").setTabListener(this));
			bar.addTab(bar.newTab().setText("Favoris").setTabListener(this));
			bar.addTab(bar.newTab().setText("Localisation")
					.setTabListener(this));

			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_USE_LOGO);
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayShowHomeEnabled(true);
			bar.setDisplayShowTitleEnabled(true);
			bar.show();

		} catch (Exception e) {
			e.getMessage();
		}
	}		
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_action_bar_main, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		if (tab.getText().equals("Projets")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
			}
			fram1 = new TabProjets();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram1);
			fragMentTra.commit();
		} else if (tab.getText().equals("Favoris")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
			}
			fram2 = new TabFavoris();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram2);
			fragMentTra.commit();
		} else if (tab.getText().equals("Localisation")) {
			try {
				rl.removeAllViews();
			} catch (Exception e) {
			}
			
			fram3 = new TabAllProjectsMaps();
			fragMentTra.addToBackStack(null);
			fragMentTra = getFragmentManager().beginTransaction();
			fragMentTra.add(rl.getId(), fram3);
			fragMentTra.commit();
		}

		/*
		 * MEA TESTA adullact.publicrowdfunding.model.server.ExampleAndTest
		 * exampleAndTest = new ExampleAndTest();
		 * //exampleAndTest.authenticationAdmin();
		 * exampleAndTest.createProject();
		 * 
		 * ServerInfo.instance().tryConnection();
		 */

		
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

}
