package adullact.publicrowdfunding;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class OneProjectActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabHost = getTabHost();

		String id = getIntent().getExtras().getString("key");
		Intent intent = new Intent(this, DetailProjetActivity.class);
		intent.putExtra("key", id);
		tabSpec = tabHost.newTabSpec("Le projet").setIndicator("Description")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		
		intent = new Intent(this, DetailProjetFinancement.class);
		intent.putExtra("key", id);
		tabSpec = tabHost.newTabSpec("Graphique").setIndicator("Fiancement")
				.setContent(intent);
		tabHost.addTab(tabSpec);

		intent = new Intent(this, DetailProjetMap.class);
		intent.putExtra("key", id);
		tabSpec = tabHost.newTabSpec("Maps du projet").setIndicator("Localisation")
				.setContent(intent);
		tabHost.addTab(tabSpec);
		
		

	} 

}
