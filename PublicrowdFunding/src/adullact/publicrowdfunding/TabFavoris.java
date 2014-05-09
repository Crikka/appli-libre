package adullact.publicrowdfunding;

import android.app.Activity;
import android.os.Bundle;

public class TabFavoris extends DownBarMenu {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		
		addDownBarMenu();
		
	}
}